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
    public void wheneverSignedUp_SignedUp(@Payload SignedUp signedUp) {
        System.out.println("\n\n##### listener pointIncrease : " + signedUp + "\n\n");
        Point.pointIncrease(signedUp);
    }

     /**
     * [KT 인증됨 이벤트] KT 회원 인증시 포인트 추가 지급
     */
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='Verified'"
    )
    public void wheneverVerified_Verified(@Payload Verified verified) {
        Verified event = verified;
        System.out.println("\n\n##### listener Verified : " + verified + "\n\n");

        Point.pointIncrease_verified(event);
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
