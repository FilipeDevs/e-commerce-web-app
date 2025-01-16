package filipe.devs.ecom_backend.order.domain.order.vo;



import filipe.devs.ecom_backend.shared.error.domain.Assert;

import java.util.UUID;

public record ProductPublicId(UUID value) {

  public ProductPublicId {
    Assert.notNull("value", value);
  }
}
