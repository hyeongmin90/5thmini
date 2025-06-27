package ktminithteam.domain;

import java.util.*;
import ktminithteam.domain.*;
import ktminithteam.infra.AbstractEvent;
import lombok.*;

@Data
@ToString
public class RequestSubscribed extends AbstractEvent {

    private Long id;
    private Long bookId;
    private Long userId;
    private Long subscriberId;
    private String status;
    private Date expirationDate;
    private Long cost;
}
