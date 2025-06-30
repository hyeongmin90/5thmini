package ktminithteam.domain;

import ktminithteam.infra.AbstractEvent;
import lombok.*;

@Data
@ToString
public class RejectSubscribe extends AbstractEvent {

    private Long id;
    private Long subscriberId;

    public RejectSubscribe(Point aggregate) {
        super(aggregate);
        this.id = aggregate.getId();
        this.subscriberId = aggregate.getSubscriberId();
    }

    public RejectSubscribe() {
        super();
    }
}
