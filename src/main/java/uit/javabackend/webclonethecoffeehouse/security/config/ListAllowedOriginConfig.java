package uit.javabackend.webclonethecoffeehouse.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
public class ListAllowedOriginConfig {

    @Value("${tch.configcros.alloworigin}")
    private List<String> listAllowedOrigin;
}
