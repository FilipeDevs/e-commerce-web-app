package filipe.devs.ecom_backend.order.domain.order.aggregate;


import filipe.devs.ecom_backend.order.domain.order.vo.StripeSessionId;
import filipe.devs.ecom_backend.order.domain.user.vo.UserAddressToUpdate;
import org.jilt.Builder;

import java.util.List;

@Builder
public record StripeSessionInformation(StripeSessionId stripeSessionId,
                                       UserAddressToUpdate userAddress,
                                       List<OrderProductQuantity> orderProductQuantity) {
}
