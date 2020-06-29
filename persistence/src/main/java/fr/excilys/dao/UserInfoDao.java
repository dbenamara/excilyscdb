package fr.excilys.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import fr.excilys.model.QUserInfo;
import fr.excilys.model.QUserRoles;
import fr.excilys.model.UserInfo;
import fr.excilys.model.UserRoles;

@Repository
public class UserInfoDao {


	@PersistenceContext
	EntityManager entityManager;

	public UserInfoDao() {}
	
	public void create(UserInfo userInfo) {
		System.out.println("User CREATE");
		entityManager.persist(userInfo);
	}
	
	public Optional<UserInfo> getUserInfo(String username) {
		System.out.println("User GETINFO");
		QUserInfo userInfo = QUserInfo.userInfo;
		JPAQuery<UserInfo> query = new JPAQuery<UserInfo>(entityManager);
		return Optional.ofNullable(query.from(userInfo)
				.where(userInfo.username.eq(username))
				.fetchFirst());
	}
	
	public void delete(int id) {
		QUserInfo userInfo = QUserInfo.userInfo;
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		queryFactory.delete(userInfo).where(userInfo.id.eq(id)).execute();
	}
	
	
	public void update(UserInfo userInf) {
		QUserInfo userInfo = QUserInfo.userInfo;
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		queryFactory.update(userInfo).where(userInfo.id.eq(userInf.getId()))
			.set(userInfo.id, userInf.getId())
			.set(userInfo.username, userInf.getUsername())
		    .set(userInfo.password, userInf.getPassword())
		    .set(userInfo.userRoles, userInf.getUserRoles())
		    .execute();
	}
	
	public List<UserRoles> getUserRoles(String userName) {
        QUserInfo userInfo = QUserInfo.userInfo;
        QUserRoles userRoles = QUserRoles.userRoles;
		JPAQuery<UserInfo> query = new JPAQuery<UserInfo>(entityManager);
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		return queryFactory.select(userRoles.userRoles).where(userRoles.user.username.eq(userName)).fetch();
        
    }
}
