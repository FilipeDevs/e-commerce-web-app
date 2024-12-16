package filipe.devs.ecom_backend.order.domain.user.vo;


import filipe.devs.ecom_backend.shared.error.domain.Assert;

import java.util.UUID;

public record UserPublicId(UUID value) {

  public UserPublicId {
    Assert.notNull("value", value);
  }
}
