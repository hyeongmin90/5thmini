package ktminithteam.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import ktminithteam.config.kafka.KafkaProcessor;
import ktminithteam.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='BookPublished'"
    )
    public void wheneverBookPublished_Publish(
        @Payload BookPublished bookPublished
    ) {
        BookPublished event = bookPublished;
        System.out.println(
            "\n\n##### listener Publish : " + bookPublished + "\n\n"
        );
        // Comments //
        //조회용 출간 데이터 복사

        // Sample Logic //

    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SubscribeSucceed'"
    )
    public void wheneverSubscribeSucceed_IncreaseSubscribeCount(
        @Payload SubscribeSucceed subscribeSucceed
    ) {
        SubscribeSucceed event = subscribeSucceed;
        System.out.println(
            "\n\n##### listener IncreaseSubscribeCount : " +
            subscribeSucceed +
            "\n\n"
        );
        // Sample Logic //

    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SubscribeSucceed'"
    )
    public void wheneverSubscribeSucceed_CopySubscribe(
        @Payload SubscribeSucceed subscribeSucceed
    ) {
        SubscribeSucceed event = subscribeSucceed;
        System.out.println(
            "\n\n##### listener CopySubscribe : " + subscribeSucceed + "\n\n"
        );
        // Sample Logic //

    }
}
//>>> Clean Arch / Inbound Adaptor
