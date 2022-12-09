package uit.javabackend.webclonethecoffeehouse.vnp_payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uit.javabackend.webclonethecoffeehouse.vnp_payment.model.VnpayPayment;

import java.util.UUID;

@Repository
public interface VnpayPaymentRepository extends JpaRepository<VnpayPayment, UUID> {

}
