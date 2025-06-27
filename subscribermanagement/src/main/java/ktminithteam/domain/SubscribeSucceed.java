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

    private Long id;
    private Long subscriberId;
    private Date expirationDate;
    private Long bookId;
    private String status;

    public SubscribeSucceed(Subscribe aggregate) {
        super(aggregate);
    }

    public SubscribeSucceed() {
        super();
    }
}
//>>> DDD / Domain Event
