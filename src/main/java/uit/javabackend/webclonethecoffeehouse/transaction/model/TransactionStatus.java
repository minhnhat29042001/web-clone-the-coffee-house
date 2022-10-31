package uit.javabackend.webclonethecoffeehouse.transaction.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import uit.javabackend.webclonethecoffeehouse.common.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = TransactionEntity.TransactionStatus.TABLE_NAME)
public class TransactionStatus extends BaseEntity {

    @Column(name = TransactionEntity.TransactionStatus.status)
    @Length(min = 5, max = 20)
    private String status;

    @OneToMany(mappedBy = TransactionEntity.TransactionMapped.TRANSACTION_MAPPED_STATUS)
    private List<Transaction> transactions = new ArrayList<>();

    // Best practices
    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
        transaction.setStatus(this);
    }

    public void removeTransaction(Transaction transaction) {
        this.transactions.remove(transaction);
        transaction.setStatus(null);
    }

}
