package uit.javabackend.webclonethecoffeehouse.business.boundary;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uit.javabackend.webclonethecoffeehouse.business.dto.BusinessDTO;
import uit.javabackend.webclonethecoffeehouse.business.model.Business;
import uit.javabackend.webclonethecoffeehouse.business.service.BusinessService;
import uit.javabackend.webclonethecoffeehouse.common.util.ResponseUtil;
import uit.javabackend.webclonethecoffeehouse.security.authorization.TCHOperation;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/business")
@CrossOrigin(origins = "*")
public class BusinessRestResource {

    private final BusinessService services;

    public BusinessRestResource(BusinessService services) {
        this.services = services;
    }

    @GetMapping("common/get-all")
    public Object findAll() {
        return ResponseUtil.get(services.findAllDto(BusinessDTO.class), HttpStatus.OK);
    }
    @GetMapping("common/{business-id}/get")
    public Object getBusinessById(@PathVariable("business-id") UUID id) {
        return ResponseUtil.get(services.getById(id), HttpStatus.OK);
    }

    @TCHOperation(name = "BusinessManagement")
    @PostMapping(path = "/add-info")
    public Object save(@RequestBody BusinessDTO businessDTO) {
        return ResponseUtil.get(services.save(businessDTO, Business.class, BusinessDTO.class), HttpStatus.CREATED);
    }

    @TCHOperation(name = "BusinessManagement")
    @PutMapping("/update-info")
    public Object update(@RequestBody BusinessDTO businessDTO) {
        return ResponseUtil.get(services.update(businessDTO), HttpStatus.OK);
    }

}
