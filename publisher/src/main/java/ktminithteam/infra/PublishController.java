package ktminithteam.infra;

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
    PublishService publishService;

    @Autowired
    OpenAIApiClient aiClient;

    @PutMapping("/publish/{id}/confirm")
    public void confirmPublish(@PathVariable Long id) {
        Publish publish = publishRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("출간 정보를 찾을 수 없습니다"));

        publish.confirm(); // 출간 확정 처리 → isAccept=true + 이벤트 발행
        publishRepository.save(publish); // DB에 반영
    }

    @GetMapping("/publish") //조회
    public List<Publish> getAllPublishes() {
        Iterable<Publish> iterable = publishRepository.findAll();
        List<Publish> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }
    @GetMapping("/publish/{id}") //상세 조회
    public Publish getPublishById(@PathVariable Long id) {
        return publishRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("출간 정보를 찾을 수 없습니다"));
    }

    // 출간 재수행 API
    @PostMapping("/{id}/retry")
    public String retryPublish(@PathVariable Long id) {
        Optional<Publish> optional = publishRepository.findById(id);
        if (optional.isPresent()) {
            Publish publish = optional.get();

            String summary = aiClient.generateSummary(event.getContent());
            String category = aiClient.classifyCategory(event.getContent());
            String coverUrl = aiClient.generateCover(event.getTitle());

            publish.setSummaryUrl(summary);
            publish.setCoverUrl(category);
            publish.setCategory(coverUrl);

            publishRepository.save(publish);
            return "출간 정보가 재생성되었습니다.";
        } else {
            return "해당 출간 정보를 찾을 수 없습니다.";
        }
    }
}
//>>> Clean Arch / Inbound Adaptor
