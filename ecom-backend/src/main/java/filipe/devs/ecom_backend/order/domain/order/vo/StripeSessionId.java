package filipe.devs.ecom_backend.order.domain.order.vo;


import filipe.devs.ecom_backend.shared.error.domain.Assert;

public record StripeSessionId(String value) {

  public StripeSessionId {
    Assert.notNull("value", value);
  }
}
