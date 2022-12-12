package uit.javabackend.webclonethecoffeehouse.role.boundary;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uit.javabackend.webclonethecoffeehouse.common.util.ResponseUtil;
import uit.javabackend.webclonethecoffeehouse.role.dto.OperationDTO;
import uit.javabackend.webclonethecoffeehouse.role.model.Operation;
import uit.javabackend.webclonethecoffeehouse.role.service.OperationService;
import uit.javabackend.webclonethecoffeehouse.security.authorization.TCHOperation;

import javax.validation.Valid;

@RestController
@RequestMapping("/OperationsManagement")
public class OperationRestResource {
    private final OperationService operationService;

    public OperationRestResource(OperationService operationService) {
        this.operationService = operationService;
    }

    @TCHOperation(name = "RoleSystemManagement")
    @GetMapping("/GetAllOperations")
    public Object findAll() {
        return ResponseUtil.get(operationService.findAllDto(OperationDTO.class), HttpStatus.OK);
    }

    @TCHOperation(name = "RoleSystemManagement")
    @PostMapping("/SaveOperation")
    public Object save(@RequestBody @Valid OperationDTO dto) {
        return ResponseUtil.get(
                operationService.save(dto, Operation.class, OperationDTO.class)
                , HttpStatus.OK
        );
    }

    @TCHOperation(name = "RoleSystemManagement")
    @PutMapping("/UpdateOperation")
    public Object update(@RequestBody OperationDTO operation) {
        return ResponseUtil.get(operationService.update(operation), HttpStatus.OK);
    }

    @TCHOperation(name = "RoleSystemManagement")
    @DeleteMapping("/DeleteOperation")
    public Object delete(@RequestParam("code") String code) {
        operationService.deleteByCode(code);
        return HttpStatus.OK;
    }
}
