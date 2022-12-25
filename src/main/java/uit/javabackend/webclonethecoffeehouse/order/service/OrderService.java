package uit.javabackend.webclonethecoffeehouse.order.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uit.javabackend.webclonethecoffeehouse.business.dto.UserDiscountDTO;
import uit.javabackend.webclonethecoffeehouse.business.model.Discount;
import uit.javabackend.webclonethecoffeehouse.business.model.UserDiscount;
import uit.javabackend.webclonethecoffeehouse.business.service.DiscountService;
import uit.javabackend.webclonethecoffeehouse.business.service.UserDiscountService;
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
import uit.javabackend.webclonethecoffeehouse.vnp_payment.dto.VnpPaymentCreateDTO;
import uit.javabackend.webclonethecoffeehouse.vnp_payment.service.VnpayPaymentService;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public interface OrderService extends GenericService<Order, OrderDTO, UUID> {


    OrderDTO update(OrderDTO order);

    //void deleteByName(String name);

    OrderDTO save(OrderDTO orderDTO);

    OrderWithVnpayPaymentDTO saveOrderWithVnpayPayment(OrderWithVnpayPaymentDTO orderWithVnpayPaymentDTO);


    Object createOrder(OrderWithProductsDTO orderWithProductsDTO, HttpServletRequest request) throws UnsupportedEncodingException;

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
    private final UserDiscountService userDiscountService;
    private final TCHMapper mapper;
    private final VnpayPaymentService vnpayPaymentService;
    private final DiscountService discountService;

    @Value("${order.id.existed}")
    private TCHBusinessException orderIsNotExisted;

    @Value("${user.username.existed}")
    private TCHBusinessException userIsNotExisted;

    @Value("${orderproduct.id.existed}")
    private TCHBusinessException productIsNotExisted;

    OrderServiceImpl(OrderRepository orderRepository, UserService userService, ProductService productService, OrderProductService orderProductService, UserDiscountService userDiscountService, TCHMapper mapper, @Lazy VnpayPaymentService vnpayPaymentService, DiscountService discountService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.productService = productService;
        this.orderProductService = orderProductService;
        this.userDiscountService = userDiscountService;
        this.mapper = mapper;
        this.vnpayPaymentService = vnpayPaymentService;
        this.discountService = discountService;
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

        // luu userid vao order
        String usernameprincipal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUsername(usernameprincipal);
        order.setUser(user);

        //kiem tra co coupon tren order khong
        if (!orderDTO.getCodeCoupon().isEmpty()) {
            Discount discount = discountService.findByCode(orderDTO.getCodeCoupon())
                    .orElseThrow(() -> new TCHBusinessException("code is not existed!"));

            //kiem tra so luong cua discount(coupon) con khong
            if (discount.getNumbersOfUsers() >= 1) {

                //kiem tra discountId voi userId da co trong userdiscount chua
                Optional<UserDiscount> userDiscount = userDiscountService.findUserDiscountByUserIdAndDiscountId(user.getId(), discount.getId());
                if (userDiscount.isPresent()) {

                    UserDiscount getUserDiscount = userDiscount.get();

                    if (getUserDiscount.getUsedCount() >= discount.getLimitAmountOnUser()) {
                        throw new TCHBusinessException(String.format("moi user chi duoc su dung toi da %s %s", discount.getLimitAmountOnUser(),orderDTO.getCodeCoupon()));
                    } else {
                        getUserDiscount.setUsedCount(getUserDiscount.getUsedCount() + 1);
                    }

                }else{
                    UserDiscountDTO userDiscountDTO = UserDiscountDTO.builder()
                            .description("userdiscount")
                            .usedCount(1)
                            .userId(user.getId())
                            .discountId(discount.getId())
                            .build();
                    userDiscountService.save(userDiscountDTO);
                }

                //luu discountId vao order
                order.setDiscount(discount);
                //cap nhat lai so luong cho discount
                discount.setNumbersOfUsers(discount.getNumbersOfUsers() - 1);
                discountService.save(discount);

            } else {
                throw new TCHBusinessException("da het coupon");
            }
        }


        Order savedOrder = orderRepository.save(order);
        return mapper.map(savedOrder, OrderDTO.class);
    }

    @Override
    public OrderWithVnpayPaymentDTO saveOrderWithVnpayPayment(OrderWithVnpayPaymentDTO orderWithVnpayPaymentDTO) {
        Order order = mapper.map(orderWithVnpayPaymentDTO, Order.class);
        Order savedOrder = orderRepository.save(order);
        return mapper.map(savedOrder, OrderWithVnpayPaymentDTO.class);
    }

    @Transactional(rollbackFor = {TCHBusinessException.class,UnsupportedEncodingException.class})
    @Override
    public Object createOrder(OrderWithProductsDTO orderWithProductsDTO, HttpServletRequest request) throws UnsupportedEncodingException {
        OrderDTO orderDTO = mapper.map(orderWithProductsDTO,OrderDTO.class);
        OrderDTO savedOrder = save(orderDTO);
        List<OrderProductWithProductDTO> orderproducts = orderWithProductsDTO.getOrderProducts();

        orderProductService.saveOrderProductToOrderId(orderproducts,savedOrder.getId());

        VnpPaymentCreateDTO vnpPaymentCreateDTO = VnpPaymentCreateDTO.builder()
                .orderId(savedOrder.getId())
                .amount(savedOrder.getTotalPrice())
                .description("tch")
                .bankcode(orderWithProductsDTO.getBankcode())
                .build();


        return vnpayPaymentService.createPayment(vnpPaymentCreateDTO,request);
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

}
