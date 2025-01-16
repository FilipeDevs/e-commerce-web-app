package filipe.devs.ecom_backend.order.domain.order.service;



import filipe.devs.ecom_backend.order.domain.order.aggregate.DetailCartResponse;
import filipe.devs.ecom_backend.order.domain.order.aggregate.DetailCartResponseBuilder;
import filipe.devs.ecom_backend.product.domain.aggregate.Product;
import filipe.devs.ecom_backend.product.domain.aggregate.ProductCart;

import java.util.List;

public class CartReader {

  public CartReader() {
  }

  public DetailCartResponse getDetails(List<Product> products) {
    List<ProductCart> cartProducts = products.stream().map(ProductCart::from).toList();
    return DetailCartResponseBuilder.detailCartResponse().products(cartProducts)
      .build();
  }
}
