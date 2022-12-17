package uit.javabackend.webclonethecoffeehouse.role.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import uit.javabackend.webclonethecoffeehouse.common.exception.TCHBusinessException;
import uit.javabackend.webclonethecoffeehouse.common.service.GenericService;
import uit.javabackend.webclonethecoffeehouse.common.util.TCHMapper;
import uit.javabackend.webclonethecoffeehouse.role.dto.OperationDTO;
import uit.javabackend.webclonethecoffeehouse.role.model.Operation;
import uit.javabackend.webclonethecoffeehouse.role.model.Role;
import uit.javabackend.webclonethecoffeehouse.role.repository.OperationRepository;
import uit.javabackend.webclonethecoffeehouse.role.repository.RoleRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public interface OperationService extends GenericService<Operation, OperationDTO, UUID> {

    List<Operation> findAll(List<UUID> operationIds);

    void deleteByCode(String code);


    List<Operation> findByRoleId(UUID roleId);

    OperationDTO update(OperationDTO operationDTO);

    Object saveOperations(List<OperationDTO> operationDTOS,UUID roleId);
}

@org.springframework.stereotype.Service
@Transactional
class OperationServiceImpl implements OperationService {
    private final OperationRepository operationRepository;
    private final RoleRepository roleRepository;
    private final TCHMapper mapper;
    private TCHBusinessException roleIsNotExisted = new TCHBusinessException("role is not existed");

    OperationServiceImpl(OperationRepository operationRepository, RoleRepository roleRepository, TCHMapper mapper) {
        this.operationRepository = operationRepository;
        this.roleRepository = roleRepository;
        this.mapper = mapper;
    }

    @Override
    public JpaRepository<Operation, UUID> getRepository() {
        return operationRepository;
    }

    @Override
    public ModelMapper getMapper() {
        return mapper;
    }

    @Override
    public List<Operation> findAll(List<UUID> operationIds) {
        return operationRepository.findAllById(operationIds);
    }


    @Override
    public void deleteByCode(String code) {
        operationRepository.deleteByCode(code);
    }

    @Override
    public List<Operation> findByRoleId(UUID roleId) {
        return operationRepository.findByRoleId(roleId);
    }

    @Override
    public OperationDTO update(OperationDTO operationDTO) {
        Operation operation = mapper.map(operationDTO, Operation.class);
        return mapper.map(operation, OperationDTO.class);
    }

    @Override
    public Object saveOperations(List<OperationDTO> operationDTOS,UUID roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> roleIsNotExisted);

        List<Operation> operations = operationDTOS.stream().map(model -> mapper.map(model,Operation.class))
                .collect(Collectors.toList());
        operations.forEach(role::addOperation);

        return operationRepository.saveAll(operations)
                .stream()
                .map(model -> mapper.map(model, OperationDTO.class))
                .collect(Collectors.toList());
    }

}
