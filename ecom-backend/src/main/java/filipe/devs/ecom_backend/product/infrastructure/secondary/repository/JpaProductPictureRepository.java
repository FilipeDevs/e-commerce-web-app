package filipe.devs.ecom_backend.product.infrastructure.secondary.repository;

import filipe.devs.ecom_backend.product.infrastructure.secondary.entity.PictureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductPictureRepository extends JpaRepository<PictureEntity, Long> {
}
