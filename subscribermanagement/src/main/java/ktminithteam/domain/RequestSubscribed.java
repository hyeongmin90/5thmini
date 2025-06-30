package ktminithteam.domain;

import java.time.LocalDate;
import java.util.*;
import ktminithteam.domain.*;
import ktminithteam.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class RequestSubscribed extends AbstractEvent {

    private Long subscribeId;
    private Long subscriberId;
    private Long publishId;
    private Long cost;

    public RequestSubscribed(Subscribe aggregate) {
        super(aggregate);
    }

    public RequestSubscribed() {
        super();
    }
}
//>>> DDD / Domain Event
