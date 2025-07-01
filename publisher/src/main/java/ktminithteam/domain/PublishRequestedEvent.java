package ktminithteam.domain;

import java.util.*;
import ktminithteam.domain.*;
import ktminithteam.infra.AbstractEvent;
import lombok.*;

@Data
@ToString
public class PublishRequestedEvent extends AbstractEvent {

    private Long publishId;
    private Long manuscriptId;
    private Long authorId;
    private String title;
    private String content;
    private Date requestedAt;
}
