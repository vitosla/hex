package by.lvl.hexmap.models.User.repo;

import by.lvl.hexmap.api.retrofit.RetrofitService;
import by.lvl.hexmap.models.User.User;
import by.lvl.hexmap.models.User.UserDTO;
import by.lvl.hexmap.models.User.UserMapper;
import rx.Observable;


public class RemoteUserRepository implements IUserRepository {
    @Override
    public Observable<Void> signup(User user) {
        UserDTO value = new UserMapper().map(user);
        return RetrofitService.getInstance().signup(value);
    }
}
