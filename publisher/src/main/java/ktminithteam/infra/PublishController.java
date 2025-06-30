package ktminithteam.infra;

import java.util.*;
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
// @RequestMapping(value="/publishes")
@Transactional
public class PublishController {

    @Autowired
    PublishRepository publishRepository;

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
    @PostMapping("/publish/request-test") //출간 요청 직접 테스트용
    public void requestPublishTest(@RequestBody Publish dummy) {
        PublishRequestedEvent event = new PublishRequestedEvent();
        event.setTitle(dummy.getTitle());
        event.setContent(dummy.getContent());
        event.setBookId(dummy.getBookId());
        //event.setCategory(dummy.getCategory());

        event.publish(); // 이벤트 수동 발행
    }

    @Autowired
    PublishService publishService;

    @PostMapping("/publish/{id}/ai-result")
    public void applyAiResult(@PathVariable Long id, @RequestBody AiResultRequest request) {
        publishService.handleAiResults(
            id,
            request.getSummaryUrl(),
            request.getCoverUrl(),
            request.getCategory()
        );
    }
}
//>>> Clean Arch / Inbound Adaptor
