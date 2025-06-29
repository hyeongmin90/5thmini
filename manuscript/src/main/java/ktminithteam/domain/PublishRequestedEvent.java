package ktminithteam.domain;

import java.util.Date;
import ktminithteam.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class PublishRequestedEvent extends AbstractEvent {

    private Long manuscriptId;
    private Long authorId;
    private String title;
    private String content;
    private Date requestedAt;

    public PublishRequestedEvent(Manuscript aggregate) {
        super(aggregate);
        this.manuscriptId = aggregate.getManuscriptId();
        this.authorId = aggregate.getAuthorId();
        this.title = aggregate.getTitle();
        this.requestedAt = new Date(); 
    }

    public PublishRequestedEvent() {
        super();
    }
}
//>>> DDD / Domain Event
