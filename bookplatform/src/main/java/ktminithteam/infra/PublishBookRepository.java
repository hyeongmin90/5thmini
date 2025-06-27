package ktminithteam.infra;

import java.util.List;
import ktminithteam.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "publishBooks",
    path = "publishBooks"
)
public interface PublishBookRepository
    extends PagingAndSortingRepository<PublishBook, Long> {}
