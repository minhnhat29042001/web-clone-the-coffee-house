package uit.javabackend.webclonethecoffeehouse.order.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import uit.javabackend.webclonethecoffeehouse.common.model.BaseEntity;
import uit.javabackend.webclonethecoffeehouse.orderproduct.model.OrderProduct;
import uit.javabackend.webclonethecoffeehouse.product.model.ProductEntity;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = OrderEntity.Order.TABLE_NAME)
public class Order extends BaseEntity {

    // ten nguoi nhan hang
    @Column(name = OrderEntity.Order.NAME,unique = true)
    @Length(min = 5  , max = 100 ,message = "Product name must have length between {min} and {max}")
    private String name;

    @Column(name = OrderEntity.Order.ADDRESS)
    private String address;

    @Column(name = OrderEntity.Order.NOTE)
    private String note;

    @Column(columnDefinition = "boolean default false", name = OrderEntity.Order.USE_COUPON)
    private boolean useCoupon;

    @Column(name = OrderEntity.Order.TOTAL_PRICE)
    private Integer totalPrice;

    // relationship
    @OneToMany(mappedBy = OrderEntity.OrderMappedOrderProduct.ORDER_MAPPED_ORDERPRODUCT)
    private Set<OrderProduct> orderProducts;
}
