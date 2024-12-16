package filipe.devs.ecom_backend.order.domain.user.vo;

import filipe.devs.ecom_backend.shared.error.domain.Assert;

public record UserImageUrl(String value) {

    public UserImageUrl {
        Assert.field("value", value).maxLength(1000);
    }
}
