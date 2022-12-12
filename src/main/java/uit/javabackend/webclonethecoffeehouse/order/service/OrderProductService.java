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
import uit.javabackend.webclonethecoffeehouse.order.dto.OrderProductDTO;
import uit.javabackend.webclonethecoffeehouse.order.dto.OrderProductWithProductDTO;
import uit.javabackend.webclonethecoffeehouse.order.model.Order;
import uit.javabackend.webclonethecoffeehouse.order.model.OrderProduct;
import uit.javabackend.webclonethecoffeehouse.order.repository.OrderProductRepository;
import uit.javabackend.webclonethecoffeehouse.order.repository.OrderRepository;
import uit.javabackend.webclonethecoffeehouse.product.model.Product;
import uit.javabackend.webclonethecoffeehouse.product.repository.ProductRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


public interface OrderProductService extends GenericService<OrderProduct, OrderProductDTO, UUID> {


    OrderProduct update(OrderProduct orderProduct);

    OrderProductDTO save(OrderProductDTO orderProductDTO);

//    OrderProductWithOrderDTO getOrderProductWithOrderDTO(UUID orderID);
//
//    OrderProductWithProductDTO getOrderProductWithProductDTO(UUID orderID);
//
//    List<OrderProductWithOrderDTO> getAllOrderProductWithOrderDTO();
//
//    List<OrderProductWithProductDTO> getAllOrderProductWithProductDTO();

    List<OrderProduct> saveAll(List<OrderProduct> orderProducts);

    List<OrderProductDTO> getAllByOrderId(UUID orderId);

    List<OrderProductDTO> getAllByProductId(UUID productId);

    OrderProductDTO findOrderProductById(UUID orderProductId);

    List<OrderProductWithProductDTO> saveOrderProductToOrderId(List<OrderProductWithProductDTO> orderProductWithProductDTOS, UUID orderId);

    //

}

@Service
@Transactional
@PropertySource("classpath:validation/ValidationMessages.properties")
class OrderProductServiceImp implements OrderProductService {

    private final OrderProductRepository repository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    private final TCHMapper mapper;

    @Value("${order.id.existed}")
    private TCHBusinessException orderIsNotExisted;

    @Value("${product.name.existed}")
    private TCHBusinessException productIsNotExsited;

    @Value("${product.name.existed}")
    private TCHBusinessException orderProductIsNotExisted;

    OrderProductServiceImp(OrderProductRepository repository, OrderRepository orderRepository, ProductRepository productRepository, TCHMapper mapper) {
        this.repository = repository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.mapper = mapper;
    }


    @Override
    public JpaRepository<OrderProduct, UUID> getRepository() {
        return repository;
    }

    @Override
    public ModelMapper getMapper() {
        return mapper;
    }


    @Override
    public OrderProduct update(OrderProduct orderProduct) {
        OrderProduct curOrderProduct = repository.findById(orderProduct.getId())
                .orElseThrow(() -> new RuntimeException("Order Product is not existed."));

        curOrderProduct.setTotalPrice(orderProduct.getTotalPrice());
        curOrderProduct.setQuantity(orderProduct.getQuantity());
        return repository.save(curOrderProduct);
    }

    @Override
    public OrderProductDTO save(OrderProductDTO orderProductDTO) {
        OrderProduct product = mapper.map(orderProductDTO, OrderProduct.class);
        OrderProduct savedOrderProduct = repository.save(product);
        return mapper.map(savedOrderProduct, OrderProductDTO.class);
    }

//    @Override
//    public OrderProductWithOrderDTO getOrderProductWithOrderDTO(UUID orderID) {
//        OrderProduct orderProduct = repository.findById(orderID).orElseThrow(()->
//                OrderProductIsNotExisted
//        );
//
//
//        return mapper.map(orderProduct,OrderProductWithOrderDTO.class);
//    }
//
//    @Override
//    public OrderProductWithProductDTO getOrderProductWithProductDTO(UUID orderID) {
//        OrderProduct product = repository.findById(orderID).orElseThrow(()->
//                OrderProductIsNotExisted
//        );
//        return mapper.map(product, OrderProductWithProductDTO.class);
//    }

//    @Override
//    public List<OrderProductWithOrderDTO> getAllOrderProductWithOrderDTO() {
//        List<OrderProduct> orderProductList = repository.findAll();
//        List<OrderProductWithOrderDTO> orderProductWithOrderDTOList = new ArrayList<>();
//        orderProductList.forEach(
//                orderProduct ->{
//                    OrderProductWithOrderDTO orderProductWithOrderDTO = mapper.map(orderProduct,OrderProductWithOrderDTO.class);
//                    orderProductWithOrderDTOList.add(orderProductWithOrderDTO);
//                }
//        );
//        return orderProductWithOrderDTOList;
//    }
//
//    @Override
//    public List<OrderProductWithProductDTO> getAllOrderProductWithProductDTO() {
//        List<OrderProduct> orderProductList = repository.findAll();
//        List<OrderProductWithProductDTO> orderProductWithProductDTOList = new ArrayList<>();
//        orderProductList.forEach(
//                 orderProduct -> {
//                    OrderProductWithProductDTO orderProductWithProductDTO = mapper.map(orderProduct,OrderProductWithProductDTO.class);
//                    orderProductWithProductDTOList.add(orderProductWithProductDTO);
//                }
//        );
//        return orderProductWithProductDTOList;
//    }


    @Override
    public List<OrderProduct> saveAll(List<OrderProduct> orderProducts) {
        return repository.saveAll(orderProducts);
    }

    @Override
    public List<OrderProductDTO> getAllByOrderId(UUID orderId) {
        List<OrderProduct> orderProducts;

        if (orderRepository.findById(orderId).isPresent()) {
            orderProducts = repository.findAllByOrderId(orderId);
            return orderProducts.stream()
                    .map(model -> mapper.map(model, OrderProductDTO.class))
                    .collect(Collectors.toList());
        } else {
            throw orderIsNotExisted;
        }

    }

    @Override
    public List<OrderProductDTO> getAllByProductId(UUID productId) {
        List<OrderProduct> orderProducts;

        if (productRepository.findById(productId).isPresent()) {
            orderProducts = repository.findAllByProductId(productId);
            return orderProducts.stream()
                    .map(model -> mapper.map(model, OrderProductDTO.class))
                    .collect(Collectors.toList());
        } else {
            throw productIsNotExsited;
        }

    }

    @Override
    public OrderProductDTO findOrderProductById(UUID orderProductId) {
        OrderProduct orderProduct = repository.findById(orderProductId)
                .orElseThrow(() -> orderProductIsNotExisted);

        return mapper.map(orderProduct, OrderProductDTO.class);
    }

    @Override
    public List<OrderProductWithProductDTO> saveOrderProductToOrderId(List<OrderProductWithProductDTO> orderProductWithProductDTOS, UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> orderIsNotExisted);
        List<OrderProduct> orderProducts = orderProductWithProductDTOS.stream()
                .map(model -> mapper.map(model, OrderProduct.class))
                .collect(Collectors.toList());
        orderProducts.forEach(order::addOrderProduct);

        orderProducts.forEach(
                orderProduct -> {
                    UUID productId = orderProduct.getProduct().getId();
                    Product product = productRepository.findById(productId).orElseThrow(() -> productIsNotExsited);
                    product.addOrderProduct(orderProduct);
                });
        List<OrderProduct> saveOrderProducts = repository.saveAll(orderProducts);

        return saveOrderProducts.stream()
                .map(model -> mapper.map(model, OrderProductWithProductDTO.class))
                .collect(Collectors.toList());

    }


}