package uit.javabackend.webclonethecoffeehouse.order.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uit.javabackend.webclonethecoffeehouse.common.exception.TCHBusinessException;
import uit.javabackend.webclonethecoffeehouse.common.service.GenericService;
import uit.javabackend.webclonethecoffeehouse.common.util.TCHMapper;
import uit.javabackend.webclonethecoffeehouse.order.dto.OrderDTO;
import uit.javabackend.webclonethecoffeehouse.order.dto.OrderProductWithProductDTO;
import uit.javabackend.webclonethecoffeehouse.order.dto.OrderWithProductsDTO;
import uit.javabackend.webclonethecoffeehouse.order.dto.OrderWithVnpayPaymentDTO;
import uit.javabackend.webclonethecoffeehouse.order.model.Order;
import uit.javabackend.webclonethecoffeehouse.order.model.OrderProduct;
import uit.javabackend.webclonethecoffeehouse.order.repository.OrderRepository;
import uit.javabackend.webclonethecoffeehouse.product.model.Product;
import uit.javabackend.webclonethecoffeehouse.product.service.ProductService;
import uit.javabackend.webclonethecoffeehouse.user.model.User;
import uit.javabackend.webclonethecoffeehouse.user.service.UserService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public interface OrderService extends GenericService<Order, OrderDTO, UUID> {


    OrderDTO update(OrderDTO order);

    //void deleteByName(String name);

    OrderDTO save(OrderDTO orderDTO);

    OrderWithVnpayPaymentDTO saveOrderWithVnpayPayment(OrderWithVnpayPaymentDTO orderWithVnpayPaymentDTO);


    OrderWithProductsDTO saveOrder(OrderWithProductsDTO orderDto);

    OrderDTO findOrderByOrderId(UUID id);

    OrderWithVnpayPaymentDTO findOrderWithVnpayPaymentByOrderId(UUID id);

    /**
     * duoc su dung de view danh sach don hang da thuc hien cua user
     */
    List<OrderWithProductsDTO> findAllOrderByUserId(UUID userId); // replace with OderWithUserDto


}


@Service
@Transactional
@PropertySource("classpath:validation/ValidationMessages.properties")
class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductService productService;
    private final OrderProductService orderProductService;
    private final TCHMapper mapper;
    @Value("${order.id.existed}")
    private TCHBusinessException orderIsNotExisted;

    @Value("${user.username.existed}")
    private TCHBusinessException userIsNotExisted;

    @Value("${orderproduct.id.existed}")
    private TCHBusinessException productIsNotExisted;

    OrderServiceImpl(OrderRepository orderRepository, UserService userService, ProductService productService, OrderProductService orderProductService, TCHMapper mapper) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.productService = productService;
        this.orderProductService = orderProductService;
        this.mapper = mapper;
    }


    @Override
    public JpaRepository<Order, UUID> getRepository() {
        return this.orderRepository;
    }

    @Override
    public ModelMapper getMapper() {
        return this.mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return OrderService.super.findAll();
    }

    @Override
    public OrderDTO update(OrderDTO orderDTO) {
        Order curOrder = orderRepository.findById(orderDTO.getId())
                .orElseThrow(() -> orderIsNotExisted);
        curOrder.setCustomerName(orderDTO.getCustomerName());
        curOrder.setAddress(orderDTO.getAddress());
        curOrder.setNote(orderDTO.getNote());
        curOrder.setCodeCoupon(orderDTO.getCodeCoupon());
        curOrder.setTotalPrice(orderDTO.getTotalPrice());
        curOrder.setStatus(orderDTO.getStatus());

        return save(curOrder, Order.class, OrderDTO.class);
    }

//    @Override
//    public void deleteByName(String name) {
//        repository.deleteByCustomerName(name);
//    }

    @Override
    public OrderDTO save(OrderDTO orderDTO) {
        Order order = mapper.map(orderDTO, Order.class);
        Order savedOrder = orderRepository.save(order);
        return mapper.map(savedOrder, OrderDTO.class);
    }

    @Override
    public OrderWithVnpayPaymentDTO saveOrderWithVnpayPayment(OrderWithVnpayPaymentDTO orderWithVnpayPaymentDTO) {
        Order order = mapper.map(orderWithVnpayPaymentDTO, Order.class);
        Order savedOrder = orderRepository.save(order);
        return mapper.map(savedOrder, OrderWithVnpayPaymentDTO.class);
    }

    @Override
    public OrderDTO findOrderByOrderId(UUID id) {
        Order cur = orderRepository.findById(id).orElseThrow(() -> orderIsNotExisted);
        return mapper.map(cur, OrderDTO.class);
    }

    @Override
    public OrderWithVnpayPaymentDTO findOrderWithVnpayPaymentByOrderId(UUID id) {
        Order curOrder = orderRepository.findById(id).orElseThrow(() -> orderIsNotExisted);
        return mapper.map(curOrder, OrderWithVnpayPaymentDTO.class);
    }

    /**
     * view list oder cua user
     *
     * @param userId
     */
    @Override
    public List<OrderWithProductsDTO> findAllOrderByUserId(UUID userId) {

        User curUser = userService.findById(userId).orElseThrow(
                () -> userIsNotExisted
        );

        return curUser.getOrders()
                .stream()
                .map(model -> mapper.map(model, OrderWithProductsDTO.class))
                .collect(Collectors.toList());

    }


    @Override
    public OrderWithProductsDTO saveOrder(OrderWithProductsDTO orderDto) {

        //map to Order
        Order orderConvert = mapper.map(orderDto, Order.class);
        // save Order to db
        Order savedOrder = getRepository().save(orderConvert);

        // map list<OrderProductWithProductDto to List<OrderProduct>
        List<OrderProduct> orderProducts = orderDto.getOrderProductDtos()
                .stream()
                .map(model -> mapper.map(model, OrderProduct.class))
                .collect(Collectors.toList());


//        Order curOrder = orderRepository.findById(savedOrder.getId()).orElseThrow(
//                () -> orderIsNotExisted
//        );
        // add list id orderproduct to Order
        orderProducts.forEach(savedOrder::addOrderProduct);
        // save list<OrderProduct> to db


        // add list id orderproduct to product
        orderProducts.forEach(
                orderProduct -> {
                    UUID productId = orderProduct.getProduct().getId();
                    Product product = productService.findById(productId).orElseThrow(() -> productIsNotExisted);
                    product.addOrderProduct(orderProduct);
                });

        List<OrderProduct> savedOrderProducts = orderProductService.saveAll(orderProducts);


        // map to response
        List<OrderProductWithProductDTO> orderProductWithProductDTOs = savedOrderProducts.stream()
                .map(model -> mapper.map(model, OrderProductWithProductDTO.class))
                .collect(Collectors.toList());

        // response
        OrderWithProductsDTO orderDtoRes = mapper.map(savedOrder, OrderWithProductsDTO.class);
        orderDtoRes.setOrderProductDtos(orderProductWithProductDTOs);
        return orderDtoRes;
    }


}
