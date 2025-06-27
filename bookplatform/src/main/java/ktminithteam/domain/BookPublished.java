package ktminithteam.domain;

import java.util.*;
import ktminithteam.domain.*;
import ktminithteam.infra.AbstractEvent;
import lombok.*;

@Data
@ToString
public class BookPublished extends AbstractEvent {

    private Long publishId;
    private String content;
    private String summaryUrl;
    private String coverUrl;
    private String category;
    private Long cost;
    private Long bookId;
    private Boolean isAccept;
    private Date createdAt;
    private String title;
}
