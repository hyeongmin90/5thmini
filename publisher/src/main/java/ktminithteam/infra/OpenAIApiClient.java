package ktminithteam.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Component
@RequiredArgsConstructor
public class OpenAIApiClient {

    @Value("${openai.api-key}")
    private String apiKey;

    @Value("${openai.chat-url}")
    private String chatUrl;

    @Value("${openai.image-url}")
    private String imageUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public String generateSummary(String content) {
        return callChatGPT("다음 내용을 3줄로 요약해줘:\n" + content);
    }

    public String classifyCategory(String content) {
        return callChatGPT("다음 글의 주제를 한 단어로 분류해줘. 예시: 농업, 기술, 일상 등:\n" + content);
    }

    public String generateCover(String title) {
        Map<String, Object> body = new HashMap<>();
        body.put("prompt", title + " 책 표지 일러스트");
        body.put("n", 1);
        body.put("size", "512x512");

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        Map response = restTemplate.postForObject(imageUrl, request, Map.class);
        return (String) ((Map)((List) response.get("data")).get(0)).get("url");
    }

    private String callChatGPT(String prompt) {
        Map<String, Object> body = Map.of(
            "model", "gpt-3.5-turbo",
            "messages", List.of(Map.of("role", "user", "content", prompt))
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        Map response = restTemplate.postForObject(chatUrl, request, Map.class);

        // 수정: 응답에서 choices를 올바르게 파싱
        List choices = (List) response.get("choices");
        if (choices != null && !choices.isEmpty()) {
            Map firstChoice = (Map) choices.get(0);
            Map message = (Map) firstChoice.get("message");
            return (String) message.get("content");
        }
        return "";
    }
}
