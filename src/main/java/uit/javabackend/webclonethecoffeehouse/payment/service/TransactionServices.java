package uit.javabackend.webclonethecoffeehouse.payment.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import uit.javabackend.webclonethecoffeehouse.common.service.GenericService;
import uit.javabackend.webclonethecoffeehouse.common.util.TCHMapper;
import uit.javabackend.webclonethecoffeehouse.payment.dto.TransactionDTO;
import uit.javabackend.webclonethecoffeehouse.payment.model.Transaction;
import uit.javabackend.webclonethecoffeehouse.payment.repository.TransactionRepository;

import java.util.UUID;

public interface TransactionServices extends GenericService<Transaction, TransactionDTO, UUID> {

}

@Service
class TransactionServicesImp implements TransactionServices {

    private final TransactionRepository repository;
    private final TCHMapper mapper;

    TransactionServicesImp(TransactionRepository repository, TCHMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    @Override
    public JpaRepository<Transaction, UUID> getRepository() {
        return repository;
    }

    @Override
    public ModelMapper getMapper() {
        return mapper;
    }
}
