package ktminithteam.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublishService {

    @Autowired
    private PublishRepository publishRepository;

    // AI 자동화 결과 반영 메서드
    public void handleAiResults(Long publishId, String summaryUrl, String coverUrl, String category) {
        // 1. 출간 엔티티 조회
        Publish publish = publishRepository.findById(publishId)
                .orElseThrow(() -> new RuntimeException("출간 정보를 찾을 수 없습니다."));

        // 2. AI 결과 적용
        publish.applyAiResult(summaryUrl, coverUrl, category);

        // 3. 출간 확정 처리
        publish.confirm();

        // 4. 저장
        publishRepository.save(publish);
    }
}
