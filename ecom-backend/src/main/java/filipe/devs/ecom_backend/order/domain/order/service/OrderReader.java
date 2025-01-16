package filipe.devs.ecom_backend.order.domain.order.service;


import filipe.devs.ecom_backend.order.domain.order.aggregate.Order;
import filipe.devs.ecom_backend.order.domain.order.repository.OrderRepository;
import filipe.devs.ecom_backend.order.domain.user.vo.UserPublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class OrderReader {

  private final OrderRepository orderRepository;

  public OrderReader(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public Page<Order> findAllByUserPublicId(UserPublicId userPublicId, Pageable pageable) {
    return orderRepository.findAllByUserPublicId(userPublicId, pageable);
  }

  public Page<Order> findAll(Pageable pageable) {
    return orderRepository.findAll(pageable);
  }
}
