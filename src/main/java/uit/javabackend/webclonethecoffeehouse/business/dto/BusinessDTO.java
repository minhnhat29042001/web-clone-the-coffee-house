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

    @Length(min = 5, max = 50, message = "companyName between {min} and {max}")
    private String companyName;

    @Length(min = 5, max = 50, message = "representativeName between {min} and {max}")
    private String representativeName;

    @Length(min = 5, max = 20, message = "brand between {min} and {max}")
    private String brand;

    @Length(min = 5, max = 20, message = "orderHotline between {min} and {max}")
    private String orderHotline;

    @Length(min = 5, max = 20, message = "recruitmentHotline between {min} and {max}")
    private String recruitmentHotline;

    @Length(min = 5, max = 50, message = "email between {min} and {max}")
    @Email
    private String email;

    @Length(min = 5, max = 100, message = "address between {min} and {max}")
    private String address;

    @Length(min = 5, max = 100, message = "taxCode between {min} and {max}")
    private String taxCode;

    @URL
    private String socialNetworkUrl;

    @URL
    private String websiteUrl;

    @URL
    private String imageUrl;
}
