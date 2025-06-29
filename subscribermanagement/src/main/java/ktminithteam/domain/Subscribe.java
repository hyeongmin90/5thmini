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
    private Long id;

    private Long subscriberId;

    private Long publishId;

    private String status;

    private Date expirationDate;
/**
@TODO Cost 내용 넣어야함, 현재 cost 필드 null
*/
    @PostPersist
    public void onPostPersist() {
        RequestSubscribed requestSubscribed = new RequestSubscribed(this);
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
            repository().save(subscribe);
        });
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
