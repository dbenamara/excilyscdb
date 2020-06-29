package fr.excilys.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.excilys.dao.UserInfoDao;
import fr.excilys.dto.UserInfoDto;
import fr.excilys.model.UserInfo;
import fr.excilys.model.UserInfo.UserInfoBuilder;
import fr.excilys.model.UserRoles;

@Service
public class UserInfoService implements UserDetailsService {
	private UserInfoDao userInfoDao;
	
	public UserInfoService(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}
	
	
	public void create(UserInfo userInfo) {
		userInfoDao.create(userInfo);
	}
	
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserInfo userInfo;

		userInfo = userInfoDao.getUserInfo(username).get();

		List<UserRoles> roles = userInfoDao.getUserRoles(username);
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		
			
		if (roles != null) {
			for (String role : getRoleAsString(roles)) {
				GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
				grantList.add(authority);
			}
		}

		UserDetails userDetails = (UserDetails) new User(userInfo.getUsername(), //
				userInfo.getPassword(), grantList);

		return userDetails;
	}
	
	private List<String> getRoleAsString (List<UserRoles> userRoles) {
		List<String> rolesAsString = new ArrayList<>();
		
		for (UserRoles userRole: userRoles) {
			rolesAsString.add(userRole.getUserRole());
		}
		
		return rolesAsString;
	}
	
	
	public UserInfo registerNewUserAccountUser(UserInfoDto UserInfoDto) {
		UserInfo userInfo = new UserInfo(new UserInfoBuilder()
				.setUsername(UserInfoDto.getUsername())
				.setPassword((UserInfoDto.getPassword())));
		System.out.println(" juste avant persist  :  "+userInfo);
		userInfoDao.create(userInfo);
		return userInfo;
	}
	
	
//	@Transactional
//	public List<String> getUserRoles(String userName) {
//		return userInfoDao.getUserRoles(userName);
//	}
	
	
}
