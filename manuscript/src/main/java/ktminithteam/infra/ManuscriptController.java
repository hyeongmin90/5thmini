package ktminithteam.infra;

import java.util.Date;
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
}
//>>> Clean Arch / Inbound Adaptor
