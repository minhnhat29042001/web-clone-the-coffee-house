package uit.javabackend.webclonethecoffeehouse.product.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import uit.javabackend.webclonethecoffeehouse.productgroup.model.ProductGroup;
import uit.javabackend.webclonethecoffeehouse.common.model.BaseEntity;
import uit.javabackend.webclonethecoffeehouse.currency.model.Currency;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = ProductEntity.Product.TABLE_NAME)
public class Product extends BaseEntity {
    @Column(name = ProductEntity.Product.PRODUCT_NAME,unique = true)
    @Length(min = 5  , max = 100 ,message = "Product name must have length between {min} and {max}")
    private String name;


    @Column(name = ProductEntity.Product.PRODUCT_PRICE)
    private Integer price;

    @ManyToOne // map id currency
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "productgroup_id")
    private ProductGroup productGroup;

    @Column(name = ProductEntity.Product.PRODUCT_IMG_URL)
    private String imgUrl;




    @Column(name = ProductEntity.Product.PRODUCT_DESCRIPTION)
    @Length(min = 5  , max = 100 ,message = "Product description must have length between {min} and {max}")
    private String description;
}
