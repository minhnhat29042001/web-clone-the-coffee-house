package uit.javabackend.webclonethecoffeehouse.order.service;


import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uit.javabackend.webclonethecoffeehouse.common.service.GenericService;
import uit.javabackend.webclonethecoffeehouse.common.util.TCHMapper;
import uit.javabackend.webclonethecoffeehouse.order.dto.OrderProductDTO;
import uit.javabackend.webclonethecoffeehouse.order.dto.OrderProductWithOrderDTO;
import uit.javabackend.webclonethecoffeehouse.order.dto.OrderProductWithProductDTO;
import uit.javabackend.webclonethecoffeehouse.order.model.OrderProduct;
import uit.javabackend.webclonethecoffeehouse.order.repository.OrderProductRepository;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface OrderProductService extends GenericService<OrderProduct, OrderProductDTO, UUID> {

    List<OrderProduct> findAll();

    OrderProduct update (OrderProduct orderProduct);

    OrderProductDTO save (OrderProductDTO orderProductDTO);

    OrderProductWithOrderDTO getOrderProductWithOrderDTO(UUID orderID);

    OrderProductWithProductDTO getOrderProductWithProductDTO(UUID orderID);

    List<OrderProductWithOrderDTO> getAllOrderProductWithOrderDTO();

    List<OrderProductWithProductDTO> getAllOrderProductWithProductDTO();
}

@Service
@Transactional
class OrderProductServiceImp implements OrderProductService{

    private final OrderProductRepository repository;
    private final TCHMapper mapper;
    private final ValidationException OrderProductIsNotExisted = new ValidationException("OrderProduct is not existed.");

    OrderProductServiceImp(OrderProductRepository repository, TCHMapper mapper) {
        this.repository = repository;
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
    @Transactional(readOnly = true)
    public List<OrderProduct> findAll() {
        return repository.findAll();
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
        OrderProduct product =mapper.map(orderProductDTO,OrderProduct.class);
        OrderProduct savedOrderProduct= repository.save(product);
        return mapper.map(savedOrderProduct, OrderProductDTO.class);
    }

    @Override
    public OrderProductWithOrderDTO getOrderProductWithOrderDTO(UUID orderID) {
        OrderProduct orderProduct = repository.findById(orderID).orElseThrow(()->
                OrderProductIsNotExisted
        );
        return mapper.map(orderProduct,OrderProductWithOrderDTO.class);
    }

    @Override
    public OrderProductWithProductDTO getOrderProductWithProductDTO(UUID orderID) {
        OrderProduct product = repository.findById(orderID).orElseThrow(()->
                OrderProductIsNotExisted
        );
        return mapper.map(product, OrderProductWithProductDTO.class);
    }

    @Override
    public List<OrderProductWithOrderDTO> getAllOrderProductWithOrderDTO() {
        List<OrderProduct> orderProductList = repository.findAll();
        List<OrderProductWithOrderDTO> orderProductWithOrderDTOList = new ArrayList<>();
        orderProductList.forEach(
                orderProduct ->{
                    OrderProductWithOrderDTO orderProductWithOrderDTO = mapper.map(orderProduct,OrderProductWithOrderDTO.class);
                    orderProductWithOrderDTOList.add(orderProductWithOrderDTO);
                }
        );
        return orderProductWithOrderDTOList;
    }

    @Override
    public List<OrderProductWithProductDTO> getAllOrderProductWithProductDTO() {
        List<OrderProduct> orderProductList = repository.findAll();
        List<OrderProductWithProductDTO> orderProductWithProductDTOList = new ArrayList<>();
        orderProductList.forEach(
                 orderProduct -> {
                    OrderProductWithProductDTO orderProductWithProductDTO = mapper.map(orderProduct,OrderProductWithProductDTO.class);
                    orderProductWithProductDTOList.add(orderProductWithProductDTO);
                }
        );
        return orderProductWithProductDTOList;
    }
}