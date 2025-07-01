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
    SubscriberRepository subscriberRepository;

    @Autowired
    SubscribeRepository subscribeRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='RejectSubscribe'"
    )
    public void wheneverRejectSubscribe_SubscribeFailure(
        @Payload RejectSubscribe rejectSubscribe
    ) {
        RejectSubscribe event = rejectSubscribe;
        System.out.println(
            "\n\n##### listener SubscribeFailure : " + rejectSubscribe + "\n\n"
        );

        // Sample Logic //
        Subscriber.recommend(event);        
        Subscribe.subscribeFailure(event);
    }


    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='Substart'"
    )
    public void wheneverSubstart_SubscribeSuccess(@Payload Substart substart) {
        Substart event = substart;
        System.out.println(
            "\n\n##### listener SubscribeSuccess : " + substart + "\n\n"
        );

        // Sample Logic //
        Subscribe.subscribeSuccess(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
