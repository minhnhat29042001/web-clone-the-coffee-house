package uit.javabackend.webclonethecoffeehouse.business.boundary;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uit.javabackend.webclonethecoffeehouse.business.dto.DiscountDTO;
import uit.javabackend.webclonethecoffeehouse.business.model.Discount;
import uit.javabackend.webclonethecoffeehouse.business.service.DiscountService;
import uit.javabackend.webclonethecoffeehouse.common.util.ResponseUtil;
import uit.javabackend.webclonethecoffeehouse.security.authorization.TCHOperation;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/discount")
public class DiscountRestResource {

    private final DiscountService services;

    public DiscountRestResource(DiscountService services) {
        this.services = services;
    }

    @GetMapping("common/get-all")
    public Object findAll() {
        return ResponseUtil.get(services.findAllDto(DiscountDTO.class), HttpStatus.OK);
    }

    @TCHOperation(name = "GetDiscountWithUserDiscount")
    @GetMapping("/{discount-id}/GetDiscountWithUserDiscount")
    public Object findDiscountWithUserDiscountDTO(@PathVariable("discount-id") UUID discountID) {
        return ResponseUtil.get(services.getDiscountWithUserDiscountDTO(discountID), HttpStatus.OK);
    }

    @TCHOperation(name = "GetAllDiscountWithUserDiscount")
    @GetMapping("/GetAllDiscountWithUserDiscount")
    public Object findAllDiscountWithUserDiscountDTO() {
        return ResponseUtil.get(services.getAllDiscountWithUserDiscountDTO(), HttpStatus.OK);
    }

    @TCHOperation(name = "AddDiscount")
    @PostMapping(path = "/add-discount")
    public Object save(@RequestBody @Valid DiscountDTO discountDTO) {
        return ResponseUtil.get(services.save(discountDTO, Discount.class, DiscountDTO.class), HttpStatus.CREATED);
    }

    @TCHOperation(name = "UpdateDiscount")
    @PutMapping("/update-discount")
    public Object update(@RequestBody DiscountDTO discountDTO) {
        return ResponseUtil.get(services.update(discountDTO), HttpStatus.OK);
    }

    @TCHOperation(name = "DeleteDiscount")
    @DeleteMapping("/DeleteByCodeDiscount")
    public Object deleteProductGroup(@RequestParam("code") String code) {
        services.deleteByCode(code);
        return HttpStatus.OK;
    }

    @TCHOperation(name = "AddUserDiscounts")
    @PostMapping("/{discount-id}/AddUserDiscounts")
    public ResponseEntity<?> addUserDiscount(@RequestBody List<UUID> ids, @PathVariable("discount-id") UUID discountId) {
        return ResponseUtil.get(services.addUserDiscount(ids, discountId), HttpStatus.CREATED);
    }

    @TCHOperation(name = "RemoveUserDiscounts")
    @DeleteMapping("/{discount-id}/RemoveUserDiscounts")
    public ResponseEntity<?> deleteUserDiscount(@RequestBody List<UUID> ids, @PathVariable("discount-id") UUID discountId) {
        return ResponseUtil.get(services.removeUserDiscount(ids, discountId), HttpStatus.OK);
    }

    @GetMapping ("/check-coupon")
    public ResponseEntity<?> checkCoupon(@RequestParam("code") String code) {
        return ResponseUtil.get(services.checkCoupon(code), HttpStatus.OK);
    }


}
