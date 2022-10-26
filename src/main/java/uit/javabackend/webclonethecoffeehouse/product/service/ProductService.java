package uit.javabackend.webclonethecoffeehouse.product.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uit.javabackend.webclonethecoffeehouse.common.service.GenericService;
import uit.javabackend.webclonethecoffeehouse.common.util.TCHMapper;
import uit.javabackend.webclonethecoffeehouse.file.FileService;
import uit.javabackend.webclonethecoffeehouse.product.dto.ProductDTO;
import uit.javabackend.webclonethecoffeehouse.product.dto.ProductWithCurrencyDTO;
import uit.javabackend.webclonethecoffeehouse.product.dto.ProductWithProductGroupDTO;
import uit.javabackend.webclonethecoffeehouse.product.model.Product;
import uit.javabackend.webclonethecoffeehouse.product.repository.ProductRepository;


import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface ProductService  extends GenericService<Product, ProductDTO, UUID> {
    List<Product> findAll();
    Product update (Product product);
    void deletebyName(String name);
    ProductDTO save (ProductDTO productDTO);
    ProductWithCurrencyDTO getProductWithCurrencyDTO(UUID productID);
    List<ProductWithCurrencyDTO> getAllProductWithCurrenCyDTO();
    ProductWithProductGroupDTO getProductWithProdcutGroupDTO(UUID productID);
    List<ProductWithProductGroupDTO> getAllProductWithProductGroupDTO();
    ProductDTO saveProductImg(String productName,MultipartFile file,String baseUrl);

}

@Service
@Transactional
 class ProductServiceImpl implements ProductService{
    private final ProductRepository repository;
    private final TCHMapper mapper;
    private final FileService fileService;

    ProductServiceImpl(ProductRepository repository, TCHMapper mapper, FileService fileService) {
        this.repository = repository;
        this.mapper = mapper;
        this.fileService = fileService;
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
         curProduct.setPrice(product.getPrice());
         curProduct.setImgUrl(product.getImgUrl());
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
    public ProductWithCurrencyDTO getProductWithCurrencyDTO(UUID productID) {
        Product product = repository.findById(productID).orElseThrow(()->
                new ValidationException("Product is not existed")
                );
        ProductWithCurrencyDTO productWithCurrencyDTO = mapper.map(product,ProductWithCurrencyDTO.class);
        return productWithCurrencyDTO;
    }

    @Override
    public List<ProductWithCurrencyDTO> getAllProductWithCurrenCyDTO() {
        List<Product> productList = repository.findAll();
        List<ProductWithCurrencyDTO> productWithCurrencyDTOList = new ArrayList<>();
        productList.forEach(
                (product) ->{
                    ProductWithCurrencyDTO productWithCurrencyDTO = mapper.map(product,ProductWithCurrencyDTO.class);
                    productWithCurrencyDTOList.add(productWithCurrencyDTO);
                }
        );
        return productWithCurrencyDTOList;
    }

    @Override
    public ProductWithProductGroupDTO getProductWithProdcutGroupDTO(UUID productID) {
        Product product = repository.findById(productID).orElseThrow(()->
                new ValidationException("Product is not existed")
        );
        ProductWithProductGroupDTO productWithProductGroupDTO = mapper.map(product,ProductWithProductGroupDTO.class);
        return productWithProductGroupDTO;
    }

    @Override
    public List<ProductWithProductGroupDTO> getAllProductWithProductGroupDTO() {
        List<Product> productList = repository.findAll();
        List<ProductWithProductGroupDTO> productWithProductGroupDTOList = new ArrayList<>();
        productList.forEach(
                (product) ->{
                    ProductWithProductGroupDTO productWithProductGroupDTO = mapper.map(product,ProductWithProductGroupDTO.class);
                    productWithProductGroupDTOList.add(productWithProductGroupDTO);
                }
        );
        return productWithProductGroupDTOList;
    }

    @Override
    public ProductDTO saveProductImg(String productName, MultipartFile file,String baseUrl) {
        Product product = repository.findByName(productName).orElseThrow(()->
           new ValidationException("Product is not existed")
        );
        fileService.init();
        fileService.save(file);
        String urlLoadFile = baseUrl+"/api/Files/"+file.getOriginalFilename();
        product.setImgUrl(urlLoadFile);
        return mapper.map(product,ProductDTO.class);
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
