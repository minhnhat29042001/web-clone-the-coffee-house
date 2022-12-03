package uit.javabackend.webclonethecoffeehouse.product.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uit.javabackend.webclonethecoffeehouse.common.service.GenericService;
import uit.javabackend.webclonethecoffeehouse.common.util.TCHMapper;
import uit.javabackend.webclonethecoffeehouse.product.dto.ProductGroupDTO;
import uit.javabackend.webclonethecoffeehouse.product.dto.ProductGroupWithProductsDTO;
import uit.javabackend.webclonethecoffeehouse.product.model.Product;
import uit.javabackend.webclonethecoffeehouse.product.model.ProductGroup;
import uit.javabackend.webclonethecoffeehouse.product.repository.ProductGroupRepository;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface ProductGroupService extends GenericService<ProductGroup, ProductGroupDTO, UUID> {
//    @Override
//    List<ProductGroup> findAll();


    ProductGroup update (ProductGroupDTO productGroupDTO);
    ProductGroupDTO save (ProductGroupDTO productGroupDTO);
    void deleteByName (String name);
    ProductGroupWithProductsDTO addProduct(List<UUID> ids, UUID productGroupId);
    ProductGroupWithProductsDTO removeProduct(List<UUID> ids, UUID productGroupId);
    ProductGroupWithProductsDTO getProductGroupWithProductDTO (UUID productGroupId);
    List<ProductGroupWithProductsDTO> getAllProductGroupWithProductDTO ();
}
@Service
@Transactional
class ProductGroupServiceImpl implements ProductGroupService {
    private final ProductGroupRepository repository;
    private final TCHMapper mapper;
    private final ProductService productService;
    private final ValidationException productGroupIsNotExisted = new ValidationException("Product is not existed.");

    ProductGroupServiceImpl(ProductGroupRepository repository, TCHMapper mapper, ProductService productService) {
        this.repository = repository;
        this.mapper = mapper;
        this.productService = productService;
    }

    @Override
    public JpaRepository<ProductGroup, UUID> getRepository() {
        return this.repository;
    }

    @Override
    public ModelMapper getMapper() {
        return this.mapper;
    }

//    @Override
//    public List<ProductGroup> findAll() {
//        return repository.findAll();
//    }

    @Override
    public ProductGroup update(ProductGroupDTO productGroupDTO) {
        ProductGroup curProductGroup = repository.findByName(productGroupDTO.getName())
                .orElseThrow(() ->productGroupIsNotExisted);
        curProductGroup.setName(productGroupDTO.getName());
        return repository.save(curProductGroup);
    }

    @Override
    public ProductGroupDTO save(ProductGroupDTO productGroupDTO) {
        ProductGroup productGroup =mapper.map(productGroupDTO, ProductGroup.class);
        ProductGroup savedProductGroup = repository.save(productGroup);
        return mapper.map(savedProductGroup, ProductGroupDTO.class);
    }

    @Override
    public void deleteByName(String name) {
        repository.deleteByName(name);
    }

    @Override
    public ProductGroupWithProductsDTO addProduct(List<UUID> ids, UUID productGroupId) {
        ProductGroup productGroup = repository.findById(productGroupId).orElseThrow( () ->
                productGroupIsNotExisted
        );
        List<Product> products = productService.findByIds(ids);
        products.forEach(productGroup::addProduct);
        return mapper.map(productGroup, ProductGroupWithProductsDTO.class);
    }

    @Override
    public ProductGroupWithProductsDTO removeProduct(List<UUID> ids, UUID productGroupId) {
        ProductGroup productGroup = repository.findById(productGroupId).orElseThrow( () ->
                productGroupIsNotExisted
        );
        List<Product> products = productService.findByIds(ids);
        products.forEach(productGroup::removeProduct);
        return mapper.map(productGroup, ProductGroupWithProductsDTO.class);
    }

    @Override
    public ProductGroupWithProductsDTO getProductGroupWithProductDTO(UUID productGroupId) {
        ProductGroup productGroup = repository.findById(productGroupId).orElseThrow(()->
                productGroupIsNotExisted
        );
        return mapper.map(productGroup,ProductGroupWithProductsDTO.class);

    }

    @Override
    public List<ProductGroupWithProductsDTO> getAllProductGroupWithProductDTO() {
        List<ProductGroup> productGroupList = repository.findAll();
        List<ProductGroupWithProductsDTO> productGroupWithProductsDTOList = new ArrayList<>();
        productGroupList.forEach(
                productGroup ->{
                    ProductGroupWithProductsDTO productGroupWithProductsDTO = mapper.map(productGroup,ProductGroupWithProductsDTO.class);
                    productGroupWithProductsDTOList.add(productGroupWithProductsDTO);
                }
        );
        return productGroupWithProductsDTOList;
    }


}
