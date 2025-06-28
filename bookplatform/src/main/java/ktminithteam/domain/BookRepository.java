package ktminithteam.domain;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "Books",
    path = "Books"
)
public interface BookRepository
    extends PagingAndSortingRepository<Book, Long> {

    Optional<Book> findByPublishId(Long publishId);}
