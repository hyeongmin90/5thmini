package ktminithteam.infra;

import javax.transaction.Transactional;
import ktminithteam.domain.*;
import ktminithteam.dto.MyBookDto;
import ktminithteam.dto.RecommendBookDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@Transactional
public class PlatformController {
    @Autowired SubscribeRepository subscribeRepository;
    @Autowired PublishBookRepository publishBookRepository;
    @Autowired BookRepository bookRepository;

    @GetMapping(value="/mybooks")
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

    @GetMapping(value="recommendbooks")
    public Page<RecommendBookDto> getRecommendBooks(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "subscribeCount"));
        Page<Book> bookPage = bookRepository.findAll(pageable);
        List<Long> publishIds = bookPage
                .stream()
                .map(Book::getPublishId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        
        List<PublishBook> publishBooks = publishBookRepository.findByPublishIdIn(publishIds);
        Map<Long, PublishBook> publishBookMap = publishBooks.stream()
                .collect(Collectors.toMap(PublishBook::getPublishId, pb -> pb));
        
        return bookPage.map(book -> {
            PublishBook pub = publishBookMap.get(book.getPublishId());
            return new RecommendBookDto(
                    pub.getTitle(),
                    pub.getAuthorId(),
                    pub.getCoverUrl(),
                    pub.getSummaryUrl(),
                    pub.getCategory(),
                    book.isBestseller(),
                    book.getSubscribeCount()
            );
        });
    }
}