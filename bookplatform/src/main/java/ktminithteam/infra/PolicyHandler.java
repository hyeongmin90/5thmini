package ktminithteam.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

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
    BookRepository bookRepository;

    @Autowired
    SubscribeRepository subscribeRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='BookPublished'"
    )
    public void wheneverBookPublished_CreatBook(
        @Payload BookPublished bookPublished
    ) {
        BookPublished event = bookPublished;
        System.out.println(
            "\n\n##### listener Publish : " + bookPublished + "\n\n"
        );
        
        Book book = new Book();
        book.setPublishId(event.getPublishId());
        book.setSubscribeCount(0L);
        book.setBestseller(false);
        bookRepository.save(book);

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

        try {
            Book book = bookRepository.findByPublishId(event.getPublishId()).get();
            book.increaseSubscribeCount();
            book.checkBestseller();
            bookRepository.save(book);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
//>>> Clean Arch / Inbound Adaptor
