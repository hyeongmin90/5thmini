package ktminithteam.domain;

import java.time.LocalDate;
import java.util.*;
import ktminithteam.domain.*;
import ktminithteam.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class SubscribeSucceed extends AbstractEvent {

    private Long subscribeId;
    private Long subscriberId;
    private LocalDate expirationDate;
    private Long publishId;

    public SubscribeSucceed(Subscribe aggregate) {
        super(aggregate);
    }

    public SubscribeSucceed() {
        super();
    }
}
//>>> DDD / Domain Event
