package uit.javabackend.webclonethecoffeehouse.order.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import uit.javabackend.webclonethecoffeehouse.business.model.Discount;
import uit.javabackend.webclonethecoffeehouse.common.model.BaseEntity;
import uit.javabackend.webclonethecoffeehouse.order.enums.OrderStatus;
import uit.javabackend.webclonethecoffeehouse.vnp_payment.model.VnpayPayment;
import uit.javabackend.webclonethecoffeehouse.user.model.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = OrderEntity.Order.TABLE_NAME )
public class Order extends BaseEntity {

    // ten nguoi nhan hang
    @Column(name = OrderEntity.Order.CUSTOMER_NAME)
    @Length(min = 5  , max = 100 ,message = " name must have length between {min} and {max}")
    private String customerName;

    @Column(name = OrderEntity.Order.ADDRESS)
    private String address;

    @Column(name = OrderEntity.Order.NOTE)
    private String note;

    @Column( name = OrderEntity.Order.CODE_COUPON)
    private String codeCoupon;

    @Column(name = OrderEntity.Order.TOTAL_PRICE)
    private Integer totalPrice;

    @Column(name = OrderEntity.Order.ORDER_STATUS)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = OrderEntity.Order.USER_ID)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = OrderEntity.Order.DISCOUNT_ID)
    private Discount discount;



    // OneToMany
    // relationship Bidirectional
    @OneToMany(mappedBy = OrderEntity.OrderMapped.ORDER_MAPPED_ORDERPRODUCT,cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<OrderProduct> orderProducts = new ArrayList<>();

    public Order addOrderProduct(OrderProduct orderProduct) {
        this.orderProducts.add(orderProduct);
        orderProduct.setOrder(this);
        return this;
    }

    public void removeOrderProduct(OrderProduct orderProduct) {
        this.orderProducts.remove(orderProduct);
        orderProduct.setOrder(null);
    }


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = OrderEntity.Order.VNPAY_PAYMENT_ID)
    private VnpayPayment vnpayPayment;
}
