package filipe.devs.ecom_backend.order.domain.order.aggregate;


import filipe.devs.ecom_backend.product.domain.aggregate.ProductCart;
import org.jilt.Builder;

import java.util.List;

@Builder
public class DetailCartResponse {

  List<ProductCart> products;

  public DetailCartResponse(List<ProductCart> products) {
    this.products = products;
  }

  public List<ProductCart> getProducts() {
    return products;
  }

  public void setProducts(List<ProductCart> products) {
    this.products = products;
  }
}
