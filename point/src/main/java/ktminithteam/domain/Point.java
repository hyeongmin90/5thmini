package ktminithteam.domain;

import javax.persistence.*;
import ktminithteam.PointApplication;
import ktminithteam.infra.AbstractEvent;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "Point_table")
@Data
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long subscriberId;
    private Integer point;
    private Boolean hasSubscriptionTicket;
    private LocalDate subscriptionTicketExpirationDate;


    public static PointRepository repository() {
        return PointApplication.applicationContext.getBean(PointRepository.class);
    }

    /**
     * 회원가입 시 포인트 지급 / KT 회원 인증 시 5000 추가 지급
     */
    public static void pointIncrease(SignedUp signedUp) {
        Point point = new Point();
        point.setSubscriberId(signedUp.getSubscriberId());

        int basePoint = 1000;
        if ("KT".equalsIgnoreCase(signedUp.getTelecom())) {
            basePoint += 5000;
        }

        point.setPoint(basePoint);
        point.setHasSubscriptionTicket(false);
        repository().save(point);
    }
    /**
     * KT 인증시 포인트 지급
     */
    public static void pointIncrease_verified(Verified verified) {
        repository().findBySubscriberId(verified.getSubscriberId()).ifPresent(subscriber -> {
            subscriber.setPoint(subscriber.getPoint() + 5000);
            
            repository().save(subscriber);
        });
    }


    /**
     * 구독 요청 시 포인트 차감 및 구독권 발급 처리
     * -> 로직 변경 도서 구독과 구독권은 별개로 설정
     */
    public static void requestSubscribe(RequestSubscribed request) {
        repository().findBySubscriberId(request.getSubscriberId()).ifPresent(point -> {
            if (point.getPoint() >= request.getCost()) {
                point.setPoint((int)(point.getPoint() - request.getCost()));
                // point.setHasSubscriptionTicket(true);
                // point.setSubscriptionTicketExpirationDate(LocalDate.now().plusDays(30));
                repository().save(point);

                Substart substart = new Substart(point);
                // substart.setExpirationDate(java.sql.Date.valueOf(point.getSubscriptionTicketExpirationDate()));
                substart.publishAfterCommit();
            } else {
                RejectSubscribe reject = new RejectSubscribe(point);
                reject.publishAfterCommit();
            }
        });
    }
    /**
     * 구독권(티켓) 구매
     */
    public static void purchaseTicket(){

    }
}

