package uit.javabackend.webclonethecoffeehouse.security.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uit.javabackend.webclonethecoffeehouse.common.exception.TCHBusinessException;
import uit.javabackend.webclonethecoffeehouse.common.util.PasswordGenerateUtils;
import uit.javabackend.webclonethecoffeehouse.common.util.TCHMapper;
import uit.javabackend.webclonethecoffeehouse.role.model.UserGroup;
import uit.javabackend.webclonethecoffeehouse.role.repository.UserGroupRepository;
import uit.javabackend.webclonethecoffeehouse.security.dto.LoginDTO;
import uit.javabackend.webclonethecoffeehouse.security.dto.ValidateTokenDTO;
import uit.javabackend.webclonethecoffeehouse.security.jwt.JwtUtils;
import uit.javabackend.webclonethecoffeehouse.security.model.EmailAndJWT;
import uit.javabackend.webclonethecoffeehouse.security.repository.EmailAndJWTRepository;
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

    String forgotPassword(String email, String feHomePage, String host);

    boolean resetPassword(String code);

    UserDTOWithToken changePassword(String username, String newPassword, String oldPassword);
}

@Service
@Transactional
class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;
    private final PasswordEncoder passwordEncoder;
    private final TCHMapper mapper;
    private final JwtUtils jwtUtils;
    private  final EmailAndJWTService emailAndJWTService;
    private final EmailAndJWTRepository emailAndJWTRepository;

    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;

    AuthServiceImpl(UserRepository userRepository, UserGroupRepository userGroupRepository, PasswordEncoder passwordEncoder, TCHMapper mapper, JwtUtils jwtUtils, EmailAndJWTService emailAndJWTService, EmailAndJWTRepository emailAndJWTRepository, JavaMailSender javaMailSender) {
        this.userRepository = userRepository;
        this.userGroupRepository = userGroupRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
        this.jwtUtils = jwtUtils;
        this.emailAndJWTService = emailAndJWTService;
        this.emailAndJWTRepository = emailAndJWTRepository;
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
    public String forgotPassword(String email, String feHomePage, String host) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new TCHBusinessException("User not found"));
        String code = jwtUtils.generateJwt(email);
        EmailAndJWT emailAndJWT = new EmailAndJWT(email,code);
        boolean checkExistEmailAndJWT = emailAndJWTService.checkExistEmailAndJWT(emailAndJWT);
        if(checkExistEmailAndJWT){
            EmailAndJWT emailAndJWT1 = emailAndJWTRepository.findByEmail(email).orElseThrow(()-> new TCHBusinessException("EmailAndJWT not found"));
            emailAndJWT1.setJwt(code);
            emailAndJWTRepository.save(emailAndJWT);
        }else{
            emailAndJWTService.addEmailAndJWT(emailAndJWT);
        }


        try {
            String url = host + "/auth/resetPassword?code=" + code + "&redirectUri=" + feHomePage;

            MimeMessage mailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mailHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
            // Setting up necessary details
            mailMessage.setContent(
                    "<p>Dear " + user.getName() + ",</p>" +
                            "<p> This email is automatically sent to reset the clone coffee house web password.</p>" +
                            "<a href=\"" + url + "\" style=\"background-color: #49CC90; border-color:#49CC90; color: white;\">" +
                            "Click me to change password" +
                            "</a>" +
                            "<p>Do <span style=\"color: red\">not</span> share this email to anyone</p>" +

                            "<p>Thank you !</p>"
                    , "text/html"
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
    public boolean resetPassword(String code) {

        String email = jwtUtils.getUsername(code);
        EmailAndJWT emailAndJWT = new EmailAndJWT(email,code);
        boolean checkExistEmailAndJWT = emailAndJWTService.checkExistEmailAndJWT(emailAndJWT);
        if(checkExistEmailAndJWT){
            emailAndJWTService.deleteEmailAndJWT(email);
            User user = userRepository.findByEmail(email).orElseThrow(() -> new TCHBusinessException("User not found"));
            String newPassword = PasswordGenerateUtils.generateCommonLangPassword();
            try {
                MimeMessage mailMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper mailHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
                // Setting up necessary details
                mailMessage.setContent(
                        "<p>Dear " + user.getName() + ",</p>" +
                                "<p> This email is automatically sent to reset the clone coffee house web password.</p>" +
                                "<p>Do <span style=\"color: red\">not</span> share this email to anyone</p>" +
                                "<p>Your new password is: " + newPassword + "</p>" +
                                "<p>Thank you !</p>"
                        , "text/html"
                );
                mailHelper.setTo(user.getEmail());
                mailHelper.setSubject("Reset the clone of the coffee house account password");

                javaMailSender.send(mailMessage);
                user.setPassword(passwordEncoder.encode(newPassword));
                return true;
            }

            // Catch block to handle the exceptions
            catch (Exception e) {
                return false;
            }
        }else{
            return false;
        }

    }

    @Override
    public UserDTOWithToken changePassword(String username, String newPassword, String oldPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new TCHBusinessException("User not found: " + username));
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
        } else throw new TCHBusinessException("Old password not correct");
        return mapper.map(user, UserDTOWithToken.class);
    }


}
