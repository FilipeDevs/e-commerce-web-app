package filipe.devs.ecom_backend.product.domain.vo;

import filipe.devs.ecom_backend.shared.error.domain.Assert;

import java.util.UUID;

public record PublicId(UUID value) {

  public PublicId {
    Assert.notNull("value", value);
  }
}
