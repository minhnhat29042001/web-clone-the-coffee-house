package uit.javabackend.webclonethecoffeehouse.user.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import uit.javabackend.webclonethecoffeehouse.common.service.GenericService;
import uit.javabackend.webclonethecoffeehouse.common.util.TCHMapper;
import uit.javabackend.webclonethecoffeehouse.role.dto.UserGroupDTO;
import uit.javabackend.webclonethecoffeehouse.user.dto.UserDTO;
import uit.javabackend.webclonethecoffeehouse.user.model.User;
import uit.javabackend.webclonethecoffeehouse.user.repository.UserRepository;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface UserService extends GenericService<User, UserDTO, UUID> {

    void deleteByUserName(String username);

    UserDTO update(UserDTO userDTO);

    List<UserGroupDTO> findAllUserGroupUsername(String username);
}

@Service
class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TCHMapper tchMapper;

    UserServiceImpl(UserRepository userRepository, TCHMapper tchMapper) {
        this.userRepository = userRepository;
        this.tchMapper = tchMapper;
    }

    @Override
    public JpaRepository<User, UUID> getRepository() {
        return userRepository;
    }

    @Override
    public ModelMapper getMapper() {
        return tchMapper;
    }

    @Override
    public void deleteByUserName(String username) {
        userRepository.deleteByUsername(username);
    }

    public UserDTO update(UserDTO userDTO) {
        User user = tchMapper.map(userDTO, User.class);
        return tchMapper.map(userRepository.save(user), UserDTO.class);
    }

    @Override
    public List<UserGroupDTO> findAllUserGroupUsername(String username) {
        List<UserGroupDTO> userGroupDTOs = new ArrayList<>();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ValidationException("User is not existed.")
                );
        user.getUserGroups().forEach(
                userGroup -> userGroupDTOs.add(tchMapper.map(userGroup, UserGroupDTO.class))
        );
        return userGroupDTOs;
    }
}