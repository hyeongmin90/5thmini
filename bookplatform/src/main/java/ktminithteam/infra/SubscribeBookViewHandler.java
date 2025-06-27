package ktminithteam.infra;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import ktminithteam.config.kafka.KafkaProcessor;
import ktminithteam.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class SubscribeBookViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private SubscribeBookRepository subscribeBookRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenSubscribeSucceed_then_CREATE_1(
        @Payload SubscribeSucceed subscribeSucceed
    ) {
        try {
            if (!subscribeSucceed.validate()) return;

            // view 객체 생성
            SubscribeBook subscribeBook = new SubscribeBook();
            // view 객체에 이벤트의 Value 를 set 함
            subscribeBook.setSubscribeId(
                String.valueOf(subscribeSucceed.getId())
            );
            subscribeBook.setExpriationDate(
                subscribeSucceed.getExpirationDate()
            );
            subscribeBook.setBookId(subscribeSucceed.getBookId());
            // view 레파지 토리에 save
            subscribeBookRepository.save(subscribeBook);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
