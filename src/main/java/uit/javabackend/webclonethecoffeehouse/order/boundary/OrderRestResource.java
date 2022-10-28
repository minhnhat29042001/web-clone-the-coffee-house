package uit.javabackend.webclonethecoffeehouse.order.boundary;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uit.javabackend.webclonethecoffeehouse.common.util.ResponseUtil;
import uit.javabackend.webclonethecoffeehouse.order.dto.OrderDTO;
import uit.javabackend.webclonethecoffeehouse.order.model.Order;
import uit.javabackend.webclonethecoffeehouse.order.service.OrderService;
import uit.javabackend.webclonethecoffeehouse.product.dto.ProductDTO;
import uit.javabackend.webclonethecoffeehouse.product.model.Product;

import javax.validation.Valid;

@RestController
@RequestMapping("api/OrdersManagement")
public class OrderRestResource {
    private final OrderService orderService;

    public OrderRestResource(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/GetAllOrders")
    public Object findAll(){
        return ResponseUtil.get(orderService.findAllDto(OrderDTO.class), HttpStatus.OK);
    }

    @PostMapping(path = "/AddOrder")
    public Object save(@RequestBody @Valid OrderDTO orderDTO){
        return ResponseUtil.get(orderService.save(orderDTO),HttpStatus.CREATED);
    }

    @PutMapping("/UpdateOrder")
    public Object update(@RequestBody Order order){
        return ResponseUtil.get(orderService.update(order),HttpStatus.OK);
    }

    @DeleteMapping("/DeleteOrder")
    public Object delete(@RequestParam("name")  String name){
        orderService.deleteByName(name);
        return HttpStatus.OK;
    }

}
