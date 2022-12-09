package uit.javabackend.webclonethecoffeehouse.security.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uit.javabackend.webclonethecoffeehouse.common.exception.TCHBusinessException;
import uit.javabackend.webclonethecoffeehouse.common.util.TCHMapper;
import uit.javabackend.webclonethecoffeehouse.security.dto.LoginDTO;
import uit.javabackend.webclonethecoffeehouse.security.jwt.JwtUtils;
import uit.javabackend.webclonethecoffeehouse.user.dto.UserDTOWithToken;
import uit.javabackend.webclonethecoffeehouse.user.model.User;
import uit.javabackend.webclonethecoffeehouse.user.repository.UserRepository;

public interface AuthService {
    UserDTOWithToken login(LoginDTO dto);
}

@Service
class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TCHMapper mapper;
    private final JwtUtils jwtUtils;

    AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, TCHMapper mapper, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
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
}
