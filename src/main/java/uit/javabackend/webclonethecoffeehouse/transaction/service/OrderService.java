package uit.javabackend.webclonethecoffeehouse.transaction.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uit.javabackend.webclonethecoffeehouse.common.service.GenericService;
import uit.javabackend.webclonethecoffeehouse.common.util.TCHMapper;
import uit.javabackend.webclonethecoffeehouse.order.dto.OrderDTO;
import uit.javabackend.webclonethecoffeehouse.order.model.Order;
import uit.javabackend.webclonethecoffeehouse.order.repository.OrderRepository;

import javax.validation.ValidationException;
import java.util.List;
import java.util.UUID;

public interface OrderService extends GenericService<Order, OrderDTO, UUID> {

    List<Order> findAll();

    Order update(Order order);

    void deleteByName(String name);

    OrderDTO save(OrderDTO orderDTO);


}

@Service
@Transactional
class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;
    private final TCHMapper mapper;
    private final ValidationException orderIsNotExisted = new ValidationException("Transaction is not existed.");

    OrderServiceImpl(OrderRepository repository, TCHMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    @Override
    public JpaRepository<Order, UUID> getRepository() {
        return this.repository;
    }

    @Override
    public ModelMapper getMapper() {
        return this.mapper;
    }

    @Override
    public List<Order> findAll() {
        return repository.findAll();
    }

    @Override
    public Order update(Order order) {
        Order curOrder = repository.findById(order.getId())
                .orElseThrow(() -> orderIsNotExisted);
        curOrder.setName(order.getName());
        curOrder.setAddress(order.getAddress());
        curOrder.setNote(order.getNote());
        curOrder.setUseCoupon(order.isUseCoupon());
        curOrder.setTotalPrice(order.getTotalPrice());

        return repository.save(curOrder);
    }

    @Override
    public void deleteByName(String name) {
        repository.deleteByName(name);
    }

    @Override
    public OrderDTO save(OrderDTO orderDTO) {
        Order order = mapper.map(orderDTO, Order.class);
        Order savedOrder = repository.save(order);
        return mapper.map(savedOrder, OrderDTO.class);
    }
}
