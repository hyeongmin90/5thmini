package ktminithteam.infra;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import ktminithteam.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
//<<< Clean Arch / Inbound Adaptor

@RestController
@RequiredArgsConstructor
public class SubscribeController {

    private final SubscribeService subscribeService;

    @PostMapping("/subscribes")
    public Subscribe create(@RequestBody Subscribe subscribe) {
        return subscribeService.createSubscribe(subscribe);
    }
}
