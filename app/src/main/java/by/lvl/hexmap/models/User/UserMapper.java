package by.lvl.hexmap.models.User;


public class UserMapper {

    public User map(UserDTO dto) {
        User item = new User();
        item.setId(dto.getId());
        item.setName(dto.getName());
        item.setData(dto.getData());
        return item;
    }

    public UserDTO map(User user) {
        UserDTO item = new UserDTO();
        item.setId(user.getId());
        item.setName(user.getName());
        item.setData(user.getData());
        return item;
    }
}
