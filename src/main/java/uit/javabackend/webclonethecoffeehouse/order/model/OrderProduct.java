package uit.javabackend.webclonethecoffeehouse.order.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import uit.javabackend.webclonethecoffeehouse.common.model.BaseEntity;
import uit.javabackend.webclonethecoffeehouse.product.model.Product;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = OrderProductEntity.OrderProduct.TABLE_NAME)
public class OrderProduct extends BaseEntity {

    @Column(name = OrderProductEntity.OrderProduct.NAME)
    private String name;

    @Column(name = OrderProductEntity.OrderProduct.TOTALPRICE)
    private int totalPrice;

    @Column(name = OrderProductEntity.OrderProduct.QUANTITY)
    private int quantity;

    // relationship
    @ManyToOne
    @JoinColumn(name = OrderProductEntity.OrderProduct.PRODUCT )
    private Product product;

    @ManyToOne
    @JoinColumn(name = OrderProductEntity.OrderProduct.ORDER )
    private Order order;

}
