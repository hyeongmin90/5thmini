package ktminithteam.policy;

import ktminithteam.domain.*;
import ktminithteam.domain.PublishRequestedEvent;
import ktminithteam.service.AICoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PublishPolicy {

    @Autowired
    private PublishRepository publishRepository;
    
    @Autowired
    AICoverService aiCoverService;

    @EventListener
    public void handle(PublishRequestedEvent event) {
        // 이벤트에서 publishId 같은 식별자만 받고,
        // 실제 출간 도메인 정보를 DB에서 조회
        Publish publish = publishRepository.findById(event.getPublishId())
            .orElseThrow(() -> new RuntimeException("출간 정보를 찾을 수 없습니다"));

        // publish 에서 제목, 카테고리 정보 가져와 AI 서비스 호출
        String coverUrl = aiCoverService.generateCover(publish.getTitle(), publish.getCategory());

        // 출간 진행 메서드 호출 (출간 상태 변경 및 coverUrl 적용 등)
        publish.applyAiResult(/* summaryUrl */ null, coverUrl, publish.getCategory());
        // 변경사항 저장
        publishRepository.save(publish);
    }
}
