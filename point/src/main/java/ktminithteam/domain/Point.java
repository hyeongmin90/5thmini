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
     * @TODO 
     * 구독권을 가지고 있다면 포인트 소모 않고 구독권의 만료일을 넣어서 보내기
     * request의 id로 이벤트 발행하기 현재는 포인트 테이블의 id를 보내고 있음
     * 
     * 구독 요청 시 포인트 차감 및 구독권 발급 처리
     * -> 로직 변경 도서 구독과 구독권은 별개로 설정
     */
    public static void requestSubscribe(RequestSubscribed request) {
        repository().findBySubscriberId(request.getSubscriberId()).ifPresent(point -> {
            if(point.hasSubscriptionTicket){
                Substart substart = new Substart(point, request.getSubscribeId());
                substart.setExpirationDate(point.getSubscriptionTicketExpirationDate());
                substart.publishAfterCommit();
            }
            else if (point.getPoint() >= request.getCost()) {
                point.setPoint((int)(point.getPoint() - request.getCost()));
                // point.setHasSubscriptionTicket(true);
                // point.setSubscriptionTicketExpirationDate(LocalDate.now().plusDays(30));
                repository().save(point);

                Substart substart = new Substart(point, request.getSubscribeId());

                // substart.setExpirationDate(java.sql.Date.valueOf(point.getSubscriptionTicketExpirationDate()));
                substart.publishAfterCommit();
            } else {
                RejectSubscribe reject = new RejectSubscribe(point, request.getSubscribeId());
                reject.publishAfterCommit();
            }
        });
    }
}

