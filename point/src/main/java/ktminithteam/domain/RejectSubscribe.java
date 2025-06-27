package ktminithteam.domain;

import java.time.LocalDate;
import java.util.*;
import ktminithteam.domain.*;
import ktminithteam.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class RejectSubscribe extends AbstractEvent {

    private Long id;
    private Long subscriberId;

    public RejectSubscribe(Point aggregate) {
        super(aggregate);
    }

    public RejectSubscribe() {
        super();
    }
}
//>>> DDD / Domain Event
