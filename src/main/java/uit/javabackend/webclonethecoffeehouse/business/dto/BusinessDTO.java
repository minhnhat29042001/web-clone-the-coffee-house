package uit.javabackend.webclonethecoffeehouse.business.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessDTO implements Serializable {

    private UUID id;

    @Length(min = 5, max = 50, message = "")
    private String companyName;

    @Length(min = 5, max = 50, message = "")
    private String representativeName;

    @Length(min = 5, max = 15, message = "")
    private String brand;

    @Length(min = 5, max = 15, message = "")
    private String orderHotline;

    @Length(min = 5, max = 15, message = "")
    private String recruitmentHotline;

    @Length(min = 5, max = 20, message = "")
    @Email
    private String email;

    @Length(min = 5, max = 50, message = "")
    private String address;

    @Length(min = 5, max = 20, message = "")
    private String taxCode;

    @URL
    @Length(min = 5, max = 100, message = "")
    private String socialNetworkUrl;

    @Length(min = 5, max = 100, message = "")
    @URL
    private String websiteUrl;

    @Length(min = 5, max = 100, message = "")
    @URL
    private String imageUrl;
}
