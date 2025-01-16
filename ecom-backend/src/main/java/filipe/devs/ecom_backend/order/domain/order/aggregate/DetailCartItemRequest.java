package filipe.devs.ecom_backend.order.domain.order.aggregate;


import filipe.devs.ecom_backend.product.domain.vo.PublicId;
import org.jilt.Builder;

@Builder
public record DetailCartItemRequest(PublicId productId, long quantity) {
}
