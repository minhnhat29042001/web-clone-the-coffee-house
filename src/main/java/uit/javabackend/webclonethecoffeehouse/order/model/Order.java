package uit.javabackend.webclonethecoffeehouse.order.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import uit.javabackend.webclonethecoffeehouse.business.model.Discount;
import uit.javabackend.webclonethecoffeehouse.common.model.BaseEntity;
import uit.javabackend.webclonethecoffeehouse.order.enums.OrderStatus;
import uit.javabackend.webclonethecoffeehouse.payment.model.Payment;
import uit.javabackend.webclonethecoffeehouse.payment.model.Transaction;
import uit.javabackend.webclonethecoffeehouse.payment.model.TransactionEntity;
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
    @Column(name = OrderEntity.Order.CUSTOMER_NAME,unique = true)
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
    @ManyToOne
    @JoinColumn(name = OrderEntity.Order.USER_ID)
    private User user;

    @ManyToOne
    @JoinColumn(name = OrderEntity.Order.DISCOUNT_ID)
    private Discount discount;

//    @ManyToOne
//    @JoinColumn(name = OrderEntity.Order.PAYMENT_ID)
//    private Payment payment;


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


    // relationship Bidirectional
    @OneToMany(mappedBy = TransactionEntity.TransactionMapped.TRANSACTION_MAPPED_ODER)
    List<Transaction> transactions = new ArrayList<>();

    // Best practices
    public Order addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
        transaction.setOrder(this);
        return this;
    }

    public void removeTransaction(Transaction transaction) {
        this.transactions.remove(transaction);
        transaction.setOrder(null);
    }
}
