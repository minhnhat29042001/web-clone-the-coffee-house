package uit.javabackend.webclonethecoffeehouse.security.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uit.javabackend.webclonethecoffeehouse.common.exception.TCHBusinessException;
import uit.javabackend.webclonethecoffeehouse.security.model.EmailAndJWT;
import uit.javabackend.webclonethecoffeehouse.security.repository.EmailAndJWTRepository;

import java.util.Optional;

public interface EmailAndJWTService {
    boolean checkExistEmailAndJWT(String email, String JWT);
    EmailAndJWT addEmailAndJWT (EmailAndJWT emailAndJWT);
    void deleteEmailAndJWT (String email);
}

@Service
@Transactional
class EmailAndJWTImpl implements  EmailAndJWTService{
    EmailAndJWTImpl(EmailAndJWTRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean checkExistEmailAndJWT(String email, String JWT) {
        Optional<EmailAndJWT> checkExistEmailAndJWT = repository.getEntityByEmailAndJwt(email,JWT);
        return checkExistEmailAndJWT.isPresent()?true:false;
    }

    @Override
    public EmailAndJWT addEmailAndJWT(EmailAndJWT emailAndJWT) {
        return repository.save(emailAndJWT);
    }

    @Override
    public void deleteEmailAndJWT(String email) {
        repository.deleteByEmail(email);
    }

    private final EmailAndJWTRepository repository;


}