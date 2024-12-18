package filipe.devs.ecom_backend.product.domain.vo;

import filipe.devs.ecom_backend.shared.error.domain.Assert;

public record ProductDescription(String value) {

  public ProductDescription {
    Assert.field("value", value).notNull().minLength(10);
  }
}
