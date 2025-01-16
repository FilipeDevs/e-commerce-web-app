package filipe.devs.ecom_backend.order.domain.order.repository;



import filipe.devs.ecom_backend.order.domain.order.aggregate.Order;
import filipe.devs.ecom_backend.order.domain.order.aggregate.StripeSessionInformation;
import filipe.devs.ecom_backend.order.domain.order.vo.OrderStatus;
import filipe.devs.ecom_backend.order.domain.user.vo.UserPublicId;
import filipe.devs.ecom_backend.product.domain.vo.PublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrderRepository {

  void save(Order order);

  void updateStatusByPublicId(OrderStatus orderStatus, PublicId orderPublicId);

  Optional<Order> findByStripeSessionId(StripeSessionInformation stripeSessionInformation);

  Page<Order> findAllByUserPublicId(UserPublicId userPublicId, Pageable pageable);

  Page<Order> findAll(Pageable pageable);



}
