package uit.javabackend.webclonethecoffeehouse.product.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uit.javabackend.webclonethecoffeehouse.common.service.GenericService;
import uit.javabackend.webclonethecoffeehouse.common.util.TCHMapper;
import uit.javabackend.webclonethecoffeehouse.product.dto.ProductDTO;
import uit.javabackend.webclonethecoffeehouse.product.model.Product;
import uit.javabackend.webclonethecoffeehouse.product.repository.ProductRepository;


import java.util.List;
import java.util.UUID;

public interface ProductService  extends GenericService<Product, ProductDTO, UUID> {
    List<Product> findAll();
    Product update (Product product);
    void deletebyName(String name);
    ProductDTO save (ProductDTO productDTO);


}

@Service
@Transactional
 class ProductServiceImpl implements ProductService{
    private final ProductRepository repository;
    private final TCHMapper mapper;

    ProductServiceImpl(ProductRepository repository, TCHMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
     public List<Product> findAll() {
         return repository.findAll();
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
    public void deletebyName(String name) {
        repository.deleteByName(name);
    }



    @Override
    public ProductDTO save(ProductDTO productDTO) {
        Product product =mapper.map(productDTO,Product.class);
        Product savedProduct= repository.save(product);
        return mapper.map(savedProduct,ProductDTO.class);
    }

    @Override
    public JpaRepository<Product, UUID> getRepository() {
        return this.repository;
    }

    @Override
    public ModelMapper getMapper() {
        return this.mapper;
    }
 }
