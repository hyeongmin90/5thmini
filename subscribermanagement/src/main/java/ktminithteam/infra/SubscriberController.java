package ktminithteam.infra;
import ktminithteam.domain.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/subscribers")
@Transactional
public class SubscriberController {
    @Autowired
    SubscriberRepository subscriberRepository;

    @RequestMapping(value = "/subscribers/joinmembership",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Subscriber joinMembership(HttpServletRequest request, HttpServletResponse response, 
        ) throws Exception {
            System.out.println("##### /subscriber/joinMembership  called #####");
            Subscriber subscriber = new Subscriber();
            subscriber.joinMembership();
            subscriberRepository.save(subscriber);
            return subscriber;
    }
}
//>>> Clean Arch / Inbound Adaptor
