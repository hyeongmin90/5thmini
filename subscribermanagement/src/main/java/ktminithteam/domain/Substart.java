package ktminithteam.domain;

import java.util.*;
import ktminithteam.domain.*;
import ktminithteam.infra.AbstractEvent;
import lombok.*;

@Data
@ToString
public class Substart extends AbstractEvent {

    private Long id;
    private Long subscriberId;
    private Date subscriptionTicketExpirationDate;
}
