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
import ktminithteam.dto.LoginRequest;
import ktminithteam.dto.SubscriberResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//<<< Clean Arch / Inbound Adaptor

@RestController
@RequestMapping(value = "/subscribers")
@Transactional
public class SubscriberController {

    @Autowired
    SubscriberRepository subscriberRepository;

    // 회원 가입 커맨드
    @PostMapping("/joinmembership")
    public ResponseEntity<SubscriberResponse> joinMembership(@RequestBody Subscriber subscriber) throws Exception {
        System.out.println("##### /subscriber/joinMembership  called #####");
        String email = subscriber.getEmail();
        if (subscriberRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        subscriber.setIsRecommended(false); // 기본값 false로 설정
        subscriberRepository.save(subscriber);
        SubscriberResponse response = new SubscriberResponse(
                subscriber.getSubscriberId(),
                subscriber.getName(),
                subscriber.getEmail(),
                subscriber.getPhoneNumber(),
                subscriber.getIsRecommended()
        );
        return ResponseEntity.ok(response);
    }

    // kt 인증 커맨드
    @PatchMapping("/{subscriberId}/kt-auth")
    public ResponseEntity<Map<String, Object>> ktAuth(
            @PathVariable Long subscriberId,
            @RequestBody Map<String, String> body) {

        String phoneNumber = body.get("phoneNumber");
        Subscriber subscriber = subscriberRepository.findById(subscriberId).orElse(null);

        if (subscriber == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("success", false, "message", "구독자를 찾을 수 없습니다."));
        }

        // KT가 아닌 경우만 허용
        if ("KT".equalsIgnoreCase(subscriber.getTelecom())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", "이미 KT 인증된 사용자입니다."));
        }

        subscriber.setTelecom("KT");
        subscriber.setPhoneNumber(phoneNumber);
        subscriberRepository.save(subscriber);
        Verified verified = new Verified(subscriber);
        verified.publishAfterCommit();
        return ResponseEntity.ok(Map.of("success", true));
    }

    // 로그인 API
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<Subscriber> optionalSubscriber = subscriberRepository.findByEmailAndPassword(request.getEmail(), request.getPassword());
        if (optionalSubscriber.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("success", false, "message", "이메일 또는 비밀번호가 올바르지 않습니다."));
        }
        Subscriber subscriber = optionalSubscriber.get();
        SubscriberResponse response = new SubscriberResponse(
                subscriber.getSubscriberId(),
                subscriber.getName(),
                subscriber.getEmail(),
                subscriber.getPhoneNumber(),
                subscriber.getIsRecommended()
        );
        return ResponseEntity.ok(response);
    }

    // 구독자 단일 조회 API
    @GetMapping("/{subscriberId}")
    public ResponseEntity<?> getSubscriber(@PathVariable Long subscriberId) {
        Optional<Subscriber> optionalSubscriber = subscriberRepository.findById(subscriberId);
        if (optionalSubscriber.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("success", false, "message", "구독자를 찾을 수 없습니다."));
        }
        Subscriber subscriber = optionalSubscriber.get();
        SubscriberResponse response = new SubscriberResponse(
                subscriber.getSubscriberId(),
                subscriber.getName(),
                subscriber.getEmail(),
                subscriber.getPhoneNumber(),
                subscriber.getIsRecommended()
        );
        return ResponseEntity.ok(response);
    }

    // 추천 끄기 API
    @PatchMapping("/{subscriberId}/recommend-off")
    public ResponseEntity<?> recommendOff(@PathVariable Long subscriberId) {
        Optional<Subscriber> optionalSubscriber = subscriberRepository.findById(subscriberId);
        if (optionalSubscriber.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("success", false, "message", "구독자를 찾을 수 없습니다."));
        }
        Subscriber subscriber = optionalSubscriber.get();
        subscriber.setIsRecommended(false);
        subscriberRepository.save(subscriber);
        return ResponseEntity.ok(Map.of("success", true));
    }
}
//>>> Clean Arch / Inbound Adaptor
