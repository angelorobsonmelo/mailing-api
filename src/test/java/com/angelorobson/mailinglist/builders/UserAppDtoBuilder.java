package com.angelorobson.mailinglist.builders;

import com.angelorobson.mailinglist.dtos.UserAppDto;

public class UserAppDtoBuilder {

    private UserAppDto userAppDto;

    private UserAppDtoBuilder() {}

    public static UserAppDtoBuilder oneUserDtoWithNameJoao() {
        UserAppDtoBuilder builder = new UserAppDtoBuilder();
        builder.userAppDto = new UserAppDto();
        builder.userAppDto.setFirstName("João");
        builder.userAppDto.setLastName("Silva");
        builder.userAppDto.setEmail("josaosilva@gmail.com");
        return builder;
    }

    public static UserAppDtoBuilder oneUserDotWithNameManoel() {
        UserAppDtoBuilder builder = new UserAppDtoBuilder();
        builder.userAppDto = new UserAppDto();
        builder.userAppDto.setFirstName("Manoel");
        builder.userAppDto.setLastName("Pereira");
        builder.userAppDto.setEmail("manoelpereira@gmail.com");
        return builder;
    }

    public UserAppDto build() {
        return userAppDto;
    }
}
