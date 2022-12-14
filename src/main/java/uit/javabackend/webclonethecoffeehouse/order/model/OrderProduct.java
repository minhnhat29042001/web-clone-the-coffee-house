package uit.javabackend.webclonethecoffeehouse.order.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import uit.javabackend.webclonethecoffeehouse.common.model.BaseEntity;
import uit.javabackend.webclonethecoffeehouse.order.enums.OrderProductSize;
import uit.javabackend.webclonethecoffeehouse.order.enums.OrderProductTopping;
import uit.javabackend.webclonethecoffeehouse.product.model.Product;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = OrderProductEntity.OrderProduct.TABLE_NAME)
public class OrderProduct extends BaseEntity {

    @Column(name = OrderProductEntity.OrderProduct.NOTE)
    private String note;

    @Column(name = OrderProductEntity.OrderProduct.SIZE)
    private String size;

    @Column(name = OrderProductEntity.OrderProduct.TOPPING)
    private String topping;

    @Column(name = OrderProductEntity.OrderProduct.TOTALPRICE)
    private int totalPrice;

    @Column(name = OrderProductEntity.OrderProduct.QUANTITY)
    private int quantity;

    // relationship
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = OrderProductEntity.OrderProduct.PRODUCT_ID )
    private Product product;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = OrderProductEntity.OrderProduct.ORDER_ID )
    private Order order;


}
