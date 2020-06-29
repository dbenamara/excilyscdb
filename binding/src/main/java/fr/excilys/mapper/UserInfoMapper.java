package fr.excilys.mapper;


import java.util.HashSet;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import fr.excilys.dto.UserInfoDto;
import fr.excilys.model.UserInfo;
import fr.excilys.model.UserRoles;

public class UserInfoMapper {
	public static UserInfo fromUserInfoDtotoUserInfo(UserInfoDto userInfoDto) {

		String passwordHash = new BCryptPasswordEncoder().encode(userInfoDto.getPassword());
		UserInfo user = new UserInfo(userInfoDto.getUsername(), passwordHash, true);
		UserRoles userRole = new UserRoles(user, userInfoDto.getRole());
		Set<UserRoles> userRoleSet = new HashSet<>();

		userRoleSet.add(userRole);
		user.setUserRoles(userRoleSet);

		return user;

	}
}
