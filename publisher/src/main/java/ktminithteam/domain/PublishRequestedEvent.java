package ktminithteam.domain;

import java.util.*;
import ktminithteam.domain.*;
import ktminithteam.infra.AbstractEvent;
import lombok.*;

@Data
@ToString
public class PublishRequestedEvent extends AbstractEvent {
    private Long publishId;
    private Long bookId;
    private Long authorId;
    private String title;
    private Date requestedAt;
    private String content;
}
