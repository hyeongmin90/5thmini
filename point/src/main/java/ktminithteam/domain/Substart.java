package ktminithteam.domain;

import ktminithteam.infra.AbstractEvent;
import lombok.*;

import java.util.Date;

@Data
@ToString
public class Substart extends AbstractEvent {

    private Long id;
    private Long subscriberId;
    private Date expirationDate;

    public Substart(Point aggregate) {
        super(aggregate);
        this.id = aggregate.getId();
        this.subscriberId = aggregate.getSubscriberId();
    }

    public Substart() {
        super();
    }
}

