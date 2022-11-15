package uit.javabackend.webclonethecoffeehouse.business.boundary;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uit.javabackend.webclonethecoffeehouse.business.dto.DiscountDTO;
import uit.javabackend.webclonethecoffeehouse.business.dto.UserDiscountDTO;
import uit.javabackend.webclonethecoffeehouse.business.model.Discount;
import uit.javabackend.webclonethecoffeehouse.business.service.DiscountService;
import uit.javabackend.webclonethecoffeehouse.business.service.UserDiscountService;
import uit.javabackend.webclonethecoffeehouse.common.util.ResponseUtil;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/UserDiscountManagement")
public class UserDiscountRestResource {
    private final UserDiscountService services;

    public UserDiscountRestResource(UserDiscountService services) {
        this.services = services;
    }

    @GetMapping("/get-all")
    public Object findAll() {
        return ResponseUtil.get(services.findAllDto(UserDiscountDTO.class), HttpStatus.OK);
    }


    @GetMapping("/{userdiscount-id}/GetUserDiscountWithDiscount")
    public Object findUserDiscountWithDiscountDTO(@PathVariable("userdiscount-id") UUID userDiscountID){
        return ResponseUtil.get(services.getUserDiscountWithDiscountDTO(userDiscountID),HttpStatus.OK);
    }

    @GetMapping("/GetAllUserDiscountWithDiscount")
    public Object findAllUserDiscountWithDiscountDTO(){
        return ResponseUtil.get(services.getAllUserDiscountWithDiscountDTO(),HttpStatus.OK);
    }


    @PostMapping(path = "/add-userdiscount")
    public Object save(@RequestBody @Valid UserDiscountDTO userDiscountDTOiscountDTO) {
        return ResponseUtil.get(services.save(userDiscountDTOiscountDTO), HttpStatus.CREATED);
    }

    @PutMapping("/update-userdiscount")
    public Object update(@RequestBody UserDiscountDTO userDiscountDTO) {
        return ResponseUtil.get(services.update(userDiscountDTO), HttpStatus.OK);
    }

    @DeleteMapping("/DeleteByUserDiscountId")
    public Object deleteProductGroup(@RequestParam ("id") UUID id){
        services.deleteById(id);
        return HttpStatus.OK;
    }


}
