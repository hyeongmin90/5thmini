package ktminithteam.domain;

import java.util.*;
import ktminithteam.domain.*;
import ktminithteam.infra.AbstractEvent;
import lombok.*;

@Data
@ToString
public class Verified extends AbstractEvent {

    private Long subscriberId;
    private String telecom;
}
