package ktminithteam.domain;

import javax.persistence.*;
import lombok.Data;
import ktminithteam.BookplatformApplication;

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

    public static BookRepository repository() {
        BookRepository bookRepository = BookplatformApplication.applicationContext.getBean(
            BookRepository.class
        );
        return bookRepository;
    }

    public void increaseSubscribeCount() {
        subscribeCount++;
    }

    public void checkBestseller() {
        if (subscribeCount >= 10) {
            isBestseller = true;
        }
    }
}