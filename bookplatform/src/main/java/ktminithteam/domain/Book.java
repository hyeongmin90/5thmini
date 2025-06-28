package ktminithteam.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Book_table")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long bookId;
    private Long publishId;
    private Long subscribeCount;
    private boolean isBestseller;
}