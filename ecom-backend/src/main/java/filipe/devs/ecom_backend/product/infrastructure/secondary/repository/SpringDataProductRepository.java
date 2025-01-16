package filipe.devs.ecom_backend.product.infrastructure.secondary.repository;


import filipe.devs.ecom_backend.order.domain.order.vo.ProductPublicId;
import filipe.devs.ecom_backend.product.domain.aggregate.FilterQuery;
import filipe.devs.ecom_backend.product.domain.aggregate.Picture;
import filipe.devs.ecom_backend.product.domain.aggregate.Product;
import filipe.devs.ecom_backend.product.domain.repository.ProductRepository;
import filipe.devs.ecom_backend.product.domain.vo.PublicId;
import filipe.devs.ecom_backend.product.infrastructure.secondary.entity.CategoryEntity;
import filipe.devs.ecom_backend.product.infrastructure.secondary.entity.PictureEntity;
import filipe.devs.ecom_backend.product.infrastructure.secondary.entity.ProductEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public class SpringDataProductRepository implements ProductRepository {

  private final JpaCategoryRepository jpaCategoryRepository;

  private final JpaProductRepository jpaProductRepository;

  private final JpaProductPictureRepository jpaProductPictureRepository;

  public SpringDataProductRepository(JpaCategoryRepository jpaCategoryRepository, JpaProductRepository jpaProductRepository, JpaProductPictureRepository jpaProductPictureRepository) {
    this.jpaCategoryRepository = jpaCategoryRepository;
    this.jpaProductRepository = jpaProductRepository;
    this.jpaProductPictureRepository = jpaProductPictureRepository;
  }

  @Override
  public Product save(Product productToCreate) {
    ProductEntity newProductEntity = ProductEntity.from(productToCreate);
    Optional<CategoryEntity> categoryEntityOpt = jpaCategoryRepository.findByPublicId(newProductEntity.getCategory().getPublicId());
    CategoryEntity categoryEntity = categoryEntityOpt.orElseThrow(() -> new EntityNotFoundException(String.format("No category found with Id %s", productToCreate.getCategory().getPublicId())));
    newProductEntity.setCategory(categoryEntity);
    ProductEntity savedProductEntity = jpaProductRepository.save(newProductEntity);

    saveAllPictures(productToCreate.getPictures(), savedProductEntity);


    return ProductEntity.to(savedProductEntity);

  }

  private void saveAllPictures(List<Picture> pictures, ProductEntity newProductEntity) {
    Set<PictureEntity> picturesEntities = PictureEntity.from(pictures);

    for (PictureEntity picturesEntity : picturesEntities) {
      picturesEntity.setProduct(newProductEntity);
    }

    jpaProductPictureRepository.saveAll(picturesEntities);
  }

  @Override
  public Page<Product> findAll(Pageable pageable) {
    return jpaProductRepository.findAll(pageable).map(ProductEntity::to);
  }

  @Override
  public int delete(PublicId publicId) {
    return jpaProductRepository.deleteByPublicId(publicId.value());
  }

  @Override
  public Page<Product> findAllFeaturedProduct(Pageable pageable) {
    return jpaProductRepository.findAllByFeaturedTrue(pageable).map(ProductEntity::to);
  }

  @Override
  public Optional<Product> findOne(PublicId publicId) {
    return jpaProductRepository.findByPublicId(publicId.value()).map(ProductEntity::to);
  }

  @Override
  public Page<Product> findByCategoryExcludingOne(Pageable pageable, PublicId categoryPublicId, PublicId excludedProductPublicId) {
    return jpaProductRepository.findByCategoryPublicIdAndPublicIdNot(pageable, categoryPublicId.value(),
            excludedProductPublicId.value()).map(ProductEntity::to);
  }

  @Override
  public Page<Product> findByCategoryAndSize(Pageable pageable, FilterQuery filterQuery) {
    return jpaProductRepository.findByCategoryPublicIdAndSizesIn(pageable, filterQuery.categoryPublicId().value(),
            filterQuery.sizes()).map(ProductEntity::to);
  }

  @Override
  public List<Product> findByPublicIds(List<PublicId> publicIds) {
    List<UUID> publicIdsUUIDs = publicIds.stream().map(PublicId::value).toList();
    return jpaProductRepository.findAllByPublicIdIn(publicIdsUUIDs).stream().map(ProductEntity::to).toList();
  }

  @Override
  public void updateQuantity(ProductPublicId productPublicId, long quantity) {
    jpaProductRepository.updateQuantity(productPublicId.value(), quantity);
  }

}
