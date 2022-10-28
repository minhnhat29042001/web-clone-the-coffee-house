package uit.javabackend.webclonethecoffeehouse.orderproduct.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import uit.javabackend.webclonethecoffeehouse.common.model.BaseEntity;
import uit.javabackend.webclonethecoffeehouse.order.model.Order;
import uit.javabackend.webclonethecoffeehouse.product.model.Product;
import uit.javabackend.webclonethecoffeehouse.product.model.ProductEntity;
import uit.javabackend.webclonethecoffeehouse.productgroup.model.ProductGroupEntity;

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
