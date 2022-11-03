package uit.javabackend.webclonethecoffeehouse.payment.boundary;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uit.javabackend.webclonethecoffeehouse.payment.service.TransactionServices;

@RestController
@RequestMapping("api/v1/transaction-management")
public class TransactionRestResource {

    private final TransactionServices services;

    public TransactionRestResource(TransactionServices services) {
        this.services = services;
    }


}
