package uit.javabackend.webclonethecoffeehouse.role.boundary;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uit.javabackend.webclonethecoffeehouse.common.util.ResponseUtil;
import uit.javabackend.webclonethecoffeehouse.role.dto.OperationDTO;
import uit.javabackend.webclonethecoffeehouse.role.model.Operation;
import uit.javabackend.webclonethecoffeehouse.role.service.OperationService;

import javax.validation.Valid;

@RestController
@RequestMapping("/OperationsManagement")
public class OperationRestResource {
    private final OperationService operationService;

    public OperationRestResource(OperationService operationService) {
        this.operationService = operationService;
    }

    @GetMapping("/GetAllOperations")
    public ResponseEntity<?> findAll() {
        return ResponseUtil.get(operationService.findAllDto(OperationDTO.class), HttpStatus.OK);
    }

    @PostMapping("/AddOperation")
    public ResponseEntity<?> save(@RequestBody @Valid OperationDTO dto) {
        return ResponseUtil.get(
                operationService.save(dto, Operation.class, OperationDTO.class)
                , HttpStatus.OK
        );
    }

    @PutMapping("/UpdateOperation")
    public Object update(@RequestBody OperationDTO operation) {
        return ResponseUtil.get(operationService.update(operation), HttpStatus.OK);
    }

    @DeleteMapping("/DeleteOperation")
    public Object delete(@RequestParam("code") String code) {
        operationService.deleteByCode(code);
        return HttpStatus.OK;
    }
}
