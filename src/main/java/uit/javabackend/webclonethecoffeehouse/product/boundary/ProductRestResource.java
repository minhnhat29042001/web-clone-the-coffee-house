package uit.javabackend.webclonethecoffeehouse.product.boundary;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uit.javabackend.webclonethecoffeehouse.common.util.ResponseUtil;
import uit.javabackend.webclonethecoffeehouse.product.dto.ProductDTO;
import uit.javabackend.webclonethecoffeehouse.product.model.Product;
import uit.javabackend.webclonethecoffeehouse.product.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("api/ProductsManagement")
public class ProductRestResource {
    private final ProductService productService;

    public ProductRestResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/GetAllProducts")
    public Object findAll(){
        return ResponseUtil.get(productService.findAllDto(ProductDTO.class), HttpStatus.OK);
    }

    @GetMapping("/{product-id}/GetProductWithCurrency")
    public Object findProductWithCurrencyDTO(@PathVariable ("product-id") UUID productID){
        return ResponseUtil.get(productService.getProductWithCurrencyDTO(productID),HttpStatus.OK);
    }

    @GetMapping("/GetAllProductsWithCurrency")
    public Object findAllProductWithCurrencyDTO(){
        return ResponseUtil.get(productService.getAllProductWithCurrenCyDTO(),HttpStatus.OK);
    }

    @GetMapping("/{product-id}/GetProductWithProductGroup")
    public Object findProductWithProductGroupDTO(@PathVariable ("product-id") UUID productID){
        return ResponseUtil.get(productService.getProductWithProductGroupDTO(productID),HttpStatus.OK);
    }

    @GetMapping("/GetAllProductsWithProductGroup")
    public Object findAllProductWithProductGroupDTO(){
        return ResponseUtil.get(productService.getAllProductWithProductGroupDTO(),HttpStatus.OK);
    }

    /*
        Khi thêm lần đầu thì Fe có thể để chỗ imgUrl là null hoặc rỗng vì Api này trả về ProductDTO nó chứa cả (imgUrl) sau đó có thể gọi Api AddProductImg để lưu link ảnh cho product
     */
    @PostMapping(path = "/AddProduct")
    public Object save(@RequestBody @Valid ProductDTO productDTO){
        return ResponseUtil.get(productService.save(productDTO),HttpStatus.CREATED);
    }


    @PostMapping(path = "/AddProductImg",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Object saveProudctImg(@RequestParam("productName") String productName, @RequestPart("productimg") MultipartFile productImg,HttpServletRequest request){
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();
        return ResponseUtil.get(productService.saveProductImg(productName,productImg,baseUrl),HttpStatus.CREATED);
    }



    @PutMapping("/UpdateProduct")
    public Object update(@RequestBody Product product){
        return ResponseUtil.get(productService.update(product),HttpStatus.OK);
    }

    @DeleteMapping("/DeleteProduct")
    public Object delete(@RequestParam("name")  String name){
        productService.deleteByName(name);
        return HttpStatus.OK;
    }
}
