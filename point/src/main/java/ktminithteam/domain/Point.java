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
     * 회원가입 시 포인트 지급
     */
    public static void 포인트지급(SignedUp signedUp) {
        Point point = new Point();
        point.setSubscriberId(signedUp.getId());
        point.setPoint(1000); // 기본 포인트 1000 지급
        point.setHasSubscriptionTicket(false);
        repository().save(point);
    }

    /**
     * 구독 요청 시 포인트 차감 및 구독권 발급 처리
     */
    public static void requestSubscribe(RequestSubscribed request) {
        repository().findBySubscriberId(request.getSubscriberId()).ifPresent(point -> {
            if (point.getPoint() >= request.getCost()) {
                point.setPoint((int)(point.getPoint() - request.getCost()));
                point.setHasSubscriptionTicket(true);
                point.setSubscriptionTicketExpirationDate(LocalDate.now().plusDays(30));
                repository().save(point);

                Substart substart = new Substart(point);
                substart.setExpirationDate(java.sql.Date.valueOf(point.getSubscriptionTicketExpirationDate()));
                substart.publishAfterCommit();
            } else {
                RejectSubscribe reject = new RejectSubscribe(point);
                reject.publishAfterCommit();
            }
        });
    }
}

