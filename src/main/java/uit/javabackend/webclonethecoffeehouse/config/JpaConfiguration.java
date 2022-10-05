package uit.javabackend.webclonethecoffeehouse.config;

import io.micrometer.core.lang.NonNullApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware") // kích hoạt tự động khởi tạo cho BaseEntity
public class JpaConfiguration {
    @Bean
    public AuditorAware <String> auditorAware(){
        return new AuditorAwareImpl();
    }

   static class AuditorAwareImpl implements AuditorAware<String>{

       @Override
       public Optional<String> getCurrentAuditor() {
           return Optional.of("Anonymus");
       }
   }
}
