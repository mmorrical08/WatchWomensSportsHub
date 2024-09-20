package learn.wwsh.data;

import learn.wwsh.models.AppUser;
import org.springframework.transaction.annotation.Transactional;

public interface AppUserRepository {
    @Transactional
    AppUser findByUsername(String email);

    @Transactional
    AppUser create(AppUser user);

}
