package filipe.devs.ecom_backend.order.domain.order.vo;


import filipe.devs.ecom_backend.shared.error.domain.Assert;

public record OrderPrice(double value) {

  public OrderPrice {
    Assert.field("value", value).strictlyPositive();
  }
}
