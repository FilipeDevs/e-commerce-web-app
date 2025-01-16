package filipe.devs.ecom_backend.order.domain.order.service;



import filipe.devs.ecom_backend.order.domain.order.aggregate.DetailCartItemRequest;
import filipe.devs.ecom_backend.order.domain.order.aggregate.Order;
import filipe.devs.ecom_backend.order.domain.order.aggregate.OrderedProduct;
import filipe.devs.ecom_backend.order.domain.order.repository.OrderRepository;
import filipe.devs.ecom_backend.order.domain.order.vo.StripeSessionId;
import filipe.devs.ecom_backend.order.domain.user.aggregate.User;
import filipe.devs.ecom_backend.order.infrastructure.secondary.service.stripe.StripeService;
import filipe.devs.ecom_backend.product.domain.aggregate.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderCreator {

  private final OrderRepository orderRepository;
  private final StripeService stripeService;

  public OrderCreator(OrderRepository orderRepository, StripeService stripeService) {
    this.orderRepository = orderRepository;
    this.stripeService = stripeService;
  }

  public StripeSessionId create(List<Product> productsInformations,
                                List<DetailCartItemRequest> items,
                                User connectedUser) {

    StripeSessionId stripeSessionId = this.stripeService.createPayment(connectedUser,
            productsInformations, items);

    List<OrderedProduct> orderedProducts = new ArrayList<>();

    for(DetailCartItemRequest itemRequest: items) {
      Product productDetails = productsInformations.stream()
              .filter(product -> product.getPublicId().value().equals(itemRequest.productId().value()))
              .findFirst().orElseThrow();

      OrderedProduct orderedProduct = OrderedProduct.create(itemRequest.quantity(), productDetails);
      orderedProducts.add(orderedProduct);
    }

    Order order = Order.create(connectedUser, orderedProducts, stripeSessionId);
    orderRepository.save(order);

    return stripeSessionId;
  }
}

