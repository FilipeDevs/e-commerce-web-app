package filipe.devs.ecom_backend.order.domain.user.vo;

import filipe.devs.ecom_backend.shared.error.domain.Assert;
import org.jilt.Builder;

@Builder
public record UserAddressToUpdate(UserPublicId userPublicId, UserAddress userAddress) {

    public UserAddressToUpdate {
        Assert.notNull("value", userPublicId);
        Assert.notNull("value", userAddress);
    }
}
