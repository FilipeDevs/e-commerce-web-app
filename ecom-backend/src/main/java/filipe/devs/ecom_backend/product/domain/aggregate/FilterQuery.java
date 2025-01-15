package filipe.devs.ecom_backend.product.domain.aggregate;

import filipe.devs.ecom_backend.product.domain.vo.ProductSize;
import filipe.devs.ecom_backend.product.domain.vo.PublicId;
import org.jilt.Builder;

import java.util.List;

@Builder
public record FilterQuery(PublicId categoryPublicId, List<ProductSize> sizes) {
}
