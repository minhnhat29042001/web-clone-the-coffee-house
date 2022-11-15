package uit.javabackend.webclonethecoffeehouse.product.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Length;
import uit.javabackend.webclonethecoffeehouse.common.model.BaseEntity;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
@Table(name = ProductGroupEntity.ProductGroup.TABLE_NAME)
public class ProductGroup extends BaseEntity {
        @Column(name = ProductGroupEntity.ProductGroup.NAME)
        @Length(min = 3,max = 100,message = "ProductGroup name must have lenght between {min} and {max}")
        private String name;

    @OneToMany(mappedBy = ProductEntity.ProductMappedCollection.PRODUCTGROUP_MAPPED_PRODUCT, cascade = CascadeType.ALL)
    private Set<Product> products = new LinkedHashSet<>();

        public void removeProduct(Product product){
                products.remove(product);
                product.setProductGroup(null);
        }

        public ProductGroup addProduct(Product product){
                this.products.add(product);
                product.setProductGroup(this);
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

                ProductGroup productGroup = (ProductGroup) obj;

                return this.id != null && Objects.equals(this.id, productGroup.id);
        }
}
