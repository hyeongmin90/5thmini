package ktminithteam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriberResponse {
    private Long subscriberId;
    private String name;
    private String email;
    private String phoneNumber;
    private Boolean isRecommended;
}
