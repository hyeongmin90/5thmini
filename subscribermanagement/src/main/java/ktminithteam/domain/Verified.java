package ktminithteam.domain;

import java.time.LocalDate;
import java.util.*;
import ktminithteam.domain.*;
import ktminithteam.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class Verified extends AbstractEvent {

    private Long id;
    private String telecom;

    public Verified(Subscriber aggregate) {
        super(aggregate);
    }

    public Verified() {
        super();
    }
}
//>>> DDD / Domain Event
