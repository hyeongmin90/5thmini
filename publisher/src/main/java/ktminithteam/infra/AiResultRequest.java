package ktminithteam.infra;

import lombok.Data;

@Data
public class AiResultRequest {
    private String summaryUrl;
    private String coverUrl;
    private String category;
}
