package filipe.devs.ecom_backend.order.domain.user.vo;


import filipe.devs.ecom_backend.shared.error.domain.Assert;

public record UserFirstname(String value) {

    public UserFirstname {
        Assert.field("value", value).maxLength(255);
    }
}
