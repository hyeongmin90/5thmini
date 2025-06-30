package ktminithteam.infra;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;

import ktminithteam.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//<<< Clean Arch / Inbound Adaptor
@RestController
@RequestMapping(value = "/manuscripts")
@Transactional
public class ManuscriptController {

    @Autowired
    ManuscriptRepository manuscriptRepository;

    @PostMapping
    public ResponseEntity<?> registerManuscript(@RequestBody Manuscript dto) {
        dto.setCreatedAt(new Date());
        dto.setStatus(BookStatus.DRAFT);
        manuscriptRepository.save(dto);
        return ResponseEntity.ok(dto);
    }

    
    @PutMapping("/{id}/save-temp")
    public ResponseEntity<?> saveTemporary(@PathVariable Long id, @RequestBody Map<String, String> request) {
        Manuscript manuscript = manuscriptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("원고 없음"));
        manuscript.saveTemporarily(request.get("content"));
        manuscriptRepository.save(manuscript);
        return ResponseEntity.ok("임시 저장 완료");
    }

    @PutMapping("/{id}/save-final")
    public ResponseEntity<?> saveFinal(@PathVariable Long id, @RequestBody Map<String, String> request) {
        Manuscript manuscript = manuscriptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("원고 없음"));
        manuscript.saveFinal(request.get("content"));
        manuscriptRepository.save(manuscript);
        return ResponseEntity.ok("최종 저장 완료");
    }

    @PostMapping("/{id}/request-publish")
    public ResponseEntity<?> requestPublish(@PathVariable Long id) {
        Manuscript manuscript = manuscriptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("원고 없음"));

        try {
            manuscript.requestPublish();
            manuscriptRepository.save(manuscript);
            return ResponseEntity.ok("출간 요청 완료");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 작가별 원고 조회 API (작가 ID는 필수 파라미터)
    @GetMapping
    public List<Manuscript> getManuscripts(@RequestParam Long authorId) {
        return manuscriptRepository.findByAuthorId(authorId);
    }

    // 원고 단일 조회 API (명확한 리턴 타입 지정)
    @GetMapping("/{manuscriptId}")
    public ResponseEntity<Manuscript> getManuscriptById(@PathVariable Long manuscriptId) {
        Manuscript manuscript = manuscriptRepository.findById(manuscriptId)
                .orElseThrow(() -> new RuntimeException("원고를 찾을 수 없습니다."));

        return ResponseEntity.ok(manuscript);
    }
}
//>>> Clean Arch / Inbound Adaptor
