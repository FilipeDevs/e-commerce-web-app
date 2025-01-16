package filipe.devs.ecom_backend.order.application;


import filipe.devs.ecom_backend.order.domain.order.aggregate.DetailCartItemRequest;
import filipe.devs.ecom_backend.order.domain.order.aggregate.DetailCartRequest;
import filipe.devs.ecom_backend.order.domain.order.aggregate.DetailCartResponse;
import filipe.devs.ecom_backend.order.domain.order.service.CartReader;
import filipe.devs.ecom_backend.order.domain.order.vo.StripeSessionId;
import filipe.devs.ecom_backend.order.domain.user.aggregate.User;
import filipe.devs.ecom_backend.product.application.ProductsApplicationService;
import filipe.devs.ecom_backend.product.domain.aggregate.Product;
import filipe.devs.ecom_backend.product.domain.vo.PublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderApplicationService {

  private final ProductsApplicationService productsApplicationService;
  private final CartReader cartReader;


  public OrderApplicationService(ProductsApplicationService productsApplicationService,
                                 UsersApplicationService usersApplicationService) {
    this.productsApplicationService = productsApplicationService;
    this.cartReader = new CartReader();
  }

  @Transactional(readOnly = true)
  public DetailCartResponse getCartDetails(DetailCartRequest detailCartRequest) {
    List<PublicId> publicIds = detailCartRequest.items().stream().map(DetailCartItemRequest::productId).toList();
    List<Product> productsInformation = productsApplicationService.getProductsByPublicIdsIn(publicIds);
    return cartReader.getDetails(productsInformation);
  }


}
