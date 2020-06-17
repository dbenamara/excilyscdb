package fr.excilys.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import fr.excilys.model.Company;
import fr.excilys.model.QCompany;
import fr.excilys.model.QComputer;




/**
 * @author djamel
 *
 */
@Repository
public class CompanyDao {
	
//	private static final String CREATE_COMPANY = "INSERT INTO company name = :name";
//	private static final String GET_ALL_COMPANY = "SELECT * FROM company;";
//	private static final String SELECT_COMPANY_PAGE = "SELECT * FROM company LIMIT :limit,:offset ";
//	private static final String ERROR_ACCESS = "Impossible de se connecter à la bdd";
//	private static final String DELETE_COMPANY = "DELETE FROM company WHERE id = :id;";
//	protected static final String DELETE_COMPUTER_BY_COMPANY = "DELETE FROM computer WHERE company_id= :id;";
	
	
	@PersistenceContext
	EntityManager entityManager;
	
	public CompanyDao() {}
	
	public void create(Company company) {
		entityManager.persist(company);
	}
	
	public List<Company> readAll() {
		JPAQuery<Company> query = new JPAQuery<Company>(entityManager);
		QCompany company = QCompany.company;
		return query.from(company).fetch();
	}
	
	public List<Company> getPageCompany(int offset, int limit) {
		JPAQuery<Company> query = new JPAQuery<Company>(entityManager);
		QCompany company = QCompany.company;
		return query.from(company)
				.limit(limit)
				.offset(offset)
				.fetch();
	}
	
	
	@Transactional
	public void deleteCompany(int id) {
		QComputer computer = QComputer.computer;
		QCompany company = QCompany.company;
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		queryFactory.delete(computer).where(computer.company.id.eq(id));
		queryFactory.delete(company).where(company.id.eq(id));
	}
	
	
	
}





