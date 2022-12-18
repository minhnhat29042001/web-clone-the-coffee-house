package uit.javabackend.webclonethecoffeehouse.security.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uit.javabackend.webclonethecoffeehouse.common.exception.TCHBusinessException;
import uit.javabackend.webclonethecoffeehouse.common.util.TCHMapper;
import uit.javabackend.webclonethecoffeehouse.role.model.UserGroup;
import uit.javabackend.webclonethecoffeehouse.role.repository.UserGroupRepository;
import uit.javabackend.webclonethecoffeehouse.security.dto.LoginDTO;
import uit.javabackend.webclonethecoffeehouse.security.dto.ValidateTokenDTO;
import uit.javabackend.webclonethecoffeehouse.security.jwt.JwtUtils;
import uit.javabackend.webclonethecoffeehouse.user.dto.UserDTO;
import uit.javabackend.webclonethecoffeehouse.user.dto.UserDTOWithToken;
import uit.javabackend.webclonethecoffeehouse.user.model.User;
import uit.javabackend.webclonethecoffeehouse.user.repository.UserRepository;

import javax.mail.internet.MimeMessage;
import java.util.Optional;

public interface AuthService {
    UserDTOWithToken login(LoginDTO dto);

    UserDTOWithToken registerCustomer(UserDTO dto);

    ValidateTokenDTO validateToken(String token);

    String resetPassword(String host, String email);

    UserDTOWithToken changePassword(String token, String password);
}

@Service
@Transactional
class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;
    private final PasswordEncoder passwordEncoder;
    private final TCHMapper mapper;
    private final JwtUtils jwtUtils;

    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;

    AuthServiceImpl(UserRepository userRepository, UserGroupRepository userGroupRepository, PasswordEncoder passwordEncoder, TCHMapper mapper, JwtUtils jwtUtils, JavaMailSender javaMailSender) {
        this.userRepository = userRepository;
        this.userGroupRepository = userGroupRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
        this.jwtUtils = jwtUtils;
        this.javaMailSender = javaMailSender;
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
    public UserDTOWithToken registerCustomer(UserDTO dto) {
        User user = mapper.map(dto, User.class);
        // encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setProvider(User.Provider.local);
        Optional<UserGroup> userGroupOptional = userGroupRepository.findByName("CUSTOMER");
        if (userGroupOptional.isPresent()) {
            UserGroup userGroup = userGroupOptional.get();
            userGroup.addUser(user);
            user.getUserGroups().add(userGroup);
        }

        return mapper.map(
                userRepository.save(user),
                UserDTOWithToken.class
        );
    }

    @Override
    public ValidateTokenDTO validateToken(String token) {
        return jwtUtils.validateToken(token);
    }

    @Override
    public String resetPassword(String host, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new TCHBusinessException("User not found"));
        String token = jwtUtils.generateJwt(user.getUsername());
        try {
            String url = host + "/auth/resetPassword?token=" + token;
            MimeMessage mailMessage =  javaMailSender.createMimeMessage();
            MimeMessageHelper mailHelper = new MimeMessageHelper(mailMessage ,true, "utf-8");
            // Setting up necessary details
            mailMessage.setContent(
                    "<p>Dear " + user.getName() + ",</p>" +
                    "<p> This email is automatically sent to reset the clone coffee house web password.</p>" +
                    "<a href=" + url + ">" +
                        "<button style=\"background-color: #49CC90; border-color:#49CC90; color: white;\" >Click me to change password</button>" +
                    "</a>" +
                    "<p>Do <span style=\"color: red\">not</span> share this email to anyone</p>" +

                    "<p>Thank you !</p>"
                    ,"text/html"
            );
            mailHelper.setTo(email);
            mailHelper.setSubject("Reset the clone of the coffee house account password");

            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            return "Error while Sending Mail:\n" + e.getMessage();
        }
        }

    @Override
    public UserDTOWithToken changePassword(String token, String password) {
        String username = jwtUtils.getUsername(token);
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new TCHBusinessException("User not found: " + username));
        user.setPassword(password);
        user.setToken(token);
        return mapper.map(user, UserDTOWithToken.class);
    }


}
