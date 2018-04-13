package com.angelorobson.mailinglist.builders;

import com.angelorobson.mailinglist.entities.UserApp;
import com.angelorobson.mailinglist.enums.ProfileEnum;
import com.angelorobson.mailinglist.utils.PasswordUtils;

public class UserAppBuilder {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private ProfileEnum profile;

    private UserApp userApp;

    private UserAppBuilder() {}

    public static UserAppBuilder oneUserWithNameJoao() {
        UserAppBuilder builder = new UserAppBuilder();
        builder.userApp = new UserApp();
        builder.userApp.setFirstName("João");
        builder.userApp.setLastName("Silva");
        builder.userApp.setEmail("josaosilva@gmail.com");
        builder.userApp.setPassword(PasswordUtils.generateBCrypt("123"));
        builder.userApp.setProfile(ProfileEnum.ROLE_ADMIN);
        return builder;
    }

    public static UserAppBuilder oneUserWithNameManoel() {
        UserAppBuilder builder = new UserAppBuilder();
        builder.userApp = new UserApp();
        builder.userApp.setFirstName("Manoel");
        builder.userApp.setLastName("Pereira");
        builder.userApp.setEmail("manoelpereira@gmail.com");
        builder.userApp.setPassword(PasswordUtils.generateBCrypt("123"));
        builder.userApp.setProfile(ProfileEnum.ROLE_ADMIN);
        return builder;
    }

    public UserApp build() {
        return userApp;
    }
}
