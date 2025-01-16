package filipe.devs.ecom_backend.order.domain.order.aggregate;


import filipe.devs.ecom_backend.order.domain.order.vo.OrderQuantity;
import filipe.devs.ecom_backend.order.domain.order.vo.ProductPublicId;
import org.jilt.Builder;

@Builder
public record OrderProductQuantity(OrderQuantity quantity, ProductPublicId productPublicId) {
}
