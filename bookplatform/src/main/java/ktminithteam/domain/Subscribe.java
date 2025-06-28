package ktminithteam.domain;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "SubscribeBook_table")
@Data
public class Subscribe {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long subscribeId;
    private Long publishId;
    private Long subscriberId;
    private Date expriationDate;
}
