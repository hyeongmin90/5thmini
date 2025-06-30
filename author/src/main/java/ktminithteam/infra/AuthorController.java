package ktminithteam.infra;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import ktminithteam.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Author registerAuthor(@RequestBody Author author) {
        author.setCreatedAt(new java.util.Date());
        author.setIsAccept(null);
        return authorRepository.save(author);
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
}
//>>> Clean Arch / Inbound Adaptor
