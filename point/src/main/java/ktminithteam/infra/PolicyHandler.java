package ktminithteam.infra;

import javax.transaction.Transactional;
import ktminithteam.config.kafka.KafkaProcessor;
import ktminithteam.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PolicyHandler {

    @Autowired
    PointRepository pointRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    /**
     * [회원가입 이벤트] → 포인트 지급
     */
    @StreamListener(value = KafkaProcessor.INPUT, condition = "headers['type']=='SignedUp'")
    public void wheneverSignedUp_포인트지급(@Payload SignedUp signedUp) {
        System.out.println("\n\n##### listener 포인트지급 : " + signedUp + "\n\n");
        Point.포인트지급(signedUp);
    }

    /**
     * [구독 요청 이벤트] → 포인트 차감 후 처리
     */
    @StreamListener(value = KafkaProcessor.INPUT, condition = "headers['type']=='RequestSubscribed'")
    public void wheneverRequestSubscribed_RequestSubscribe(@Payload RequestSubscribed requestSubscribed) {
        System.out.println("\n\n##### listener RequestSubscribe : " + requestSubscribed + "\n\n");
        Point.requestSubscribe(requestSubscribed);
    }
}
