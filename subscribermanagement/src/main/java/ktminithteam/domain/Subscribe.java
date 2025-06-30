package ktminithteam.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import ktminithteam.SubscribermanagementApplication;
import ktminithteam.domain.RequestSubscribed;
import ktminithteam.domain.SubscribeSucceed;
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

    private Date expirationDate;

    private Long cost;
/**
@TODO Cost 내용 넣어야함, 현재 cost 필드 null
*/
    // @PostPersist
    // public void onPostPersist() {
    //     this.setStatus("CHECKING");
    //     RequestSubscribed requestSubscribed = new RequestSubscribed(this);
    //     requestSubscribed.publishAfterCommit();
    // } 

    public static SubscribeRepository repository() {
        SubscribeRepository subscribeRepository = SubscribermanagementApplication.applicationContext.getBean(
            SubscribeRepository.class
        );
        return subscribeRepository;
    }

    

    //<<< Clean Arch / Port Method
    public static void subscribeFailure(RejectSubscribe rejectSubscribe) {
        repository().findById(rejectSubscribe.getId()).ifPresent(subscribe->{
            subscribe.setStatus("FAILURE");
            repository().save(subscribe);
        });
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void subscribeSuccess(Substart substart) {
        repository().findById(substart.getId()).ifPresent(subscribe->{
            subscribe.setStatus("SUCCESS");
            if (substart.getSubscriptionTicketExpirationDate() != null)
                subscribe.setExpirationDate(substart.getSubscriptionTicketExpirationDate());
            repository().save(subscribe);
        });
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
