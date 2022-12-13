package uit.javabackend.webclonethecoffeehouse.security.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uit.javabackend.webclonethecoffeehouse.common.exception.TCHBusinessException;
import uit.javabackend.webclonethecoffeehouse.common.util.TCHMapper;
import uit.javabackend.webclonethecoffeehouse.role.model.UserGroup;
import uit.javabackend.webclonethecoffeehouse.role.repository.UserGroupRepository;
import uit.javabackend.webclonethecoffeehouse.security.dto.LoginDTO;
import uit.javabackend.webclonethecoffeehouse.security.jwt.JwtUtils;
import uit.javabackend.webclonethecoffeehouse.user.dto.UserDTO;
import uit.javabackend.webclonethecoffeehouse.user.dto.UserDTOWithToken;
import uit.javabackend.webclonethecoffeehouse.user.model.User;
import uit.javabackend.webclonethecoffeehouse.user.repository.UserRepository;

import java.util.Optional;

public interface AuthService {
    UserDTOWithToken login(LoginDTO dto);

    UserDTO registerCustomer(UserDTO dto);
}

@Service
class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;
    private final PasswordEncoder passwordEncoder;
    private final TCHMapper mapper;
    private final JwtUtils jwtUtils;

    AuthServiceImpl(UserRepository userRepository, UserGroupRepository userGroupRepository, PasswordEncoder passwordEncoder, TCHMapper mapper, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.userGroupRepository = userGroupRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public UserDTOWithToken login(LoginDTO dto) {
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(
                        () -> new TCHBusinessException("User is not existed")
                );

        if (passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            UserDTOWithToken userDTOWithToken = mapper.map(user, UserDTOWithToken.class);
            String token = jwtUtils.generateJwt(dto.getUsername());
            userDTOWithToken.setToken(token);
            return userDTOWithToken;
        }

        throw new TCHBusinessException("Password is not correct.");
    }

    @Override
    public UserDTO registerCustomer(UserDTO dto) {
        User user = mapper.map(dto, User.class);
        // encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setProvider(User.Provider.local);
        Optional<UserGroup> userGroupOptional = userGroupRepository.findByName(UserGroup.USER_GROUP.CUSTOMER.name());
        if (userGroupOptional.isPresent()) {
            UserGroup userGroup = userGroupOptional.get();
            userGroup.addUser(user);
            user.getUserGroups().add(userGroup);
        }

        return mapper.map(
                userRepository.save(user),
                UserDTO.class
        );
    }
}
