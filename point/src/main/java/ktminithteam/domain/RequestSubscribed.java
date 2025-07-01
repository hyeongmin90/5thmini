package ktminithteam.domain;

import java.util.*;
import ktminithteam.domain.*;
import ktminithteam.infra.AbstractEvent;
import lombok.*;

@Data
@ToString
public class RequestSubscribed extends AbstractEvent {

    private Long id;
    private Long subscribeId;
    private Long publishId;
    private Long subscriberId;
    private Long cost;
}
