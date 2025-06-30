package ktminithteam.domain;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

//<<< PoEAA / Repository
public interface ManuscriptRepository extends PagingAndSortingRepository<Manuscript, Long> {
    // 작가 ID로 해당 작가의 모든 원고를 조회하는 메서드
    List<Manuscript> findByAuthorId(@Param("authorId") Long authorId);
}
//>>> PoEAA / Repository