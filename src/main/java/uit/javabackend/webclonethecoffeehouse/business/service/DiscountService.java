package uit.javabackend.webclonethecoffeehouse.business.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uit.javabackend.webclonethecoffeehouse.business.dto.DiscountDTO;
import uit.javabackend.webclonethecoffeehouse.business.dto.DiscountWithUserDiscountDTO;
import uit.javabackend.webclonethecoffeehouse.business.model.Discount;
import uit.javabackend.webclonethecoffeehouse.business.model.UserDiscount;
import uit.javabackend.webclonethecoffeehouse.business.repository.DiscountRepository;
import uit.javabackend.webclonethecoffeehouse.common.service.GenericService;
import uit.javabackend.webclonethecoffeehouse.common.util.TCHMapper;
import uit.javabackend.webclonethecoffeehouse.product.dto.ProductGroupWithProductsDTO;
import uit.javabackend.webclonethecoffeehouse.product.model.ProductGroup;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface DiscountService extends GenericService<Discount, DiscountDTO, UUID> {

    DiscountDTO save(DiscountDTO discountDTO);
    void deleteByCode(String code);
    DiscountDTO update(DiscountDTO discountDTO);
    DiscountWithUserDiscountDTO addUserDiscount(List<UUID> ids, UUID discountID);
    DiscountWithUserDiscountDTO removeUserDiscount(List<UUID> ids, UUID discountId);
    DiscountWithUserDiscountDTO getDiscountWithUserDiscountDTO (UUID discountId);
    List<DiscountWithUserDiscountDTO>getAllDiscountWithUserDiscountDTO ();

    DiscountDTO checkCoupon(String codeDiscount);

}

@Service
@Transactional
class DiscountServiceImp implements DiscountService {

    private final DiscountRepository repository;
    private final TCHMapper mapper;
    private final UserDiscountService userDiscountService;
    private final ValidationException discountIsNotExisted = new ValidationException("Discount is not existed.");

    @Override
    public JpaRepository<Discount, UUID> getRepository() {
        return repository;
    }

    @Override
    public ModelMapper getMapper() {
        return mapper;
    }

    DiscountServiceImp(DiscountRepository repository, TCHMapper mapper, UserDiscountService userDiscountService) {
        this.repository = repository;
        this.mapper = mapper;
        this.userDiscountService = userDiscountService;
    }

    @Override
    public DiscountDTO save(DiscountDTO discountDTO) {
        Discount discount = mapper.map(discountDTO,Discount.class);
        Discount savedDiscount = repository.save(discount);
        return mapper.map(savedDiscount,DiscountDTO.class);
    }

    @Override
    public void deleteByCode(String code) {
        repository.deleteByCode(code);
    }


    @Override
    public DiscountDTO update(DiscountDTO discountDTO) {
        Discount curDiscount = repository.findByCode(discountDTO.getCode())
                .orElseThrow(() -> discountIsNotExisted);

        curDiscount.setCode(discountDTO.getCode());
        curDiscount.setDescription(discountDTO.getDescription());
        curDiscount.setImageUrl(discountDTO.getImageUrl());
        curDiscount.setNumbersOfUsers(discountDTO.getNumbersOfUsers());
        curDiscount.setLimitAmountOnUser(discountDTO.getLimitAmountOnUser());
        curDiscount.setEffectiveDay(discountDTO.getEffectiveDay());
        curDiscount.setExpirationDay(discountDTO.getExpirationDay());
        curDiscount.setAmountType(discountDTO.getAmountType());

        return save(curDiscount, Discount.class, DiscountDTO.class);
    }

    @Override
    public DiscountWithUserDiscountDTO addUserDiscount(List<UUID> ids, UUID discountId) {
        Discount discount = repository.findById(discountId).orElseThrow(()->
                discountIsNotExisted
        );
        List<UserDiscount> userDiscountList = userDiscountService.findByIds(ids);
        userDiscountList.forEach(discount::addUserDiscount);
        return mapper.map(discount,DiscountWithUserDiscountDTO.class);
    }

    @Override
    public DiscountWithUserDiscountDTO removeUserDiscount(List<UUID> ids, UUID discountId) {
        Discount discount = repository.findById(discountId).orElseThrow(()->
                discountIsNotExisted
        );
        List<UserDiscount> userDiscountList = userDiscountService.findByIds(ids);
        userDiscountList.forEach(discount::removeUserDiscount);
        return mapper.map(discount,DiscountWithUserDiscountDTO.class);
    }

    @Override
    public DiscountWithUserDiscountDTO getDiscountWithUserDiscountDTO(UUID discountId) {
        Discount discount = repository.findById(discountId).orElseThrow(()->
                discountIsNotExisted
        );
        return mapper.map(discount, DiscountWithUserDiscountDTO.class);
    }

    @Override
    public List<DiscountWithUserDiscountDTO> getAllDiscountWithUserDiscountDTO() {
        List<Discount> discountList = repository.findAll();
        List<DiscountWithUserDiscountDTO> discountWithUserDiscountDTOList = new ArrayList<>();
        discountList.forEach(
                discount ->{
                    DiscountWithUserDiscountDTO discountWithUserDiscountDTO = mapper.map(discount,DiscountWithUserDiscountDTO.class);
                    discountWithUserDiscountDTOList.add(discountWithUserDiscountDTO);
                }
        );
        return discountWithUserDiscountDTOList;
    }

    @Override
    public DiscountDTO checkCoupon(String codeDiscount) {
        Optional<Discount> discount = repository.findByCode(codeDiscount);
        if(discount.isEmpty()){
            throw discountIsNotExisted;
        }
        return getMapper().map(discount,DiscountDTO.class);
    }


}