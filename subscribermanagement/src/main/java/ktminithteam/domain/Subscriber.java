package ktminithteam.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import ktminithteam.SubscribermanagementApplication;
import ktminithteam.domain.Verified;
import lombok.Data;

@Entity
@Table(name = "Subscriber_table")
@Data
//<<< DDD / Aggregate Root
public class Subscriber {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long subscriberId;

    private String name;

    private String email;

    private String password;

    private String telecom;

    private String phoneNumber;

    private Boolean isRecommended;

    // 엔티티 생성시 이벤트 발행
    @PostPersist
    public void onPostPersist() {
        SignedUp signedUp = new SignedUp(this);
        signedUp.publishAfterCommit();
    }

    @PostUpdate
    public void onPostUpdate() {
        Verified verified = new Verified(this);
        verified.publishAfterCommit();

    }

    public static SubscriberRepository repository() {
        SubscriberRepository subscriberRepository = SubscribermanagementApplication.applicationContext.getBean(
            SubscriberRepository.class
        );
        return subscriberRepository;
    }

    //<<< Clean Arch / Port Method
    public void joinMembership() {
        //implement business logic here:
        // 구독자 생성 로직
        SignedUp signedUp = new SignedUp(this);
        signedUp.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method

    public static void recommend(RejectSubscribe rejectSubscribe) {
        Long subId = rejectSubscribe.getSubscriberId();
        Subscriber sub = repository().findById(subId).orElse(null);
        sub.setIsRecommended(true);
        repository().save(sub);
    }

}
//>>> DDD / Aggregate Root
