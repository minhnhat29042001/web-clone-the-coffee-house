package uit.javabackend.webclonethecoffeehouse.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import uit.javabackend.webclonethecoffeehouse.common.model.BaseEntity;
import uit.javabackend.webclonethecoffeehouse.product.model.ProductEntity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "EmailAndJWT")
public class EmailAndJWT extends BaseEntity {

    @Column(name = "email", unique = true)

    private String email;

    @Column(name = "jwt" ,unique = true)
    private String jwt;

}