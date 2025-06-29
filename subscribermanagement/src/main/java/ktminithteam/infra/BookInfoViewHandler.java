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
public class BookInfoViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private BookInfoRepository bookInfoRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenBookPublished_then_CREATE_1(
        @Payload BookPublished bookPublished
    ) {
        try {
            if (!bookPublished.validate()) return;

            // view 객체 생성
            BookInfo bookInfo = new BookInfo();
            // view 객체에 이벤트의 Value 를 set 함
            bookInfo.setId(bookPublished.getPublishId());
            bookInfo.setCost(bookPublished.getCost());
            // view 레파지 토리에 save
            bookInfoRepository.save(bookInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
