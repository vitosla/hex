package by.lvl.hexmap.api.events;

import by.lvl.hexmap.models.User.User;


public class PostSignupEvent extends SuccessfulEvent<User> {

    public PostSignupEvent(User data) {
        super(data);
    }
}