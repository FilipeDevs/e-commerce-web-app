package filipe.devs.ecom_backend.order.domain.user.repository;



import filipe.devs.ecom_backend.order.domain.user.aggregate.User;
import filipe.devs.ecom_backend.order.domain.user.vo.UserAddressToUpdate;
import filipe.devs.ecom_backend.order.domain.user.vo.UserEmail;
import filipe.devs.ecom_backend.order.domain.user.vo.UserPublicId;

import java.util.Optional;

public interface UserRepository {

  void save(User user);

  Optional<User> get(UserPublicId userPublicId);

  Optional<User> getOneByEmail(UserEmail userEmail);

  void updateAddress(UserPublicId userPublicId, UserAddressToUpdate userAddress);

}
