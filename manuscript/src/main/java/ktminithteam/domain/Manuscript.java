package ktminithteam.domain;

import java.util.Date;
import javax.persistence.*;

import ktminithteam.ManuscriptApplication;
import ktminithteam.domain.PublishRequestedEvent;
import lombok.Data;

@Entity
@Table(name = "Manuscript_table")
@Data
public class Manuscript {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long manuscriptId;

    private Long authorId;

    private String title;

    private String content;

    private Date createdAt;

    private Date updatedAt;

    @Enumerated(EnumType.STRING)
    private BookStatus status; // "DRAFT", "TEMP_SAVE", "FINAL_SAVE", "REQUESTED"

    
    public void saveTemporarily(String newContent) {
        this.content = newContent;
        this.status = BookStatus.TEMP_SAVE;
        this.updatedAt = new Date();
    }

    
    public void saveFinal(String finalContent) {
        this.content = finalContent;
        this.status = BookStatus.FINAL_SAVE;
        this.updatedAt = new Date();
    }

    
    public void requestPublish() {
        if (this.status != BookStatus.FINAL_SAVE) {
            throw new IllegalStateException("최종 저장된 원고만 출간 요청 가능");
        }

        this.status = BookStatus.REQUESTED;
        this.updatedAt = new Date();

        PublishRequestedEvent event = new PublishRequestedEvent(this);
        event.publishAfterCommit(); 
    }

    
    public static ManuscriptRepository repository() {
        return ManuscriptApplication.applicationContext.getBean(ManuscriptRepository.class);
    }
}

