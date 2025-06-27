package ktminithteam.domain;

import java.util.*;
import ktminithteam.domain.*;
import ktminithteam.infra.AbstractEvent;
import lombok.*;

@Data
@ToString
public class SignedUp extends AbstractEvent {

    private Long id;
    private String telecom;
}
