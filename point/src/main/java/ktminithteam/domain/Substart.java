package ktminithteam.domain;

import ktminithteam.infra.AbstractEvent;
import lombok.*;

import java.util.Date;
import java.time.LocalDate;

@Data
@ToString
public class Substart extends AbstractEvent {

    private Long id;
    private Long subscribeId;
    private Long subscriberId;
    private LocalDate expirationDate;

    public Substart(Point aggregate, Long subscribeId) {
        super(aggregate);
        this.subscribeId = subscribeId;
    }

    public Substart() {
        super();
    }
}

