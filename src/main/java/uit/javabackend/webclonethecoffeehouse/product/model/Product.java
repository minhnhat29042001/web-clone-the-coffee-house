package uit.javabackend.webclonethecoffeehouse.product.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import uit.javabackend.webclonethecoffeehouse.orderproduct.model.OrderProduct;
import uit.javabackend.webclonethecoffeehouse.productgroup.model.ProductGroup;
import uit.javabackend.webclonethecoffeehouse.common.model.BaseEntity;
import uit.javabackend.webclonethecoffeehouse.currency.model.Currency;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = ProductEntity.Product.TABLE_NAME)
public class Product extends BaseEntity {
    @Column(name = ProductEntity.Product.NAME,unique = true)
    @Length(min = 5  , max = 100 ,message = "Product name must have length between {min} and {max}")
    private String name;


    @Column(name = ProductEntity.Product.PRICE)
    private Integer price;

    @ManyToOne // map id currency
    @JoinColumn(name = ProductEntity.Product.CURRENCY)
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = ProductEntity.Product.PRODUCTGROUP)
    private ProductGroup productGroup;

    @OneToMany(mappedBy = ProductEntity.ProductMappedOrderProduct.PRODUCT_MAPPED_ODERPRODUCT)
    private Set<OrderProduct> orderProducts;

    @Column(name = ProductEntity.Product.IMG_URL)
    private String imgUrl;

    @Column(name = ProductEntity.Product.DESCRIPTION)
    @Length(min = 5  , max = 100 ,message = "Product description must have length between {min} and {max}")
    private String description;
}
