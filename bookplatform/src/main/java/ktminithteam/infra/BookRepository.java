package ktminithteam.infra;

import java.util.List;
import ktminithteam.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "Books",
    path = "Books"
)
public interface BookRepository
    extends PagingAndSortingRepository<Book, Long> {}
