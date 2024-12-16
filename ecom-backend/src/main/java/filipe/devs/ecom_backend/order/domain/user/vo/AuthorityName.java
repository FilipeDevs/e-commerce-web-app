package filipe.devs.ecom_backend.order.domain.user.vo;

import filipe.devs.ecom_backend.shared.error.domain.Assert;

public record AuthorityName(String name) {
    public AuthorityName {
        Assert.field("name", name).notNull();
    }
}
