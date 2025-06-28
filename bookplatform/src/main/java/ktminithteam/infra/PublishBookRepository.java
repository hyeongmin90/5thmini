package ktminithteam.infra;

import ktminithteam.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.Optional;

@RepositoryRestResource(
    collectionResourceRel = "publishBooks",
    path = "publishBooks"
)
public interface PublishBookRepository
    extends PagingAndSortingRepository<PublishBook, Long> {

    Optional<PublishBook> findByPublishId(Long publishId);}
