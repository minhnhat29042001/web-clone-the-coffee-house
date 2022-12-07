package uit.javabackend.webclonethecoffeehouse.payment.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uit.javabackend.webclonethecoffeehouse.common.service.GenericService;
import uit.javabackend.webclonethecoffeehouse.common.util.TCHMapper;
import uit.javabackend.webclonethecoffeehouse.payment.dto.PaymentDTO;
import uit.javabackend.webclonethecoffeehouse.payment.model.Payment;
import uit.javabackend.webclonethecoffeehouse.payment.repository.PaymentRepository;

import javax.validation.ValidationException;
import java.util.UUID;


public interface PaymentServices extends GenericService<Payment, PaymentDTO, UUID> {

    PaymentDTO update(PaymentDTO paymentDTO);

    void deleteByName(String name);

}

@Service
@Transactional
class PaymentServicesImp implements PaymentServices {

    private final PaymentRepository repository;
    private final TCHMapper mapper;
    private final ValidationException paymentIsNotExisted = new ValidationException("Payment method is not existed.");


    PaymentServicesImp(PaymentRepository repository, TCHMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    @Override
    public JpaRepository<Payment, UUID> getRepository() {
        return repository;
    }

    @Override
    public ModelMapper getMapper() {
        return mapper;
    }

    @Override
    public PaymentDTO update(PaymentDTO paymentDTO) {

        Payment curPayment = repository.findByName(paymentDTO.getName())
                .orElseThrow(() -> paymentIsNotExisted);

        curPayment.setName(paymentDTO.getName());
        curPayment.setCode(paymentDTO.getCode());
        curPayment.setDescription(paymentDTO.getDescription());
        curPayment.setMethod(paymentDTO.getMethod());
        curPayment.setPartnerCode(paymentDTO.getPartnerCode());
        curPayment.setPrivateKey(paymentDTO.getPrivateKey());
        curPayment.setPublicKey(paymentDTO.getPublicKey());

        return save(curPayment, Payment.class, PaymentDTO.class);
    }

    @Override
    public void deleteByName(String name) {
        repository.deleteByName(name);
    }

}
