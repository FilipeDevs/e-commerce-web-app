package filipe.devs.ecom_backend.order.infrastructure.primary.order;


import filipe.devs.ecom_backend.order.domain.order.vo.StripeSessionId;
import org.jilt.Builder;

@Builder
public record RestStripeSession(String id) {


  public static RestStripeSession from(StripeSessionId stripeSessionId) {
    return RestStripeSessionBuilder.restStripeSession()
      .id(stripeSessionId.value())
      .build();
  }
}
