package ktminithteam.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MyBookDto {
    private String title;
    private Long authorId;
    private Date expirationDate;
    private String content;
    private String coverUrl;
    private String summaryUrl;
    private String category;
}
