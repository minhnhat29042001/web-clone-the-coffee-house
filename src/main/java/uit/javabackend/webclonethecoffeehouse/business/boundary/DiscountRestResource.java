package uit.javabackend.webclonethecoffeehouse.business.boundary;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uit.javabackend.webclonethecoffeehouse.business.dto.DiscountDTO;
import uit.javabackend.webclonethecoffeehouse.business.model.Discount;
import uit.javabackend.webclonethecoffeehouse.business.service.DiscountService;
import uit.javabackend.webclonethecoffeehouse.common.util.ResponseUtil;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/discount")
public class DiscountRestResource {

    private final DiscountService services;

    public DiscountRestResource(DiscountService services) {
        this.services = services;
    }

    @GetMapping("/get-all")
    public Object findAll() {
        return ResponseUtil.get(services.findAllDto(DiscountDTO.class), HttpStatus.OK);
    }

    @PostMapping(path = "/add-info")
    public Object save(@RequestBody @Valid DiscountDTO discountDTO) {
        return ResponseUtil.get(services.save(discountDTO, Discount.class, DiscountDTO.class), HttpStatus.CREATED);
    }

    @PutMapping("/update-info")
    public Object update(@RequestBody DiscountDTO discountDTO) {
        return ResponseUtil.get(services.update(discountDTO), HttpStatus.OK);
    }

}
