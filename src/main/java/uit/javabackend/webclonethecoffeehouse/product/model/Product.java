package uit.javabackend.webclonethecoffeehouse.product.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import uit.javabackend.webclonethecoffeehouse.common.model.BaseEntity;
import uit.javabackend.webclonethecoffeehouse.currency.model.Currency;
import uit.javabackend.webclonethecoffeehouse.order.model.OrderProduct;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = ProductEntity.Product.TABLE_NAME)
public class Product extends BaseEntity {

    @Column(name = ProductEntity.Product.NAME, unique = true)
    @Length(min = 5, max = 100, message = "Product name must have length between {min} and {max}")
    private String name;

    @Column(name = ProductEntity.Product.PRICE)
    private Integer price;

    @Column(name = ProductEntity.Product.IMG_URL)
    private String imgUrl;

    @Column(name = ProductEntity.Product.DESCRIPTION)
    @Lob
    private String description;


    @ManyToOne // map id currency
    @JoinColumn(name = ProductEntity.Product.CURRENCY)
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = ProductEntity.Product.PRODUCTGROUP)
    private ProductGroup productGroup;

    // relationship - bidirectional
    @OneToMany(mappedBy = ProductEntity.ProductMappedOrderProduct.PRODUCT_MAPPED_ODERPRODUCT, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<OrderProduct> orderProducts = new ArrayList<>();

    public Product addOrderProduct(OrderProduct orderProduct) {
        this.orderProducts.add(orderProduct);
        orderProduct.setProduct(this);
        return this;
    }

    public void removeOrderProduct(OrderProduct orderProduct) {
        this.orderProducts.remove(orderProduct);
        orderProduct.setProduct(null);
    }

}
