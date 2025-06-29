package ktminithteam.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RecommendBookDto {
    private String title;
    private Long authorId;
    private String coverUrl;
    private String summaryUrl;
    private String category;
    private boolean isBestseller;
    private Long subscribeCount;
}
