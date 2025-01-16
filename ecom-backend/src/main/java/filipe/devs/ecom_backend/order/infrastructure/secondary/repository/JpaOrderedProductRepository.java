package filipe.devs.ecom_backend.order.infrastructure.secondary.repository;


import filipe.devs.ecom_backend.order.infrastructure.secondary.entity.OrderedProductEntity;
import filipe.devs.ecom_backend.order.infrastructure.secondary.entity.OrderedProductEntityPk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderedProductRepository extends JpaRepository<OrderedProductEntity, OrderedProductEntityPk> {

}
