package filipe.devs.ecom_backend.product.infrastructure.primary;

import filipe.devs.ecom_backend.product.application.ProductsApplicationService;
import filipe.devs.ecom_backend.product.domain.aggregate.FilterQueryBuilder;
import filipe.devs.ecom_backend.product.domain.aggregate.Product;
import filipe.devs.ecom_backend.product.domain.vo.ProductSize;
import filipe.devs.ecom_backend.product.domain.vo.PublicId;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/products-shop")
public class ProductsShopResource {

    private final ProductsApplicationService productsApplicationService;

    public ProductsShopResource(ProductsApplicationService productsApplicationService) {
        this.productsApplicationService = productsApplicationService;
    }

    @GetMapping("/featured")
    public ResponseEntity<Page<RestProduct>> getAllFeatured(Pageable pageable) {
        Page<Product> products = productsApplicationService.getFeaturedProducts(pageable);

        PageImpl<RestProduct> restProducts = new PageImpl<>(
                products.getContent().stream().map(RestProduct::fromDomain).toList(),
                pageable,
                products.getTotalElements()
        );
        return ResponseEntity.ok(restProducts);
    }

    @GetMapping("/find-one")
    public ResponseEntity<RestProduct> getOne(@RequestParam("publicId") UUID id) {
        Optional<Product> productOpt = productsApplicationService.findOne(new PublicId(id));

        return productOpt.map(product -> ResponseEntity.ok(RestProduct.fromDomain(product)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/related")
    public ResponseEntity<Page<RestProduct>> findRelated(Pageable pageable,
                                                         @RequestParam("publicId") UUID id) {
        try {
            Page<Product> products = productsApplicationService.findRelated(pageable, new PublicId(id));
            PageImpl<RestProduct> restProducts = new PageImpl<>(
                    products.getContent().stream().map(RestProduct::fromDomain).toList(),
                    pageable,
                    products.getTotalElements()
            );
            return ResponseEntity.ok(restProducts);
        } catch (EntityNotFoundException enfe) {
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("/filter")
    public ResponseEntity<Page<RestProduct>> filter(Pageable pageable,
                                                    @RequestParam(value = "categoryId", required = false) UUID categoryId,
                                                    @RequestParam(value = "productSizes", required = false) List<ProductSize> productSizes) {
        if (categoryId == null) {
            // @TODO: Refactor this, if categoryId is null, return all products but also filter by sizes if needed
            // Get all products
            Page<Product> products = productsApplicationService.findAllProduct(pageable);
            PageImpl<RestProduct> restProducts = new PageImpl<>(
                    products.getContent().stream().map(RestProduct::fromDomain).toList(),
                    pageable,
                    products.getTotalElements()
            );
            return ResponseEntity.ok(restProducts);
        }

        FilterQueryBuilder filterQueryBuilder = FilterQueryBuilder.filterQuery().categoryPublicId(new PublicId(categoryId));

        if(productSizes != null) {
            filterQueryBuilder.sizes(productSizes);
        }

        Page<Product> products = productsApplicationService.filter(pageable, filterQueryBuilder.build());
        PageImpl<RestProduct> restProducts = new PageImpl<>(
                products.getContent().stream().map(RestProduct::fromDomain).toList(),
                pageable,
                products.getTotalElements()
        );
        return ResponseEntity.ok(restProducts);
    }
}