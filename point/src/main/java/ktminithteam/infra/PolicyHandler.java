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
    PointRepository pointRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SignedUp'"
    )
    public void wheneverSignedUp_포인트지급(@Payload SignedUp signedUp) {
        SignedUp event = signedUp;
        System.out.println(
            "\n\n##### listener 포인트지급 : " + signedUp + "\n\n"
        );

        // Sample Logic //
        Point.포인트지급(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='Verified'"
    )
    public void wheneverVerified_포인트지급(@Payload Verified verified) {
        Verified event = verified;
        System.out.println(
            "\n\n##### listener 포인트지급 : " + verified + "\n\n"
        );

        // Sample Logic //
        Point.포인트지급(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='RequestSubscribed'"
    )
    public void wheneverRequestSubscribed_RequestSubscribe(
        @Payload RequestSubscribed requestSubscribed
    ) {
        RequestSubscribed event = requestSubscribed;
        System.out.println(
            "\n\n##### listener RequestSubscribe : " +
            requestSubscribed +
            "\n\n"
        );

        // Comments //
        //포인트 충분: 구독 시작됨
        // 포인트 부족: 구독 거절됨

        // Sample Logic //
        Point.requestSubscribe(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
