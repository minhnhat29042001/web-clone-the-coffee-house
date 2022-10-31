package uit.javabackend.webclonethecoffeehouse.transaction.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import uit.javabackend.webclonethecoffeehouse.common.model.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@SuperBuilder
@NoArgsConstructor
@Setter
@Getter
public class Payment extends BaseEntity {

    @OneToMany(mappedBy = TransactionEntity.TransactionMapped.TRANSACTION_MAPPED_PAYMENT)
    List<Transaction> transactions = new ArrayList<>();
    @Column(name = TransactionEntity.Payment.NAME)
    @Length(min = 5, max = 15, message = "")
    private String name;
    @Length(min = 5, max = 10, message = "")
    @Column(name = TransactionEntity.Payment.CODE)
    private String code;
    @Length(max = 250, message = "")
    @Column(name = TransactionEntity.Payment.DESCRIPTION)
    private String description;
    @Enumerated(value = EnumType.STRING)
    @Column(name = TransactionEntity.Payment.METHOD)
    private MethodPayment method;
    @Column(name = TransactionEntity.Payment.URL)
    private String imageUrl;
    @Column(name = TransactionEntity.Payment.PARTNER_CODE)
    private String partnerCode;
    @Column(name = TransactionEntity.Payment.PRIVATE_KEY)
    private String privateKey;
    @Column(name = TransactionEntity.Payment.PUBLIC_KEY)
    private String publicKey;

    // Best practices
    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
        transaction.setPayment(this);
    }

    public void removeTransaction(Transaction transaction) {
        this.transactions.remove(transaction);
        transaction.setPayment(null);
    }

    public enum MethodPayment {
        COD, ONLINE
    }
}

