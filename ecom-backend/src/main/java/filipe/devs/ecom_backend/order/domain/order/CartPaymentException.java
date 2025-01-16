package filipe.devs.ecom_backend.order.domain.order;

public class CartPaymentException extends RuntimeException {
  public CartPaymentException(String message) {
    super(message);
  }
}
