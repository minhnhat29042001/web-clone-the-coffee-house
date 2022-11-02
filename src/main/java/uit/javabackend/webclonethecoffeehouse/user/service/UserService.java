package uit.javabackend.webclonethecoffeehouse.user.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import uit.javabackend.webclonethecoffeehouse.common.service.GenericService;
import uit.javabackend.webclonethecoffeehouse.common.util.TCHMapper;
import uit.javabackend.webclonethecoffeehouse.user.dto.UserDTO;
import uit.javabackend.webclonethecoffeehouse.user.model.User;
import uit.javabackend.webclonethecoffeehouse.user.repository.UserRepository;

import java.util.UUID;

public interface UserService extends GenericService<User, UserDTO, UUID> {

    void deleteByName(String name);

    UserDTO update(UserDTO userDTO);
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
    public void deleteByName(String name) {
        userRepository.deleteByName(name);
    }

    public UserDTO update(UserDTO userDTO) {
        User user = tchMapper.map(userDTO, User.class);
        return tchMapper.map(userRepository.save(user), UserDTO.class);
    }
}