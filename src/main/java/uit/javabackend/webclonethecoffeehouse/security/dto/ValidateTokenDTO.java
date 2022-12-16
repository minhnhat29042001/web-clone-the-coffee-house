package uit.javabackend.webclonethecoffeehouse.security.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidateTokenDTO {
    private STATUS status;
    private String message;

    public enum STATUS {
        TRUE,
        EXPIRED,
        INVALID
    }
}

