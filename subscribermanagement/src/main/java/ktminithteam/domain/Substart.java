package ktminithteam.domain;

import java.util.*;
import ktminithteam.domain.*;
import ktminithteam.infra.AbstractEvent;
import lombok.*;
import java.time.LocalDate;


@Data
@ToString
public class Substart extends AbstractEvent {

    private Long id;
    private Long subscribeId;
    private Long subscriberId;
    private LocalDate expirationDate;
}
