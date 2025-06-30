package ktminithteam.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import ktminithteam.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//<<< Clean Arch / Inbound Adaptor

@RestController
@RequestMapping(value="/subscribers")
@Transactional
public class SubscriberController {

    @Autowired
    SubscriberRepository subscriberRepository;
    // 회원 가입 커맨드
    @PostMapping("/joinmembership")
    public ResponseEntity<Subscriber> joinMembership(@RequestBody Subscriber subscriber) throws Exception {
            System.out.println("##### /subscriber/joinMembership  called #####");
            String email = subscriber.getEmail();
            if (subscriberRepository.findByEmail(email).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            subscriberRepository.save(subscriber);
            return ResponseEntity.ok(subscriber);
    }

    // kt 인증 커맨드
    @PatchMapping("/{subscriberId}/kt-auth")
    public ResponseEntity<Subscriber> ktAuth(
            @PathVariable Long subscriberId,
            @RequestBody Map<String, String> body) {

        String phoneNumber = body.get("phoneNumber"); // 요청에서 phonenumber 추출

        Subscriber subscriber = subscriberRepository.findById(subscriberId).orElse(null);

        // KT가 아닌 경우만 허용
        if ("KT".equalsIgnoreCase(subscriber.getTelecom())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        subscriber.setTelecom("KT");
        subscriber.setPhoneNumber(phoneNumber);
        subscriberRepository.save(subscriber);
        Verified verified = new Verified(subscriber);
        verified.publishAfterCommit();
        return ResponseEntity.ok(subscriber);
    }
}
//>>> Clean Arch / Inbound Adaptor
