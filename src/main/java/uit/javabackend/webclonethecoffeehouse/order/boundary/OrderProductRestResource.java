package uit.javabackend.webclonethecoffeehouse.order.boundary;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uit.javabackend.webclonethecoffeehouse.common.util.ResponseUtil;
import uit.javabackend.webclonethecoffeehouse.order.dto.OrderProductDTO;
import uit.javabackend.webclonethecoffeehouse.order.dto.OrderProductWithProductDTO;
import uit.javabackend.webclonethecoffeehouse.order.model.OrderProduct;
import uit.javabackend.webclonethecoffeehouse.order.service.OrderProductService;
import uit.javabackend.webclonethecoffeehouse.security.authorization.TCHOperation;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/order-product")
@CrossOrigin(origins = "*")
public class OrderProductRestResource {

    private final OrderProductService service;

    public OrderProductRestResource(OrderProductService service) {
        this.service = service;
    }

    @TCHOperation(name = "GetAllOrderProduct")
    @GetMapping("/get-all")
    public Object findAll() {
        return ResponseUtil.get(service.findAllDto(OrderProductDTO.class), HttpStatus.OK);
    }

//    @GetMapping("/{orderproduct-id}/GetOrderProductWithOrder")
//    public Object findOrderProductWithOrderDTO(@PathVariable("orderproduct-id") UUID orderProductID){
//        return ResponseUtil.get(service.getOrderProductWithOrderDTO(orderProductID),HttpStatus.OK);
//    }
//
//    @GetMapping("/GetAllOrderProductsWithOrder")
//    public Object findAllOrderProductWithOrderDTO(){
//        return ResponseUtil.get(service.getAllOrderProductWithOrderDTO(),HttpStatus.OK);
//    }
//
//    @GetMapping("/{orderproduct-id}/GetOrderProductWithProduct")
//    public Object findOrderProductWithProductDTO(@PathVariable ("orderproduct-id") UUID orderProductID){
//        return ResponseUtil.get(service.getOrderProductWithProductDTO(orderProductID),HttpStatus.OK);
//    }
//
//    @GetMapping("/GetAllOrderProductsWithProduct")
//    public Object findAllProductWithProductGroupDTO(){
//        return ResponseUtil.get(service.getAllOrderProductWithProductDTO(),HttpStatus.OK);
//    }

    @TCHOperation(name = "SaveOrderProductToOrderId")
    @PostMapping(path = "/add-orderproducts/{order-id}")
    public Object save(@PathVariable("order-id") UUID orderId, @RequestBody List<OrderProductWithProductDTO> orderProductWithProductDTOS) {
        return ResponseUtil.get(service.saveOrderProductToOrderId(orderProductWithProductDTOS, orderId), HttpStatus.CREATED);
    }

    @TCHOperation(name = "GetAllOrderProductByOrderId")
    @Operation(summary = "get list orderProduct by orderId")
    @GetMapping(path = "order/{order-id}")
    public Object findAllOrderProductByOrderId(@PathVariable("order-id") UUID orderId) {
        return ResponseUtil.get(service.getAllByOrderId(orderId), HttpStatus.CREATED);
    }

    @TCHOperation(name = "GetAllOrderProductByProductId")
    @Operation(summary = "get list orderProduct by productId")
    @GetMapping(path = "product/{product-id}")
    public Object findAllOrderProductByProductId(@PathVariable("product-id") UUID productId) {
        return ResponseUtil.get(service.getAllByProductId(productId), HttpStatus.CREATED);
    }

    @TCHOperation(name = "GetOrderProduct")
    @Operation(summary = "get orderProduct by Id")
    @GetMapping(path = "{orderproduct-id}")
    public Object findOrderProductById(@PathVariable("orderproduct-id") UUID orderProductId) {
        return ResponseUtil.get(service.findById(orderProductId), HttpStatus.CREATED);
    }

    @TCHOperation(name = "UpdateOrderProduct")
    @PutMapping("/UpdateOrderProduct")
    public Object update(@RequestBody OrderProduct orderProduct) {
        return ResponseUtil.get(service.update(orderProduct), HttpStatus.OK);
    }

}
