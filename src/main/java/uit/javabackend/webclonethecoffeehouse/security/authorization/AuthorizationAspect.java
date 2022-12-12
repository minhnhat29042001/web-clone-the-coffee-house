package uit.javabackend.webclonethecoffeehouse.security.authorization;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import uit.javabackend.webclonethecoffeehouse.role.model.Operation;
import uit.javabackend.webclonethecoffeehouse.role.repository.OperationRepository;

import java.util.List;

@Aspect
@Component
@Slf4j
public class AuthorizationAspect {
    private final OperationRepository operationRepository;

    public AuthorizationAspect(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }
    // define point cut

    @Before("@annotation(TCHOperation)")
    public void authorizeOperation(TCHOperation TCHOperation) {
        log.info("Pointcut has been activated, operation = " + TCHOperation.name());
        // get current user
        String username = getCurrentUser();
        // check permission
        if (!isPermitted(username, TCHOperation.name())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "User is not permitted to use this operation. Please contact administrators for permissions");
        }
    }

    private boolean isPermitted(String username, String operationName) {
        if ("root_admin".equals(username)) return true;
        List<Operation> permittedOperations
                = operationRepository.findAllByNameAndUsername(operationName, username);

        return !permittedOperations.isEmpty();
    }

    private String getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null)
            return null;

        if (auth.getPrincipal() instanceof String principal)
            return principal;

        UserDetails currentUser = (UserDetails) auth.getPrincipal();
        return currentUser.getUsername();
    }
}
