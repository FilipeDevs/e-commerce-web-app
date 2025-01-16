package filipe.devs.ecom_backend.order.infrastructure.primary.order;

import filipe.devs.ecom_backend.order.application.OrderApplicationService;
import filipe.devs.ecom_backend.order.domain.order.aggregate.DetailCartItemRequest;
import filipe.devs.ecom_backend.order.domain.order.aggregate.DetailCartRequest;
import filipe.devs.ecom_backend.order.domain.order.aggregate.DetailCartRequestBuilder;
import filipe.devs.ecom_backend.order.domain.order.aggregate.DetailCartResponse;
import filipe.devs.ecom_backend.product.domain.vo.PublicId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;



@RestController
@RequestMapping("/api/orders")
public class OrderResource {

  private final OrderApplicationService orderApplicationService;


  public OrderResource(OrderApplicationService orderApplicationService) {
    this.orderApplicationService = orderApplicationService;
  }

  @GetMapping("/get-cart-details")
  public ResponseEntity<RestDetailCartResponse> getDetails(@RequestParam List<UUID> productIds) {
    List<DetailCartItemRequest> cartItemRequests = productIds.stream()
      .map(uuid -> new DetailCartItemRequest(new PublicId(uuid), 1))
      .toList();

    DetailCartRequest detailCartRequest = DetailCartRequestBuilder.detailCartRequest().items(cartItemRequests).build();
    DetailCartResponse cartDetails = orderApplicationService.getCartDetails(detailCartRequest);
    return ResponseEntity.ok(RestDetailCartResponse.from(cartDetails));
  }


}
