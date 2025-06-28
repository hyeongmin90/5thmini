package ktminithteam.domain;

import java.util.*;
import ktminithteam.infra.AbstractEvent;
import lombok.*;

@Data
@ToString
public class SubscribeSucceed extends AbstractEvent {

    private Long subscribeId;
    private Long subscriberId;
    private Date expirationDate;
    private Long publishId;
    private String status;
}