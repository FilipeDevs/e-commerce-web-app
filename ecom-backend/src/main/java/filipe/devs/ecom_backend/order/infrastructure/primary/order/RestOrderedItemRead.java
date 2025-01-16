package filipe.devs.ecom_backend.order.infrastructure.primary.order;


import filipe.devs.ecom_backend.order.domain.order.aggregate.OrderedProduct;
import org.jilt.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record RestOrderedItemRead(
                                  UUID publicProductId,
                                  long quantity,
                                  double price,
                                  String name) {

  public static RestOrderedItemRead from(OrderedProduct orderedProduct) {
    return RestOrderedItemReadBuilder.restOrderedItemRead()
            .publicProductId(orderedProduct.getProductPublicId().value())
            .name(orderedProduct.getProductName().value())
            .quantity(orderedProduct.getQuantity().value())
            .price(orderedProduct.getPrice().value())
            .build();
  }

  public static List<RestOrderedItemRead> from(List<OrderedProduct> orderedProducts) {
    return orderedProducts.stream().map(RestOrderedItemRead::from).toList();
  }

}
