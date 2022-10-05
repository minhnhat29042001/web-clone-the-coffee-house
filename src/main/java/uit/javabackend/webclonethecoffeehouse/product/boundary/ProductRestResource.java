package uit.javabackend.webclonethecoffeehouse.product.boundary;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uit.javabackend.webclonethecoffeehouse.common.util.ResponseUtil;
import uit.javabackend.webclonethecoffeehouse.product.dto.ProductDTO;
import uit.javabackend.webclonethecoffeehouse.product.model.Product;
import uit.javabackend.webclonethecoffeehouse.product.service.ProductService;

import javax.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductRestResource {
    private final ProductService productService;

    public ProductRestResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Object findAll(){
        return ResponseUtil.get(productService.findAllDto(ProductDTO.class), HttpStatus.OK);
    }

    @PostMapping
    public Object save(@RequestBody @Valid ProductDTO productDTO){
        return new ResponseEntity<>(productService.save(productDTO),HttpStatus.CREATED);
    }
}
