package filipe.devs.ecom_backend.order.application;


import filipe.devs.ecom_backend.order.domain.user.aggregate.User;
import filipe.devs.ecom_backend.order.domain.user.repository.UserRepository;
import filipe.devs.ecom_backend.order.domain.user.service.UserReader;
import filipe.devs.ecom_backend.order.domain.user.service.UserSynchronizer;
import filipe.devs.ecom_backend.order.domain.user.vo.UserAddressToUpdate;
import filipe.devs.ecom_backend.order.domain.user.vo.UserEmail;
import filipe.devs.ecom_backend.order.infrastructure.secondary.service.kinde.KindeService;
import filipe.devs.ecom_backend.shared.authentication.application.AuthenticatedUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsersApplicationService {

  private final UserSynchronizer userSynchronizer;
  private final UserReader userReader;

  public UsersApplicationService(UserRepository userRepository, KindeService kindeService) {
    this.userSynchronizer = new UserSynchronizer(userRepository, kindeService);
    this.userReader = new UserReader(userRepository);
  }

  @Transactional
  public User getAuthenticatedUserWithSync(Jwt jwtToken, boolean forceResync) {
    userSynchronizer.syncWithIdp(jwtToken, forceResync);
    return userReader.getByEmail(new UserEmail(AuthenticatedUser.username().get()))
      .orElseThrow();
  }

  @Transactional(readOnly = true)
  public User getAuthenticatedUser() {
    return userReader.getByEmail(new UserEmail(AuthenticatedUser.username().get()))
      .orElseThrow();
  }

  @Transactional
  public void updateAddress(UserAddressToUpdate userAddressToUpdate) {
    userSynchronizer.updateAddress(userAddressToUpdate);
  }

}
