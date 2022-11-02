package uit.javabackend.webclonethecoffeehouse.business.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import uit.javabackend.webclonethecoffeehouse.business.dto.DiscountDTO;
import uit.javabackend.webclonethecoffeehouse.business.model.Discount;
import uit.javabackend.webclonethecoffeehouse.business.repository.DiscountRepository;
import uit.javabackend.webclonethecoffeehouse.common.service.GenericService;
import uit.javabackend.webclonethecoffeehouse.common.util.TCHMapper;

import javax.validation.ValidationException;
import java.util.UUID;

@Service
public interface DiscountService extends GenericService<Discount, DiscountDTO, UUID> {

    DiscountDTO update(DiscountDTO discountDTO);
}

class DiscountServiceImp implements DiscountService {

    private final DiscountRepository repository;
    private final TCHMapper mapper;
    private final ValidationException discountIsNotExisted = new ValidationException("Discount is not existed.");

    DiscountServiceImp(DiscountRepository repository, TCHMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public DiscountDTO update(DiscountDTO discountDTO) {
        Discount curDiscount = repository.findByCode(discountDTO.getCode())
                .orElseThrow(() -> discountIsNotExisted);

        curDiscount.setCode(discountDTO.getCode());
        curDiscount.setDescription(discountDTO.getDescription());
        curDiscount.setAllowedUsers(discountDTO.getAllowedUsers());
        curDiscount.setLimitAmountOnUser(discountDTO.getLimitAmountOnUser());
        curDiscount.setEffectiveDay(discountDTO.getEffectiveDay());
        curDiscount.setExpirationDay(discountDTO.getExpirationDay());
        curDiscount.setAmountType(discountDTO.getAmountType());

        return save(curDiscount, Discount.class, DiscountDTO.class);
    }

    @Override
    public JpaRepository<Discount, UUID> getRepository() {
        return repository;
    }

    @Override
    public ModelMapper getMapper() {
        return mapper;
    }
}