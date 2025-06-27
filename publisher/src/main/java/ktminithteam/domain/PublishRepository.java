package ktminithteam.domain;

import ktminithteam.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "publishes", path = "publishes")
public interface PublishRepository
    extends PagingAndSortingRepository<Publish, Long> {}
