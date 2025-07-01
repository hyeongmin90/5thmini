package ktminithteam.domain;

import java.util.*;
import ktminithteam.domain.*;
import ktminithteam.infra.AbstractEvent;
import lombok.*;

@Data
@ToString
public class RejectSubscribe extends AbstractEvent {

    private Long id;
    private Long subscribeId;
    private Long subscriberId;
}
