package ktminithteam.infra;

import java.io.IOException;
import java.util.*;
import java.util.Optional;
import javax.transaction.Transactional;
import ktminithteam.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/publishes")
@Transactional
public class PublishController {

    @Autowired
    PublishRepository publishRepository;

    @Autowired
    S3Service s3Service;

    @Autowired
    OpenAIApiClient aiClient;

    @PutMapping("/{id}/confirm")
    public void confirmPublish(@PathVariable Long id) {
        Publish publish = publishRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("출간 정보를 찾을 수 없습니다"));

        publish.confirm(); // 출간 확정 처리 → isAccept=true + 이벤트 발행
        publishRepository.save(publish); // DB에 반영
    }

    @GetMapping //조회
    public List<Publish> getAllPublishes() {
        Iterable<Publish> iterable = publishRepository.findAll();
        List<Publish> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }

    @GetMapping("/{id}") //상세 조회
    public Publish getPublishById(@PathVariable Long id) {
        return publishRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("출간 정보를 찾을 수 없습니다"));
    }

    // 출간 재수행 API
    @PostMapping("/{id}/retry")
    public String retryPublish(@PathVariable Long id) throws IOException {
        Optional<Publish> optional = publishRepository.findById(id);
        if (optional.isPresent()) {
            Publish publish = optional.get();

            // event가 아니라 publish의 필드 사용
            String summary = aiClient.generateSummary(publish.getContent());
            String category = aiClient.classifyCategory(publish.getContent());
            String coverUrl = aiClient.generateCover(publish.getTitle());
            UUID uuid = UUID.randomUUID();
            String summaryUrl = s3Service.uploadSummaryAsPdf(summary, uuid.toString());

            publish.setSummaryUrl(summaryUrl);
            publish.setCategory(category);
            publish.setCoverUrl(coverUrl);

            publishRepository.save(publish);

            return "재시도 완료";
        } else {
            return "해당 출간 정보를 찾을 수 없습니다.";
        }
    }

    // authorId로 출간 목록 전체 조회 API (쿼리 파라미터, /publishes?authorId=xxx)
    @GetMapping(params = "authorId")
    public List<Publish> getPublishesByAuthorId(@RequestParam Long authorId) {
        return publishRepository.findAllByAuthorId(authorId);
    }
}
//>>> Clean Arch / Inbound Adaptor
