package filipe.devs.ecom_backend.product.domain.vo;

import filipe.devs.ecom_backend.shared.error.domain.Assert;

public record CategoryName(String value) {
  public CategoryName {
    Assert.field("value", value).notNull().minLength(3);
  }
}
