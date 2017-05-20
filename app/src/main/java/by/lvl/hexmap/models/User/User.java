package by.lvl.hexmap.models.User;

import by.lvl.hexmap.tools.PhoneHelper;


public class User {

    private String id;
    private String name;
    private String data;


    public User() { }

    public User(String name) {
        this.id = PhoneHelper.getAndroidId();
        this.name = name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
