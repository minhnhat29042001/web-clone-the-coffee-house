package uit.javabackend.webclonethecoffeehouse.orderproduct.boundary;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uit.javabackend.webclonethecoffeehouse.common.util.ResponseUtil;
import uit.javabackend.webclonethecoffeehouse.orderproduct.dto.OrderProductDTO;
import uit.javabackend.webclonethecoffeehouse.orderproduct.model.OrderProduct;
import uit.javabackend.webclonethecoffeehouse.orderproduct.service.OrderProductService;
import uit.javabackend.webclonethecoffeehouse.product.dto.ProductDTO;
import uit.javabackend.webclonethecoffeehouse.product.model.Product;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("api/OrderProductsManagement")
public class OrderProductRestResource {

    private final OrderProductService service;

    public OrderProductRestResource(OrderProductService service) {
        this.service = service;
    }

    @GetMapping("/GetAllOrderProducts")
    public Object findAll(){
        return ResponseUtil.get(service.findAllDto(OrderProductDTO.class), HttpStatus.OK);
    }

    @GetMapping("/{orderproduct-id}/GetOrderProductWithOrder")
    public Object findOrderProductWithOrderDTO(@PathVariable("orderproduct-id") UUID orderProductID){
        return ResponseUtil.get(service.getOrderProductWithOrderDTO(orderProductID),HttpStatus.OK);
    }

    @GetMapping("/GetAllOrderProductsWithOrder")
    public Object findAllOrderProductWithOrderDTO(){
        return ResponseUtil.get(service.getAllOrderProductWithOrderDTO(),HttpStatus.OK);
    }

    @GetMapping("/{orderproduct-id}/GetOrderProductWithProduct")
    public Object findOrderProductWithProductDTO(@PathVariable ("orderproduct-id") UUID orderProductID){
        return ResponseUtil.get(service.getOrderProductWithProductDTO(orderProductID),HttpStatus.OK);
    }

    @GetMapping("/GetAllOrderProductsWithProduct")
    public Object findAllProductWithProductGroupDTO(){
        return ResponseUtil.get(service.getAllOrderProductWithProductDTO(),HttpStatus.OK);
    }

    @PostMapping(path = "/AddOrderProduct")
    public Object save(@RequestBody @Valid OrderProductDTO orderProductDTO){
        return ResponseUtil.get(service.save(orderProductDTO),HttpStatus.CREATED);
    }

    @PutMapping("/UpdateOrderProduct")
    public Object update(@RequestBody OrderProduct orderProduct){
        return ResponseUtil.get(service.update(orderProduct),HttpStatus.OK);
    }

}
