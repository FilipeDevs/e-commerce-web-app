package filipe.devs.ecom_backend.product.domain.service;

import filipe.devs.ecom_backend.product.domain.aggregate.FilterQuery;
import filipe.devs.ecom_backend.product.domain.aggregate.Product;
import filipe.devs.ecom_backend.product.domain.repository.ProductRepository;
import filipe.devs.ecom_backend.product.domain.vo.PublicId;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public class ProductShop {

    private ProductRepository productRepository;

    public ProductShop(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> getFeaturedProducts(Pageable pageable) {
        return productRepository.findAllFeaturedProduct(pageable);
    }

    public Page<Product> findRelated(Pageable pageable, PublicId productPublicId) {
        Optional<Product> productOpt = productRepository.findOne(productPublicId);

        if(productOpt.isPresent()) {
            Product product = productOpt.get();
            return productRepository.findByCategoryExcludingOne(pageable, product.getCategory().getPublicId(), product.getPublicId());
        } else {
            throw new EntityNotFoundException(String.format("No product found with id %s", productPublicId));
        }
    }

    public Page<Product> filter(Pageable pageable, FilterQuery filterQuery) {
        return productRepository.findByCategoryAndSize(pageable, filterQuery);
    }
}
