package uit.javabackend.webclonethecoffeehouse.payment.boundary;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uit.javabackend.webclonethecoffeehouse.common.util.ResponseUtil;
import uit.javabackend.webclonethecoffeehouse.payment.dto.PaymentDTO;
import uit.javabackend.webclonethecoffeehouse.payment.model.Payment;
import uit.javabackend.webclonethecoffeehouse.payment.service.PaymentServices;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/manage-payment-methods")
public class PaymentRestResource {

    private final PaymentServices services;

    public PaymentRestResource(PaymentServices services) {
        this.services = services;
    }

    @GetMapping("/get-all")
    public Object findAll() {
        return ResponseUtil.get(services.findAllDto(PaymentDTO.class), HttpStatus.OK);
    }

    @PostMapping(path = "/add-payment")
    public Object save(@RequestBody @Valid PaymentDTO paymentDTO) {
        return ResponseUtil.get(services.save(paymentDTO, Payment.class, PaymentDTO.class), HttpStatus.CREATED);
    }

    @PutMapping("/update-payment")
    public Object update(@RequestBody PaymentDTO paymentDTO) {
        return ResponseUtil.get(services.update(paymentDTO), HttpStatus.OK);
    }

    @DeleteMapping("/delete-payment")
    public Object delete(@RequestParam("name") String name) {
        services.deleteByName(name);
        return HttpStatus.OK;
    }
}
