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

    public static SubscribeRepository repository() {
        SubscribeRepository subscribeRepository = SubscribermanagementApplication.applicationContext.getBean(
            SubscribeRepository.class
        );
        return subscribeRepository;
    }

    //<<< Clean Arch / Port Method
    public static void subscribeFailure(RejectSubscribe rejectSubscribe) {
        //implement business logic here:

        /** Example 1:  new item 
        Subscribe subscribe = new Subscribe();
        repository().save(subscribe);

        */

        /** Example 2:  finding and process
        

        repository().findById(rejectSubscribe.get???()).ifPresent(subscribe->{
            
            subscribe // do something
            repository().save(subscribe);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void subscribeSuccess(Substart substart) {
        //implement business logic here:

        /** Example 1:  new item 
        Subscribe subscribe = new Subscribe();
        repository().save(subscribe);

        SubscribeSucceed subscribeSucceed = new SubscribeSucceed(subscribe);
        subscribeSucceed.publishAfterCommit();
        */

        /** Example 2:  finding and process
        

        repository().findById(substart.get???()).ifPresent(subscribe->{
            
            subscribe // do something
            repository().save(subscribe);

            SubscribeSucceed subscribeSucceed = new SubscribeSucceed(subscribe);
            subscribeSucceed.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
