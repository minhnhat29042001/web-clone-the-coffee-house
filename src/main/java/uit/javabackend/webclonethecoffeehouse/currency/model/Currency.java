package uit.javabackend.webclonethecoffeehouse.currency.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Length;
import uit.javabackend.webclonethecoffeehouse.common.model.BaseEntity;
import uit.javabackend.webclonethecoffeehouse.product.model.Product;
import uit.javabackend.webclonethecoffeehouse.product.model.ProductEntity;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@Entity
@NoArgsConstructor
@Table(name=CurrencyEntity.Currency.TABLE_NAME)
public class Currency extends BaseEntity {
    @Column(name = CurrencyEntity.Currency.NAME, unique = true,length = 100)
    @Length(min =3,max=100,message = "Currency name must have length between {min} and {max}")
    private String name;

    @OneToMany(mappedBy = CurrencyEntity.CurrencyMappedProduct.PRODUCT_MAPPED_CURRENCY,cascade = CascadeType.ALL)
    private Set<Product> products = new LinkedHashSet<>();

    public void removeProduct(Product product){
        products.remove(product);
        product.setCurrency(null);
    }

    public Currency addProduct(Product product){
        this.products.add(product);
        product.setCurrency(this);
        return this;

    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || Hibernate.getClass(this) != Hibernate.getClass(obj))
            return false;

        Currency currency = (Currency) obj;

        return this.id != null && Objects.equals(this.id, currency.id);
    }
}
