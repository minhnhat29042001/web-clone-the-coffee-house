package uit.javabackend.webclonethecoffeehouse.security.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uit.javabackend.webclonethecoffeehouse.common.exception.TCHBusinessException;
import uit.javabackend.webclonethecoffeehouse.security.model.EmailAndJWT;
import uit.javabackend.webclonethecoffeehouse.security.repository.EmailAndJWTRepository;

import java.util.Optional;

public interface EmailAndJWTService {
    boolean checkExistEmailAndJWT(EmailAndJWT emailAndJWT);
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
    public boolean checkExistEmailAndJWT(EmailAndJWT emailAndJWT) {
        Optional<EmailAndJWT> checkExistEmailAndJWT = repository.getEntityByEmailAndJwt(emailAndJWT.getEmail(), emailAndJWT.getJwt());
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