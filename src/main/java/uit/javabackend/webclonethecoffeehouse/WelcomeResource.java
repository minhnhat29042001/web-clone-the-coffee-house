package uit.javabackend.webclonethecoffeehouse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class WelcomeResource {
    @GetMapping("/welcome")
    public String welcome(HttpServletRequest request) {
        return """
                Welcome %s to Web clone the coffee house application
                """.formatted(request.getRemoteAddr());
    }
}

