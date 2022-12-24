package uit.javabackend.webclonethecoffeehouse.business.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uit.javabackend.webclonethecoffeehouse.business.dto.DiscountDTO;
import uit.javabackend.webclonethecoffeehouse.business.dto.DiscountWithUserDiscountDTO;
import uit.javabackend.webclonethecoffeehouse.business.dto.UserDiscountDTO;
import uit.javabackend.webclonethecoffeehouse.business.model.Discount;
import uit.javabackend.webclonethecoffeehouse.business.model.UserDiscount;
import uit.javabackend.webclonethecoffeehouse.business.repository.DiscountRepository;
import uit.javabackend.webclonethecoffeehouse.common.exception.TCHBusinessException;
import uit.javabackend.webclonethecoffeehouse.common.service.GenericService;
import uit.javabackend.webclonethecoffeehouse.common.util.TCHMapper;
import uit.javabackend.webclonethecoffeehouse.security.oauth.user.UserPrinciple;
import uit.javabackend.webclonethecoffeehouse.user.dto.UserDTO;
import uit.javabackend.webclonethecoffeehouse.user.service.UserService;

import java.time.LocalDateTime;
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

    DiscountDTO checkCoupon(String codeDiscount,int totalPriceOfOrder);
    Optional<Discount> findByCode(String name);

}

@Service
@Transactional
class DiscountServiceImp implements DiscountService {

    private final DiscountRepository repository;
    private final TCHMapper mapper;
    private final UserService userService;
    private final UserDiscountService userDiscountService;
    private final TCHBusinessException discountIsNotExisted = new TCHBusinessException("Discount is not existed.");

    @Override
    public JpaRepository<Discount, UUID> getRepository() {
        return repository;
    }

    @Override
    public ModelMapper getMapper() {
        return mapper;
    }

    DiscountServiceImp(DiscountRepository repository, TCHMapper mapper, UserService userService, UserDiscountService userDiscountService) {
        this.repository = repository;
        this.mapper = mapper;
        this.userService = userService;
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
        curDiscount.setDiscountAmount(discountDTO.getDiscountAmount());
        curDiscount.setMinimumPriceOnOrder(discountDTO.getMinimumPriceOnOrder());

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
    public DiscountDTO checkCoupon(String codeDiscount, int totalPriceOfOrder) {
        Discount discount = repository.findByCode(codeDiscount).orElseThrow(() -> discountIsNotExisted);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime effectiveDay = discount.getEffectiveDay();
        LocalDateTime expirationDay = discount.getExpirationDay();
        boolean checkEf = now.isAfter(effectiveDay);
        boolean checkEx = now.isBefore(expirationDay);

        if(!checkEf ){
            throw new TCHBusinessException("chua toi thoi gian su dung");
        }
        if(!checkEx){
            throw new TCHBusinessException("da het han su dung");
        }

        if(discount.getNumbersOfUsers() == 0){
            throw new TCHBusinessException("da het so luong su dung");
        }

        if(discount.getMinimumPriceOnOrder() > totalPriceOfOrder){
            throw new TCHBusinessException("khong du dieu kien gia don hang");
        }

       String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDTO user = userService.getUserByUsername(principal);


        Optional<UserDiscount> userDiscount = userDiscountService.findUserDiscountByUserIdAndDiscountId(user.getId(),discount.getId());
        if(userDiscount.isPresent()){
            UserDiscountDTO userDiscountDTO = mapper.map(userDiscount.get(),UserDiscountDTO.class);
            if(userDiscountDTO.getUsedCount() == 1){
                throw new TCHBusinessException("user da su dung discount nay roi");
            }
        }

        DiscountDTO discountDTO = getMapper().map(discount,DiscountDTO.class);
        return discountDTO;
    }

    @Override
    public Optional<Discount> findByCode(String name) {
       return repository.findByCode(name);
    }


}