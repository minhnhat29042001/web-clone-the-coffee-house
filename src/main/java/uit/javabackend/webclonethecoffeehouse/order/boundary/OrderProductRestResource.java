package uit.javabackend.webclonethecoffeehouse.order.boundary;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uit.javabackend.webclonethecoffeehouse.common.util.ResponseUtil;
import uit.javabackend.webclonethecoffeehouse.order.dto.OrderProductDTO;
import uit.javabackend.webclonethecoffeehouse.order.model.OrderProduct;
import uit.javabackend.webclonethecoffeehouse.order.service.OrderProductService;

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
