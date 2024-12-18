package filipe.devs.ecom_backend.product.domain.repository;

import filipe.devs.ecom_backend.product.domain.aggregate.Product;
import filipe.devs.ecom_backend.product.domain.vo.PublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ProductRepository {

  Product save(Product productToCreate);

  Page<Product> findAll(Pageable pageable);

  int delete(PublicId publicId);

}
