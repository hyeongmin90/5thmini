package ktminithteam.infra;

import ktminithteam.domain.*;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "subscribes",
    path = "subscribes"
)
public interface SubscribeRepository
    extends PagingAndSortingRepository<Subscribe, Long> {

    List<Subscribe> findBySubscriberId(Long subscriberId);}
