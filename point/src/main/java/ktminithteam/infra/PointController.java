package ktminithteam.infra;

import java.util.Optional;
import javax.transaction.Transactional;

import ktminithteam.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//<<< Clean Arch / Inbound Adaptor

@RestController
@RequestMapping("/points")
@Transactional
public class PointController {

    @Autowired
    PointRepository pointRepository;

    /**
     * 구독권 구매 → RequestSubscribed 이벤트 발행
     */
    @PostMapping("/purchase")
    public ResponseEntity<String> purchaseTicket(@RequestBody PurchaseTicket command) {

        // 이벤트 객체 생성
        RequestSubscribed event = new RequestSubscribed();
        event.setSubscriberId(command.getSubscriberId());

        // 이벤트 발행 
        event.publish();

        return ResponseEntity.ok("RequestSubscribed 이벤트가 발행되었습니다.");
    }
}
//>>> Clean Arch / Inbound Adaptor
