package ktminithteam.infra;

import javax.transaction.Transactional;
import ktminithteam.config.kafka.KafkaProcessor;
import ktminithteam.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@Transactional
public class PolicyHandler {

    @Autowired
    PublishRepository publishRepository;

    @Autowired
    OpenAIApiClient aiClient;

    @Autowired
    S3Service s3Service;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PublishRequestedEvent'"
    )
    public void wheneverPublishRequestedEvent_PublishProcess(
        @Payload PublishRequestedEvent event
    ) {
        System.out.println("\n\n##### listener PublishProcess : " + event + "\n\n");

        Publish publish = Publish.publishProcess(event);

        try {
            String summary = aiClient.generateSummary(event.getContent());
            String category = aiClient.classifyCategory(event.getContent());
            String coverUrl = aiClient.generateCover(event.getTitle());
            UUID uuid = UUID.randomUUID();
            String summaryUrl = s3Service.uploadSummaryAsPdf(summary, uuid.toString());

            publish.applyAiResult(summaryUrl, coverUrl, category);

            long cost = 500 + (event.getContent().length() / 100 * 10);
            if (summary != null) cost += 100;
            if (coverUrl != null) cost += 200;

            publish.setCost(cost);
        } catch (Exception e) {
            System.out.println("⚠️ AI 호출 실패: " + e.getMessage());
            // AI 실패 시 fallback 로직 추가 가능
        }

        publishRepository.save(publish);
    }
}
