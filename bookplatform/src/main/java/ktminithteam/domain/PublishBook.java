package ktminithteam.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "PublishBook_table")
@Data
public class PublishBook {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long publishId;

    private Long authorId;

    private String title;

    @Column(length = 1000)
    private String content;

    @Column(length = 1000)
    private String summaryUrl;

    @Column(length = 1000)
    private String coverUrl;

    private String category;

    private Long cost;
}
