package filipe.devs.ecom_backend.order.domain.user.service;



import filipe.devs.ecom_backend.order.domain.user.aggregate.User;
import filipe.devs.ecom_backend.order.domain.user.repository.UserRepository;
import filipe.devs.ecom_backend.order.domain.user.vo.UserEmail;
import filipe.devs.ecom_backend.order.domain.user.vo.UserPublicId;

import java.util.Optional;

public class UserReader {

  private final UserRepository userRepository;

  public UserReader(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Optional<User> getByEmail(UserEmail userEmail) {
    return userRepository.getOneByEmail(userEmail);
  }

  public Optional<User> getByPublicId(UserPublicId userPublicId) {
    return userRepository.get(userPublicId);
  }
}
