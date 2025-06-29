package ktminithteam.infra;

import javax.transaction.Transactional;
import ktminithteam.domain.*;
import ktminithteam.dto.MyBookDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@Transactional
@RequestMapping(value="/mybooks")
public class MybookController {
    @Autowired SubscribeRepository subscribeRepository;
    @Autowired PublishBookRepository publishBookRepository;

    @GetMapping
    public ResponseEntity<List<MyBookDto>> getMyBooks(@RequestParam Long subscriberId) {
        // 1. 구독 정보 조회
        List<Subscribe> subscribes = subscribeRepository.findBySubscriberId(subscriberId);
        System.out.println(subscriberId);
        // 2. 구독한 출간물 ID 리스트 추출
        List<Long> publishIds = subscribes.stream()
                .map(Subscribe::getPublishId)
                .collect(Collectors.toList());

        // 3. 출간물 정보 조회 (in-bulk)
        List<PublishBook> books = publishBookRepository.findByPublishIdIn(publishIds);
        // 출간물 ID로 빠른 매칭을 위한 Map 생성
        Map<Long, PublishBook> booksMap = books.stream()
                .collect(Collectors.toMap(PublishBook::getPublishId, b -> b));

        // 4. 구독정보와 책정보를 합쳐서 DTO 생성
        List<MyBookDto> mybooks = subscribes.stream()
                .map(sub -> {
                    PublishBook book = booksMap.get(sub.getPublishId());
                    if (book == null) return null;
                    return new MyBookDto(
                        book.getTitle(),
                        book.getAuthorId(),
                        sub.getExpirationDate(),
                        book.getContent(),
                        book.getCoverUrl(),
                        book.getSummaryUrl(),
                        book.getCategory()
                    );
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(mybooks);
    }
}