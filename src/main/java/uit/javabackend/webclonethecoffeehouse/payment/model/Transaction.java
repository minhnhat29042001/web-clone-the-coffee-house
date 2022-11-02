package uit.javabackend.webclonethecoffeehouse.payment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import uit.javabackend.webclonethecoffeehouse.common.model.BaseEntity;
import uit.javabackend.webclonethecoffeehouse.order.model.Order;
import uit.javabackend.webclonethecoffeehouse.user.model.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = TransactionEntity.Transaction.TABLE_NAME)
public class Transaction extends BaseEntity {

    @ManyToOne
    @Column(name = TransactionEntity.Transaction.USER_ID)
    private User user;

    @ManyToOne
    @Column(name = TransactionEntity.Transaction.ORDER_ID)
    private Order order;

    @ManyToOne
    @Column(name = TransactionEntity.Transaction.PAYMENT_ID)
    private Payment payment;

    @Column(name = TransactionEntity.Transaction.STATUS)
    private Status status;

    public enum Status {
        NEW, CANCELLED, FAILED, PENDING, DECLINED, REJECTED, SUCCESS
    }
}
