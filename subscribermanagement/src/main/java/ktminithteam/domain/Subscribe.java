package ktminithteam.domain;

import java.time.LocalDate;
import javax.persistence.*;
import ktminithteam.SubscribermanagementApplication;
import ktminithteam.infra.ApplicationContextProvider;
import ktminithteam.infra.BookInfoRepository;
import lombok.Data;

@Entity
@Table(name = "Subscribe_table")
@Data
//<<< DDD / Aggregate Root
public class Subscribe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long subscribeId;

    private Long subscriberId;

    private Long publishId;

    private String status;

    private LocalDate expirationDate;
/**
@done cost를 bookinfo에서 가져와 카프카 필드에 채움
response에는 반영 안되어 확인 불가  -> 카프카 이벤트에서 확인
*/
    @PostPersist
    public void onPostPersist() {
        this.setStatus("CHECKING"); //db에는 반영 안됨 -> 메시지 큐에서 확인용
        
        // BookInfoRepository 가져오기
        BookInfoRepository bookInfoRepository = ApplicationContextProvider
            .getApplicationContext()
            .getBean(BookInfoRepository.class);

        BookInfo book = bookInfoRepository.findById(this.getPublishId()).orElse(null);

        RequestSubscribed requestSubscribed = new RequestSubscribed(this);

        if (book != null)
            requestSubscribed.setCost(book.getCost());

        requestSubscribed.publishAfterCommit();
    } 

    public static SubscribeRepository repository() {
        SubscribeRepository subscribeRepository = SubscribermanagementApplication.applicationContext.getBean(
            SubscribeRepository.class
        );
        return subscribeRepository;
    }

    

    //<<< Clean Arch / Port Method
    public static void subscribeFailure(RejectSubscribe rejectSubscribe) {
        repository().findById(rejectSubscribe.getSubscribeId()).ifPresent(subscribe->{
            subscribe.setStatus("FAILURE");
            repository().save(subscribe);
        });
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void subscribeSuccess(Substart substart) {
        repository().findById(substart.getSubscribeId()).ifPresent(subscribe->{

            subscribe.setStatus("SUCCESS");
            subscribe.setExpirationDate(substart.getExpirationDate());

            if (substart.getExpirationDate() != null)
                subscribe.setExpirationDate(substart.getExpirationDate());
            repository().save(subscribe);
        });
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
