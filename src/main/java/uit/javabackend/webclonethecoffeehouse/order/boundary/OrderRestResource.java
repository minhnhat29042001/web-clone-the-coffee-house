package uit.javabackend.webclonethecoffeehouse.order.boundary;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uit.javabackend.webclonethecoffeehouse.common.util.ResponseUtil;
import uit.javabackend.webclonethecoffeehouse.order.dto.OrderDTO;
import uit.javabackend.webclonethecoffeehouse.order.dto.OrderWithProductsDTO;
import uit.javabackend.webclonethecoffeehouse.order.service.OrderService;
import uit.javabackend.webclonethecoffeehouse.security.authorization.TCHOperation;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/orders")
public class OrderRestResource {
    private final OrderService orderService;

    public OrderRestResource(OrderService orderService) {
        this.orderService = orderService;
    }

    @TCHOperation(name = "GetAllOrder")
    @Operation(summary = "get list order Dto, for admin tool ")
    @GetMapping("/get-all-dto")
    public Object findAllDto() {
        return ResponseUtil.get(orderService.findAllDto(OrderDTO.class), HttpStatus.OK);
    }

    @TCHOperation(name = "GetAllOrder")
    @Operation(summary = "get list order Dto, for admin tool")
    @GetMapping("/get-all-dto-page")
    public Object findAllDtoPaging(@RequestParam("size") int size
            , @RequestParam("index") int index) {
        return ResponseUtil.get(orderService.findAllDto(Pageable.ofSize(size).withPage(index), OrderDTO.class), HttpStatus.OK);
    }

    @TCHOperation(name = "GetOrderById")
    @Operation(summary = "get an order by id")
    @GetMapping("/get-order")
    public Object findOrderById(@RequestParam("order-id") UUID id) {
        return ResponseUtil.get(orderService.findOrderByOrderId(id), HttpStatus.OK);
    }


    @TCHOperation(name = "GetAllOrderByUserId")
    @Operation(summary = "get all order by User id")
    @PostMapping("{user-id}/get-all")
    public Object findAllOrderByUserId(@PathVariable("user-id") UUID userId) {
        return ResponseUtil.get(orderService.findAllOrderByUserId(userId), HttpStatus.OK);
    }

    @TCHOperation(name = "SaveOrder")
    @Operation(summary = "su dung thang nay de tao don hang ")
    @PostMapping(path = "/save-order")
    public Object save(@RequestBody @Valid OrderDTO orderDTO) {
        return ResponseUtil.get(orderService.save(orderDTO), HttpStatus.CREATED);
    }

    @TCHOperation(name = "UpdateOrder")
    @PutMapping("/update")
    public Object update(@RequestBody OrderDTO orderDTO) {
        return ResponseUtil.get(orderService.update(orderDTO), HttpStatus.OK);
    }

    @TCHOperation(name = "SaveOrder")
    @Operation(summary = "")
    @PostMapping("/create-order")
    public Object saveOrder(@RequestBody OrderWithProductsDTO orderDto) {
        return ResponseUtil.get(orderService.saveOrder(orderDto), HttpStatus.OK);
    }
}
