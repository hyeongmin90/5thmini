package ktminithteam.domain;

import java.time.LocalDate;
import java.util.*;
import ktminithteam.domain.*;
import ktminithteam.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class Substart extends AbstractEvent {

    private Long id;
    private Long subscriberId;

    public Substart(Point aggregate) {
        super(aggregate);
    }

    public Substart() {
        super();
    }
}
//>>> DDD / Domain Event
