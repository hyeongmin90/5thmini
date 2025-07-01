package ktminithteam.infra;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ktminithteam.domain.PointRepository;
import ktminithteam.domain.PurchaseTicket;
import ktminithteam.domain.Point;
import java.util.Optional;

@Service
@Transactional
public class PointService {

    private final PointRepository pointRepository;

    public PointService(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    public void handleTicketPurchase(PurchaseTicket command) {
        // 구독자 찾기
        Optional<Point> optionalPoint = pointRepository.findBySubscriberId(command.getSubscriberId());

        if (optionalPoint.isPresent()) {
            Point subscriber = optionalPoint.get();

            // 구독 상태 업데이트
            subscriber.setHasSubscriptionTicket(true);

            // 변경사항 저장
            pointRepository.save(subscriber);
        } else {
            throw new RuntimeException("해당 구독자를 찾을 수 없습니다: " + command.getSubscriberId());
        }
    }
}
