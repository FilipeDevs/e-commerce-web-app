package filipe.devs.ecom_backend.order.domain.user.vo;

import filipe.devs.ecom_backend.shared.error.domain.Assert;

public record UserLastname(String value) {

  public UserLastname {
    Assert.field("value", value).maxLength(255);
  }
}
