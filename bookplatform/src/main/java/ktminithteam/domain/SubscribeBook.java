package ktminithteam.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "SubscribeBook_table")
@Data
public class SubscribeBook {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private String subscribeId;

    private Date expriationDate;
    private Long bookId;
    private String summaryUrl;
    private String coverUrl;
    private String category;
    private String author;
    private Long subscriptionCount;
    private String content;
}
