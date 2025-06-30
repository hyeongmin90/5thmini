package ktminithteam.domain;

import java.time.LocalDate;
import java.util.*;
import ktminithteam.infra.AbstractEvent;
import lombok.Data;

@Data
public class BookPublished extends AbstractEvent {

    private Long publishId;
    private Long authorId;
    private String title;
    private String content;
    private String summaryUrl;
    private String coverUrl;
    private String category;
    private Long cost;
}
