package uit.javabackend.webclonethecoffeehouse.business.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uit.javabackend.webclonethecoffeehouse.business.dto.DiscountDTO;
import uit.javabackend.webclonethecoffeehouse.business.dto.DiscountWithUserDiscountDTO;
import uit.javabackend.webclonethecoffeehouse.business.dto.UserDiscountDTO;
import uit.javabackend.webclonethecoffeehouse.business.dto.UserDiscountWithDiscountDTO;
import uit.javabackend.webclonethecoffeehouse.business.model.Discount;
import uit.javabackend.webclonethecoffeehouse.business.model.UserDiscount;
import uit.javabackend.webclonethecoffeehouse.business.repository.UserDiscountRepository;
import uit.javabackend.webclonethecoffeehouse.common.service.GenericService;
import uit.javabackend.webclonethecoffeehouse.common.util.TCHMapper;
import uit.javabackend.webclonethecoffeehouse.product.dto.ProductGroupDTO;
import uit.javabackend.webclonethecoffeehouse.product.dto.ProductGroupWithProductsDTO;
import uit.javabackend.webclonethecoffeehouse.product.model.ProductGroup;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public interface UserDiscountService extends GenericService<UserDiscount, UserDiscountDTO, UUID> {

    UserDiscountDTO save(UserDiscountDTO userDiscountDTO);

    @Override
    void deleteById(UUID id);
    UserDiscount update(UserDiscountDTO userDiscountDTO);
    UserDiscountWithDiscountDTO getUserDiscountWithDiscountDTO (UUID userDiscountId);
    List<UserDiscountWithDiscountDTO> getAllUserDiscountWithDiscountDTO ();

}

@Service
@Transactional
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
    public UserDiscountDTO save(UserDiscountDTO userDiscountDTO) {
        UserDiscount userDiscount = mapper.map(userDiscountDTO,UserDiscount.class);
        UserDiscount saveduserDiscount = repository.save(userDiscount);
        return mapper.map(saveduserDiscount,UserDiscountDTO.class);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public UserDiscount update(UserDiscountDTO userDiscountDTO) {
        UserDiscount userDiscount =repository.findById(userDiscountDTO.getId()).orElseThrow(()-> userDiscountIsNotExisted);
        userDiscount.setDescription(userDiscountDTO.getDescription());
        userDiscount.setUsedCount(userDiscountDTO.getUsedCount());
        return repository.save(userDiscount);
    }

    @Override
    public UserDiscountWithDiscountDTO getUserDiscountWithDiscountDTO(UUID userDiscountId) {
        UserDiscount userDiscount = repository.findById(userDiscountId).orElseThrow(()->
                userDiscountIsNotExisted
        );
        return mapper.map(userDiscount,UserDiscountWithDiscountDTO.class);
    }

    @Override
    public List<UserDiscountWithDiscountDTO> getAllUserDiscountWithDiscountDTO() {
        List<UserDiscount> userDiscountList = repository.findAll();
        List<UserDiscountWithDiscountDTO> userDiscountWithDiscountDTOList = new ArrayList<>();
        userDiscountList.forEach(
                userDiscount ->{
                    UserDiscountWithDiscountDTO userDiscountWithDiscountDTO = mapper.map(userDiscount,UserDiscountWithDiscountDTO.class);
                    userDiscountWithDiscountDTOList.add(userDiscountWithDiscountDTO);
                }
        );
        return userDiscountWithDiscountDTOList;
    }
}