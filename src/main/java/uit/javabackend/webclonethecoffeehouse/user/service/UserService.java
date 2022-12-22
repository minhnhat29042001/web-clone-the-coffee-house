package uit.javabackend.webclonethecoffeehouse.user.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uit.javabackend.webclonethecoffeehouse.common.service.GenericService;
import uit.javabackend.webclonethecoffeehouse.common.util.TCHMapper;
import uit.javabackend.webclonethecoffeehouse.file.FileService;
import uit.javabackend.webclonethecoffeehouse.product.dto.ProductDTO;
import uit.javabackend.webclonethecoffeehouse.role.dto.UserGroupDTO;
import uit.javabackend.webclonethecoffeehouse.user.dto.UserDTO;
import uit.javabackend.webclonethecoffeehouse.user.dto.UserDTOWithToken;
import uit.javabackend.webclonethecoffeehouse.user.model.User;
import uit.javabackend.webclonethecoffeehouse.user.repository.UserRepository;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public interface UserService extends GenericService<User, UserDTO, UUID> {


    void deleteByUserName(String username);

    UserDTO update(UserDTO userDTO);

    List<UserGroupDTO> findAllUserGroupUsername(String username);

    UserDTOWithToken createUser(UserDTO dto);

    UserDTO getUserByUsername(String username);

    UserDTOWithToken saveUserAvatar(String username, MultipartFile file, String baseUrl);
}

@Service
@Transactional
class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TCHMapper tchMapper;
    private final FileService fileService;

    UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, TCHMapper tchMapper, FileService fileService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.tchMapper = tchMapper;
        this.fileService = fileService;
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
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ValidationException("User is not existed.")
                );
        user.getUserGroups().forEach(userGroup -> userGroup.removeUser(user));
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

    @Override
    public UserDTOWithToken createUser(UserDTO dto) {
        User user = tchMapper.map(dto, User.class);
        // encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setProvider(User.Provider.local);

        return tchMapper.map(
                userRepository.save(user),
                UserDTOWithToken.class
        );
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        return tchMapper.map(userRepository.findByUsername(username), UserDTO.class);
    }

    @Override
    public UserDTOWithToken saveUserAvatar(String username, MultipartFile file, String baseUrl) {
        User user = userRepository.findByUsername(username).orElseThrow(()->
                new ValidationException("User is not existed")
        );
        fileService.init();
        fileService.save(file);
        String urlLoadFile = baseUrl+"/api/Files/"+file.getOriginalFilename();
        user.setAvatar(urlLoadFile);
        return tchMapper.map(user,UserDTOWithToken.class);
    }

}