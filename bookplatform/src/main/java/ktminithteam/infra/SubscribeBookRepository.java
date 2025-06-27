package ktminithteam.infra;

import java.util.List;
import ktminithteam.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "subscribeBooks",
    path = "subscribeBooks"
)
public interface SubscribeBookRepository
    extends PagingAndSortingRepository<SubscribeBook, Long> {}
