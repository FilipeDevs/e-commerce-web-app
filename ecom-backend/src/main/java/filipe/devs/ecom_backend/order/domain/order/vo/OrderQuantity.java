package filipe.devs.ecom_backend.order.domain.order.vo;


import filipe.devs.ecom_backend.shared.error.domain.Assert;

public record OrderQuantity(long value) {

  public OrderQuantity {
    Assert.field("value", value).positive();

  }
}
