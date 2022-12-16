package uit.javabackend.webclonethecoffeehouse.business.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import uit.javabackend.webclonethecoffeehouse.common.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@SuperBuilder
@NoArgsConstructor
@Setter
@Getter
@Table(name = BusinessEntity.Business.TABLE_NAME)
public class Business extends BaseEntity {

    @Column(name = BusinessEntity.Business.COMPANY_NAME)
    @Length(min = 5, max = 50, message = "companyName between {min} and {max}")
    private String companyName;

    @Column(name = BusinessEntity.Business.REPRESENTATIVE_NAME)
    @Length(min = 5, max = 50, message = "representativeName between {min} and {max}")
    private String representativeName;

    @Column(name = BusinessEntity.Business.BRAND)
    @Length(min = 5, max = 20, message = "brand between {min} and {max}")
    private String brand;

    @Column(name = BusinessEntity.Business.ORDER_HOTLINE)
    @Length(min = 5, max = 20, message = "orderHotline between {min} and {max}")
    private String orderHotline;

    @Column(name = BusinessEntity.Business.RECRUITMENT_HOTLINE)
    @Length(min = 5, max = 20, message = "recruitmentHotline between {min} and {max}")
    private String recruitmentHotline;

    @Column(name = BusinessEntity.Business.EMAIL)
    @Length(min = 5, max = 50, message = "email between {min} and {max}")
    private String email;

    @Column(name = BusinessEntity.Business.ADDRESS)
    @Length(min = 5, max = 100, message = "address between {min} and {max}")
    private String address;

    @Column(name = BusinessEntity.Business.TAX_CODE)
    @Length(min = 5, max = 100, message = "taxCode between {min} and {max}")
    private String taxCode;

    @Column(name = BusinessEntity.Business.SOCIAL_NETWORK_URL)
    private String socialNetworkUrl;

    @Column(name = BusinessEntity.Business.WEBSITE_URL)
    private String websiteUrl;

    @Column(name = BusinessEntity.Business.IMAGE_URL)
    private String imageUrl;
}
