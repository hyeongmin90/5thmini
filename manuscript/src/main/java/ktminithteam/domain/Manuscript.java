package ktminithteam.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import ktminithteam.ManuscriptApplication;
import ktminithteam.domain.PublishRequestedEvent;
import lombok.Data;

@Entity
@Table(name = "Manuscript_table")
@Data
//<<< DDD / Aggregate Root
public class Manuscript {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long manuscriptId;

    private Long authorId;

    private String title;

    private String content;

    private Date createdAt;

    private Date updatedAt;

    private BookStatus status;

    @PostPersist
    public void onPostPersist() {
        PublishRequestedEvent publishRequestedEvent = new PublishRequestedEvent(
            this
        );
        publishRequestedEvent.publishAfterCommit();
    }

    public static ManuscriptRepository repository() {
        ManuscriptRepository manuscriptRepository = ManuscriptApplication.applicationContext.getBean(
            ManuscriptRepository.class
        );
        return manuscriptRepository;
    }
}
//>>> DDD / Aggregate Root
