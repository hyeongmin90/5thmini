package ktminithteam.domain;

import java.time.LocalDate;
import java.util.*;
import ktminithteam.domain.*;
import ktminithteam.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class BookPublished extends AbstractEvent {

    private Long publishId;
    private Long manuscriptId;
    private String title;
    private String content;
    private String summaryUrl;
    private String coverUrl;
    private String category;
    private Long cost;
    private Boolean isAccept;
    private Date createdAt;

    public BookPublished(Publish aggregate) {
        super(aggregate);
        this.publishId = aggregate.getPublishId();
        this.manuscriptId = aggregate.getManuscriptId();
        this.title = aggregate.getTitle();
        this.content = aggregate.getContent();
        this.summaryUrl = aggregate.getSummaryUrl();
        this.coverUrl = aggregate.getCoverUrl();
        this.category = aggregate.getCategory();
        this.cost = aggregate.getCost();
        this.isAccept = aggregate.getIsAccept();
        this.createdAt = aggregate.getCreatedAt();
    }

    public BookPublished() {
        super();
    }
}