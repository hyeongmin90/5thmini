package ktminithteam.infra;

import ktminithteam.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(
    collectionResourceRel = "publishbooks",
    path = "publishbooks"
)
public interface PublishBookRepository
    extends PagingAndSortingRepository<PublishBook, Long> {

    Optional<PublishBook> findByPublishId(Long publishId);

    List<PublishBook> findByPublishIdIn(List<Long> publishIds);
}
