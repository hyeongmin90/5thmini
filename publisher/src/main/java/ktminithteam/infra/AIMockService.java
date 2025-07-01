package ktminithteam.infra;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
public class AIMockService {

    @PostMapping("/summary")
    public String summary(@RequestBody String content) {
        return "요약된 내용: " + content.substring(0, Math.min(content.length(), 30));
    }

    @PostMapping("/category")
    public String category(@RequestBody String content) {
        return content.contains("농업") ? "농업" : "일반";
    }

    @PostMapping("/cover")
    public String cover(@RequestBody String title) {
        return "https://dummyimage.com/600x800/cccccc/000000&text=" + title;
    }
}
