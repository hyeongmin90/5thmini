package ktminithteam.infra;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import ktminithteam.domain.*;
import ktminithteam.infra.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//<<< Clean Arch / Inbound Adaptor

@RestController
@Transactional
public class AuthorController {

    @Autowired
    AuthorRepository authorRepository;

    @PostMapping("/authors")
    public ResponseEntity<RegisterResponse> registerAuthor(@RequestBody Author author) {
        // 이메일 중복 체크
        Optional<Author> existingAuthor = authorRepository.findByEmail(author.getEmail());

        if (existingAuthor.isPresent()) {
            // 이미 등록된 이메일인 경우
            RegisterResponse response = new RegisterResponse(
                null,
                false,
                "이미 등록된 이메일입니다."
            );
            return ResponseEntity.badRequest().body(response);
        }

        // 신규 가입 처리
        author.setCreatedAt(new java.util.Date());
        author.setIsAccept(null);
        Author savedAuthor = authorRepository.save(author);

        RegisterResponse response = new RegisterResponse(
            savedAuthor.getId(),
            true,
            "회원가입이 성공적으로 완료되었습니다."
        );

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/authors/{id}/accept")
    public Author acceptAuthor(@PathVariable Long id) {
        Author author = authorRepository.findById(id).orElseThrow();
        author.setIsAccept(true);
        return authorRepository.save(author);
    }

    @PatchMapping("/authors/{id}/reject")
    public Author rejectAuthor(@PathVariable Long id) {
        Author author = authorRepository.findById(id).orElseThrow();
        author.setIsAccept(false);
        return authorRepository.save(author);
    }

    // 모든 저자 목록 조회
    @GetMapping("/authors")
    public Iterable<Author> getAuthors() {
        return authorRepository.findAll();
    }

    @PostMapping("/authors/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        Optional<Author> authorOptional = authorRepository.findByEmail(request.getEmail());

        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();

            // 비밀번호가 일치하는지 확인
            if (author.getPassword().equals(request.getPassword())) {
                // isAccept가 true인 경우에만 로그인 성공
                if (author.getIsAccept() != null && author.getIsAccept()) {
                    LoginResponse response = new LoginResponse(
                        author.getId(),
                        true,
                        "로그인 성공"
                    );
                    return ResponseEntity.ok(response);
                } else {
                    LoginResponse response = new LoginResponse(
                        null, false, "승인되지 않은 작가입니다."
                    );
                    return ResponseEntity.ok(response);
                }
            } else {
                LoginResponse response = new LoginResponse(
                    null, false, "비밀번호가 일치하지 않습니다."
                );
                return ResponseEntity.ok(response);
            }
        } else {
            LoginResponse response = new LoginResponse(
                null, false, "해당 이메일의 작가를 찾을 수 없습니다."
            );
            return ResponseEntity.ok(response);
        }
    }
}
//>>> Clean Arch / Inbound Adaptor
