package by.lvl.hexmap.models.User.repo;

import by.lvl.hexmap.models.User.User;
import rx.Observable;


public interface IUserRepository {
    Observable<Void> signup(User user);
}
