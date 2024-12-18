package filipe.devs.ecom_backend.product.domain.vo;

import filipe.devs.ecom_backend.shared.error.domain.Assert;

public record ProductPrice(double value) {

  public ProductPrice {
    Assert.field("value", value).min(0.1);
  }
}
