package uit.javabackend.webclonethecoffeehouse.business.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import uit.javabackend.webclonethecoffeehouse.business.dto.UserDiscountDTO;
import uit.javabackend.webclonethecoffeehouse.business.model.UserDiscount;
import uit.javabackend.webclonethecoffeehouse.business.repository.UserDiscountRepository;
import uit.javabackend.webclonethecoffeehouse.common.service.GenericService;
import uit.javabackend.webclonethecoffeehouse.common.util.TCHMapper;

import javax.validation.ValidationException;
import java.util.List;
import java.util.UUID;

@Service
public interface UserDiscountService extends GenericService<UserDiscount, UserDiscountDTO, UUID> {

    List<UserDiscount> findAll(List<UUID> userDiscountIds);

}


class UserDiscountServiceImp implements UserDiscountService {

    private final UserDiscountRepository repository;
    private final TCHMapper mapper;
    private final ValidationException userDiscountIsNotExisted = new ValidationException("userdiscount is not existed.");

    UserDiscountServiceImp(UserDiscountRepository repository, TCHMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    @Override
    public JpaRepository<UserDiscount, UUID> getRepository() {
        return repository;
    }

    @Override
    public ModelMapper getMapper() {
        return mapper;
    }

    @Override
    public List<UserDiscount> findAll(List<UUID> userDiscountIds) {
        return repository.findAllById(userDiscountIds);
    }
}