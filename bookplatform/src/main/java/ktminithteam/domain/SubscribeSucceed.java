package ktminithteam.domain;

import java.util.*;
import ktminithteam.domain.*;
import ktminithteam.infra.AbstractEvent;
import lombok.*;

@Data
@ToString
public class SubscribeSucceed extends AbstractEvent {

    private Long id;
    private Long subscriberId;
    private Date expirationDate;
    private Long bookId;
    private String status;
}
