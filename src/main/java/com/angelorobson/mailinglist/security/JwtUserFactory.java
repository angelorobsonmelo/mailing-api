package com.angelorobson.mailinglist.security;

import com.angelorobson.mailinglist.entities.UserApp;
import com.angelorobson.mailinglist.enums.ProfileEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class JwtUserFactory {

	private JwtUserFactory() {
	}

	/**
	 * Converts and generates a Jwt UserApp based on a userApp's data.
	 *
	 * @param userApp
	 * @return JwtUser
	 */
	public static JwtUser create(UserApp userApp) {
		return new JwtUser(userApp.getId(), userApp.getEmail(), userApp.getPassword(),
				mapToGrantedAuthorities(userApp.getProfile()));
	}

	/**
	 * Converts the user profile to the format used by Spring Security.
	 *
	 * @param profileEnum
	 * @return List<GrantedAuthority>
	 */
	private static List<GrantedAuthority> mapToGrantedAuthorities(ProfileEnum profileEnum) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(profileEnum.toString()));
		return authorities;
	}

}
