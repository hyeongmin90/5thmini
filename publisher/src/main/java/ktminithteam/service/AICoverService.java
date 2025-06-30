package ktminithteam.service;

import org.springframework.stereotype.Service;

@Service
public class AICoverService {

    public String generateCover(String title, String category) {
        // TODO: 실제로는 OpenAI API(DALL·E 등)를 호출해야 함
        // 지금은 더미 URL 반환
        return "https://dummy.image.server/cover/" + title.replace(" ", "_") + "_" + category;
    }
}
