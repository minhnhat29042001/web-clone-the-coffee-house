package uit.javabackend.webclonethecoffeehouse.product.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import uit.javabackend.webclonethecoffeehouse.common.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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

    @Column(name = ProductEntity.Product.PRODUCT_URL)
    private String productUrl;

    @Column(name = ProductEntity.Product.PRODUCT_PRICE)
    private Integer price;

    @Column(name = ProductEntity.Product.PRODUCT_CURRENCY)
    private String currency;

    @Column(name = ProductEntity.Product.PRODUCT_IMG_URL)
    private String imgUrl;

    @Column(name = ProductEntity.Product.PRODUCT_COLLECTION)
    private String collectionName;

    @Column(name = ProductEntity.Product.PRODUCT_DESCRIPTION)
    private String description;
}
