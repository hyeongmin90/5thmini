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
public class PublishBookViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private PublishBookRepository publishBookRepository;

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='BookPublished'"
    )
    public void whenBookPublished_then_CREATE_1(
        @Payload BookPublished bookPublished
    ) {
        try {
            if (!bookPublished.validate()) return;

            // view 객체 생성
            PublishBook publishBook = new PublishBook();
            // view 객체에 이벤트의 Value 를 set 함
            publishBook.setPublishId(bookPublished.getpublishId());
            publishBook.setAuthorId(bookPublished.getAuthorId());
            publishBook.setTitle(bookPublished.getTitle());
            publishBook.setContent(bookPublished.getContent());
            publishBook.setSummaryUrl(bookPublished.getSummaryUrl());
            publishBook.setCoverUrl(bookPublished.getCoverUrl());
            publishBook.setCategory(bookPublished.getCategory());
            publishBook.setCost(bookPublished.getCost());
            // view 레파지 토리에 save
            publishBookRepository.save(publishBook);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SubscribeSucceed'"
    )
    public void whenSubscribeSucceed_then_UPDATE_1(
        @Payload SubscribeSucceed subscribeSucceed
    ) {
        try {
            if (!subscribeSucceed.validate()) return;
            // view 객체 조회
            publishBookRepository.findByPublishId(sub.getPublishId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
