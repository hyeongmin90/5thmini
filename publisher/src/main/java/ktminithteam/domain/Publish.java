package ktminithteam.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import ktminithteam.PublisherApplication;
import ktminithteam.domain.BookPublished;
import lombok.Data;

@Entity
@Table(name = "Publish_table")
@Data
//<<< DDD / Aggregate Root
public class Publish {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long publishId;

    private Long manuscriptId;
    private Boolean isAccept;

    @Column(length = 1000)
    private String summaryUrl;

    @Column(length = 1000)
    private String coverUrl;

    private String content;

    private Date createdAt;

    @Column(length = 500)
    private String category;

    private Long cost;

    private String title;

    private Long authorId;

    public void confirm() {
        this.isAccept = true;
        this.createdAt = new Date(); //확정 시점 기록

        BookPublished bookPublished = new BookPublished(this);
        bookPublished.publishAfterCommit();
    }

    public static PublishRepository repository() {
        PublishRepository publishRepository = PublisherApplication.applicationContext.getBean(
            PublishRepository.class
        );
        return publishRepository;
    }

    public static Publish publishProcess(PublishRequestedEvent event) {
        Publish publish = new Publish();
        publish.setManuscriptId(event.getManuscriptId());
        publish.setAuthorId(event.getAuthorId());
        publish.setTitle(event.getTitle());
        publish.setContent(event.getContent());
        publish.setCreatedAt(new Date());
        publish.setIsAccept(false);

        return repository().save(publish);
    }

    public void applyAiResult(String summaryUrl, String coverUrl, String category) {
        this.summaryUrl = summaryUrl;
        this.coverUrl = coverUrl;
        this.category = category;
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
