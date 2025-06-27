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

    @Autowired
    PublishRepository publishRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PublishRequestedEvent'"
    )
    public void wheneverPublishRequestedEvent_PublishProcess(
        @Payload PublishRequestedEvent publishRequestedEvent
    ) {
        PublishRequestedEvent event = publishRequestedEvent;
        System.out.println(
            "\n\n##### listener PublishProcess : " +
            publishRequestedEvent +
            "\n\n"
        );

        // Sample Logic //
        Publish.publishProcess(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
