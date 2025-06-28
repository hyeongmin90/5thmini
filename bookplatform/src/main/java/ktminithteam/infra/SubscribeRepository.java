package ktminithteam.infra;

import ktminithteam.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "subscribe",
    path = "subscribe"
)
public interface SubscribeRepository
    extends PagingAndSortingRepository<Subscribe, Long> {}
