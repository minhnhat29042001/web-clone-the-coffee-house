package uit.javabackend.webclonethecoffeehouse.product.boundary;


import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uit.javabackend.webclonethecoffeehouse.common.util.ResponseUtil;
import uit.javabackend.webclonethecoffeehouse.product.dto.ProductGroupDTO;
import uit.javabackend.webclonethecoffeehouse.product.service.ProductGroupService;
import uit.javabackend.webclonethecoffeehouse.security.authorization.TCHOperation;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/ProductGroupManagement")

public class ProductGroupRestResource {
    private final ProductGroupService productGroupService;

    public ProductGroupRestResource(ProductGroupService productGroupService) {
        this.productGroupService = productGroupService;
    }

    @GetMapping("common/GetAllProductGroups")
    public Object findAll() {
        return ResponseUtil.get(productGroupService.findAllDto(ProductGroupDTO.class), HttpStatus.OK);
    }

    @GetMapping("/common/{productgroup-id}/GetProductGroupsWithProduct")
    public Object findProductGroupWithProductDTO(@PathVariable("productgroup-id") UUID productGroupID) {
        return ResponseUtil.get(productGroupService.getProductGroupWithProductDTO(productGroupID), HttpStatus.OK);
    }

    @GetMapping("common/GetAllProductGroupsWithProduct")
    public Object findAllProductGroupWithProductDTO() {
        return ResponseUtil.get(productGroupService.getAllProductGroupWithProductDTO(), HttpStatus.OK);
    }

    @Operation(summary = "search product group by name")
    @GetMapping("/common/Search")
    public Object SearchProductGroup(@RequestParam("query") String query) {
        return ResponseUtil.get(productGroupService.searchProductGroup(query), HttpStatus.OK);
    }

    @TCHOperation(name = "AddProductGroup")
    @PostMapping(path = "/AddProductGroup")
    public Object save(@RequestBody @Valid ProductGroupDTO productGroupDTO) {
        return ResponseUtil.get(productGroupService.save(productGroupDTO), HttpStatus.CREATED);
    }

    @TCHOperation(name = "UpdateProductGroup")
    @PutMapping("/UpdateProductGroup")
    public Object updateProductGroup(@RequestBody @Valid ProductGroupDTO productGroup) {
        return ResponseUtil.get(productGroupService.update(productGroup), HttpStatus.OK);
    }

    @TCHOperation(name = "DeleteByNameProductGroup")
    @DeleteMapping("/DeleteByNameProductGroup")
    public Object deleteProductGroup(@RequestParam("name") String name) {
        productGroupService.deleteByName(name);
        return HttpStatus.OK;
    }

    @TCHOperation(name = "AddProductsIntoProductGroup")
    @PostMapping("/{productgroup-id}/AddProducts")
    public ResponseEntity<?> addProduct(@RequestBody List<UUID> ids, @PathVariable("productgroup-id") UUID productGroupId) {
        return ResponseUtil.get(productGroupService.addProduct(ids, productGroupId), HttpStatus.CREATED);
    }

    @TCHOperation(name = "RemoveProductsFromProductGroup")
    @DeleteMapping("/{productgroup-id}/RemoveProducts")
    public ResponseEntity<?> deleteProduct(@RequestBody List<UUID> ids, @PathVariable("productgroup-id") UUID productGroupId) {
        return ResponseUtil.get(productGroupService.removeProduct(ids, productGroupId), HttpStatus.OK);
    }
}
