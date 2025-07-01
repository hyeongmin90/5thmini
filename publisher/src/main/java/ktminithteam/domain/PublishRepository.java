package ktminithteam.domain;

import ktminithteam.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "publishes", path = "publishes")
public interface PublishRepository
    extends PagingAndSortingRepository<Publish, Long> {
    // authorId로 출간 목록 전체 조회
    List<Publish> findAllByAuthorId(Long authorId);
}
