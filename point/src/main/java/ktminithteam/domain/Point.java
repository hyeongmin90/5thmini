package ktminithteam.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import ktminithteam.PointApplication;
import ktminithteam.domain.RejectSubscribe;
import ktminithteam.domain.Substart;
import lombok.Data;

@Entity
@Table(name = "Point_table")
@Data
//<<< DDD / Aggregate Root
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long subscriberId;

    private Integer point;

    public static PointRepository repository() {
        PointRepository pointRepository = PointApplication.applicationContext.getBean(
            PointRepository.class
        );
        return pointRepository;
    }

    //<<< Clean Arch / Port Method
    public static void 포인트지급(SignedUp signedUp) {
        //implement business logic here:

        /** Example 1:  new item 
        Point point = new Point();
        repository().save(point);

        */

        /** Example 2:  finding and process
        

        repository().findById(signedUp.get???()).ifPresent(point->{
            
            point // do something
            repository().save(point);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void 포인트지급(Verified verified) {
        //implement business logic here:

        /** Example 1:  new item 
        Point point = new Point();
        repository().save(point);

        */

        /** Example 2:  finding and process
        

        repository().findById(verified.get???()).ifPresent(point->{
            
            point // do something
            repository().save(point);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void requestSubscribe(RequestSubscribed requestSubscribed) {
        //implement business logic here:

        /** Example 1:  new item 
        Point point = new Point();
        repository().save(point);

        Substart substart = new Substart(point);
        substart.publishAfterCommit();
        RejectSubscribe rejectSubscribe = new RejectSubscribe(point);
        rejectSubscribe.publishAfterCommit();
        */

        /** Example 2:  finding and process
        

        repository().findById(requestSubscribed.get???()).ifPresent(point->{
            
            point // do something
            repository().save(point);

            Substart substart = new Substart(point);
            substart.publishAfterCommit();
            RejectSubscribe rejectSubscribe = new RejectSubscribe(point);
            rejectSubscribe.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
