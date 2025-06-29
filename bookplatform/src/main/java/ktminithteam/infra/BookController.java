package ktminithteam.infra;

import javax.transaction.Transactional;
import ktminithteam.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class BookController {
    @Autowired
    BookRepository publishRepository;
}
