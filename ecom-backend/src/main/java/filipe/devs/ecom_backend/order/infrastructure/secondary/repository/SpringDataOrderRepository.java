package filipe.devs.ecom_backend.order.infrastructure.secondary.repository;


import filipe.devs.ecom_backend.order.domain.order.aggregate.Order;
import filipe.devs.ecom_backend.order.domain.order.aggregate.StripeSessionInformation;
import filipe.devs.ecom_backend.order.domain.order.repository.OrderRepository;
import filipe.devs.ecom_backend.order.domain.order.vo.OrderStatus;
import filipe.devs.ecom_backend.order.domain.user.vo.UserPublicId;
import filipe.devs.ecom_backend.order.infrastructure.secondary.entity.OrderEntity;
import filipe.devs.ecom_backend.product.domain.vo.PublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SpringDataOrderRepository implements OrderRepository {

  private final JpaOrderRepository jpaOrderRepository;
  private final JpaOrderedProductRepository jpaOrderedProductRepository;

  public SpringDataOrderRepository(JpaOrderRepository jpaOrderRepository,
                                   JpaOrderedProductRepository jpaOrderedProductRepository) {
    this.jpaOrderRepository = jpaOrderRepository;
    this.jpaOrderedProductRepository = jpaOrderedProductRepository;
  }

  @Override
  public void save(Order order) {
    OrderEntity orderEntityToCreate = OrderEntity.from(order);
    OrderEntity orderSavedEntity = jpaOrderRepository.save(orderEntityToCreate);

    orderSavedEntity.getOrderedProducts()
            .forEach(orderedProductEntity -> orderedProductEntity.getId().setOrder(orderSavedEntity));
    jpaOrderedProductRepository.saveAll(orderSavedEntity.getOrderedProducts());
  }

  @Override
  public void updateStatusByPublicId(OrderStatus orderStatus, PublicId orderPublicId) {
    jpaOrderRepository.updateStatusByPublicId(orderStatus, orderPublicId.value());
  }

  @Override
  public Optional<Order> findByStripeSessionId(StripeSessionInformation stripeSessionInformation) {
    return jpaOrderRepository.findByStripeSessionId(stripeSessionInformation.stripeSessionId().value())
            .map(OrderEntity::toDomain);
  }

  @Override
  public Page<Order> findAllByUserPublicId(UserPublicId userPublicId, Pageable pageable) {
    return jpaOrderRepository.findAllByUserPublicId(userPublicId.value(), pageable)
            .map(OrderEntity::toDomain);
  }

  @Override
  public Page<Order> findAll(Pageable pageable) {
    return jpaOrderRepository.findAll(pageable).map(OrderEntity::toDomain);
  }
}
