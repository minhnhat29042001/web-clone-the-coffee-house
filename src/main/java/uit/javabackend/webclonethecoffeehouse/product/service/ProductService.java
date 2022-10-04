package uit.javabackend.webclonethecoffeehouse.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uit.javabackend.webclonethecoffeehouse.product.model.Product;
import uit.javabackend.webclonethecoffeehouse.product.repository.ProductRepository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {
    List<Product> findAll();
    Product save(Product product);
    Product update (Product product);
    void delete(String name);

}

@Service
@Transactional
 class ProductServiceImpl implements ProductService{
    private final ProductRepository repository;

    ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
     public List<Product> findAll() {
         return repository.findAll();
     }

     @Override
     public Product save(Product product) {
         return repository.save(product);
     }

     @Override
     public Product update(Product product) {
         Product curProduct = repository.findById(product.getId())
                 .orElseThrow(() -> new RuntimeException("Product is not existed."));
         curProduct.setName(product.getName());
         curProduct.setProductUrl(product.getProductUrl());
         curProduct.setPrice(product.getPrice());
         curProduct.setImgUrl(product.getImgUrl());
         curProduct.setCollectionName(product.getCollectionName());
         curProduct.setDescription(product.getDescription());
        return repository.save(curProduct);
     }

     @Override
     public void delete(String name) {
        repository.deleteByName(name);
     }
 }
