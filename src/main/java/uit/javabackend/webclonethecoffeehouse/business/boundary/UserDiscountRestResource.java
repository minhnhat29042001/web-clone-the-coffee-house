package uit.javabackend.webclonethecoffeehouse.business.boundary;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uit.javabackend.webclonethecoffeehouse.business.dto.UserDiscountDTO;
import uit.javabackend.webclonethecoffeehouse.business.service.UserDiscountService;
import uit.javabackend.webclonethecoffeehouse.common.util.ResponseUtil;
import uit.javabackend.webclonethecoffeehouse.security.authorization.TCHOperation;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("api/UserDiscountManagement")
public class UserDiscountRestResource {
    private final UserDiscountService services;

    public UserDiscountRestResource(UserDiscountService services) {
        this.services = services;
    }

    @TCHOperation(name = "GetAllUserDiscount")
    @GetMapping("/get-all")
    public Object findAll() {
        return ResponseUtil.get(services.findAllDto(UserDiscountDTO.class), HttpStatus.OK);
    }

    @TCHOperation(name = "GetUserDiscountWithDiscount")
    @GetMapping("/{userdiscount-id}/GetUserDiscountWithDiscount")
    public Object findUserDiscountWithDiscountDTO(@PathVariable("userdiscount-id") UUID userDiscountID) {
        return ResponseUtil.get(services.getUserDiscountWithDiscountDTO(userDiscountID), HttpStatus.OK);
    }

    @TCHOperation(name = "GetAllUserDiscountWithDiscount")
    @GetMapping("/GetAllUserDiscountWithDiscount")
    public Object findAllUserDiscountWithDiscountDTO() {
        return ResponseUtil.get(services.getAllUserDiscountWithDiscountDTO(), HttpStatus.OK);
    }

    @TCHOperation(name = "AddUserDiscount")
    @PostMapping(path = "/add-userdiscount")
    public Object save(@RequestBody @Valid UserDiscountDTO userDiscountDTOiscountDTO) {
        return ResponseUtil.get(services.save(userDiscountDTOiscountDTO), HttpStatus.CREATED);
    }

    @TCHOperation(name = "UpdateUserDiscount")
    @PutMapping("/update-userdiscount")
    public Object update(@RequestBody UserDiscountDTO userDiscountDTO) {
        return ResponseUtil.get(services.update(userDiscountDTO), HttpStatus.OK);
    }

    @TCHOperation(name = "DeleteUserDiscount")
    @DeleteMapping("/DeleteByUserDiscountId")
    public Object deleteProductGroup(@RequestParam("id") UUID id) {
        services.deleteById(id);
        return HttpStatus.OK;
    }


}
