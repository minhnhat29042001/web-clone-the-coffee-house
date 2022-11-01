package uit.javabackend.webclonethecoffeehouse.role.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import uit.javabackend.webclonethecoffeehouse.common.service.GenericService;
import uit.javabackend.webclonethecoffeehouse.common.util.TCHMapper;
import uit.javabackend.webclonethecoffeehouse.role.dto.OperationDTO;
import uit.javabackend.webclonethecoffeehouse.role.model.Operation;
import uit.javabackend.webclonethecoffeehouse.role.repository.OperationRepository;

import java.util.List;
import java.util.UUID;

public interface OperationService extends GenericService<Operation, OperationDTO, UUID> {

    List<Operation> findAll(List<UUID> operationIds);

    void deleteByCode(String code);


    List<Operation> findByRoleId(UUID roleId);

    OperationDTO update(OperationDTO operationDTO);
}

@org.springframework.stereotype.Service
@Transactional
class OperationServiceImpl implements OperationService {
    private final OperationRepository operationRepository;
    private final TCHMapper mapper;

    OperationServiceImpl(OperationRepository operationRepository, TCHMapper mapper) {
        this.operationRepository = operationRepository;
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

}
