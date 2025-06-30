package ktminithteam.infra;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ktminithteam.domain.BookInfo;
import ktminithteam.domain.RequestSubscribed;
import ktminithteam.domain.Subscribe;
import ktminithteam.domain.SubscribeRepository;
import ktminithteam.domain.Substart;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final BookInfoRepository bookInfoRepository;
    // private final KafkaEventPublisher kafkaEventPublisher;

    @Transactional
    public Subscribe createSubscribe(Subscribe subscribe) {
        // 1. 저장
        subscribe.setStatus("CHECKING");

        // 2. 외부 정보 채움
        BookInfo book = bookInfoRepository.findById(subscribe.getPublishId()).orElse(null);
        if (book != null) {
            subscribe.setCost(book.getCost());
        }
        
        subscribeRepository.save(subscribe);

        RequestSubscribed requestSubscribed = new RequestSubscribed(subscribe);
        requestSubscribed.publishAfterCommit();
        return subscribe;
    }
}
