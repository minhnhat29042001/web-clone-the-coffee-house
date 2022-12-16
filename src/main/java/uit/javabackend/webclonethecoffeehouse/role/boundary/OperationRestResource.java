package uit.javabackend.webclonethecoffeehouse.role.boundary;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uit.javabackend.webclonethecoffeehouse.common.util.ResponseUtil;
import uit.javabackend.webclonethecoffeehouse.role.dto.OperationDTO;
import uit.javabackend.webclonethecoffeehouse.role.model.Operation;
import uit.javabackend.webclonethecoffeehouse.role.service.OperationService;
import uit.javabackend.webclonethecoffeehouse.security.authorization.TCHOperation;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/OperationsManagement")
public class OperationRestResource {
    private final OperationService operationService;

    public OperationRestResource(OperationService operationService) {
        this.operationService = operationService;
    }

    @TCHOperation(name = "GetAllOperations")
    @GetMapping("/GetAllOperations")
    public Object findAll() {
        return ResponseUtil.get(operationService.findAllDto(OperationDTO.class), HttpStatus.OK);
    }

    @TCHOperation(name = "SaveOperation")
    @PostMapping("/SaveOperation")
    public Object save(@RequestBody @Valid OperationDTO dto) {
        return ResponseUtil.get(
                operationService.save(dto, Operation.class, OperationDTO.class)
                , HttpStatus.OK
        );
    }

    @TCHOperation(name = "SaveOperation")
    @PostMapping("/SaveOperations/{role-id}")
    public Object saveOperations(@RequestBody List<OperationDTO> dtos, @PathVariable("role-id") UUID roleId) {
        return ResponseUtil.get(
                operationService.saveOperations(dtos, roleId)
                , HttpStatus.OK
        );
    }

    @TCHOperation(name = "UpdateOperation")
    @PutMapping("/UpdateOperation")
    public Object update(@RequestBody OperationDTO operation) {
        return ResponseUtil.get(operationService.update(operation), HttpStatus.OK);
    }

    @TCHOperation(name = "DeleteOperation")
    @DeleteMapping("/DeleteOperation")
    public Object delete(@RequestParam("code") String code) {
        operationService.deleteByCode(code);
        return HttpStatus.OK;
    }
}
