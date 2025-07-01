package ktminithteam.domain;

import ktminithteam.infra.AbstractEvent;
import lombok.*;

@Data
@ToString
public class RejectSubscribe extends AbstractEvent {

    private Long id;
    private Long subscribeId;
    private Long subscriberId;

    public RejectSubscribe(Point aggregate, Long subscribeId) {
        super(aggregate);
        this.subscribeId = subscribeId;
    }

    public RejectSubscribe() {
        super();
    }
}
