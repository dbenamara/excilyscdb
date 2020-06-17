package fr.excilys.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import fr.excilys.mapper.ComputerMapper;
import fr.excilys.model.Computer;
import fr.excilys.model.QComputer;

/**
 * @author Djamel
 *
 */
@Repository
public class ComputerDao {

	private ComputerMapper computerMapper;
//	
//	
//	private static final String CREATE_COMPUTER = "INSERT INTO computer (name, introduced, discontinued, company_id)"
//			+ " VALUES (:computerName, :introduced, :discontinued, :companyId);";
//	private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id = :id ;";
//	private static final String DELETE_COMPUTER_SELECTED = "DELETE FROM computer WHERE id IN (:id);";
//	private static final String UPDATE_COMPUTER = "UPDATE computer SET name= :name, introduced= :introduced, discontinued= :discontinued, company_id= :company_id WHERE id= :id;";
//	private static final String GET_ALL_COMPUTER = "SELECT computer.id , computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON company_id=company.id;";
//	private static final String GET_PAGE_COMPUTER = "SELECT computer.id, computer.name, computer.introduced , computer.discontinued , company_id, company.name FROM computer LEFT JOIN company ON company_id = company.id ORDER BY :order LIMIT :offset, :number;";
//	private static final String GET_COMPUTER_BY_NAME = "SELECT computer.id, computer.name, computer.introduced , computer.discontinued , company_id, company.name FROM computer LEFT JOIN company ON company_id = company.id WHERE computer.name LIKE :like ORDER BY :order LIMIT :offset, :number;";    
//	private static final String GET_COMPUTER_BY_ID = "SELECT * FROM computer LEFT JOIN company ON company_id = company.id WHERE computer.id = :computer.id;";
//	protected static final String DELETE_COMPUTER_FROM_COMPANY = "DELETE FROM computer WHERE company_id= :id;";


	@PersistenceContext
	EntityManager entityManager;
	
	public ComputerDao(ComputerMapper computerMapper) {
		this.computerMapper = computerMapper;
	}
	
	public void create(Computer computer) {
		entityManager.persist(computer);
	}
	
	public List<Computer> readAll() {
		JPAQuery<Computer> query = new JPAQuery<Computer>(entityManager);
		QComputer computer = QComputer.computer;
		return query.from(computer).fetch();
	}
	
	public void delete(int id) {
		QComputer computer = QComputer.computer;
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		queryFactory.delete(computer).where(computer.id.eq(id)).execute();
	}
	
	public void deleteAllComputerSelected(List<Integer> idList) {
		QComputer computer = QComputer.computer;
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		queryFactory.delete(computer).where(computer.id.in(idList)).execute();
	}
	
	public Optional<Computer> find(int id) {
		QComputer computer = QComputer.computer;
		JPAQuery<Computer> query = new JPAQuery<Computer>(entityManager);
		return Optional.ofNullable(query.from(computer)
				.where(computer.id.eq(id))
				.fetchFirst());
	}
	
	public void update(Computer comp) {
		QComputer computer = QComputer.computer;
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		queryFactory.update(computer).where(computer.id.eq(comp.getId()))
		    .set(computer.name, comp.getName())
		    .set(computer.introduced, comp.getIntroduced())
		    .set(computer.discontinued, comp.getDiscontinued())
		    .set(computer.company, comp.getCompany())
		    .execute();
	}
	
	public  List<Computer> getPageComputer(int offset, int limit, String orderBy) {
		JPAQuery<Computer> query = new JPAQuery<Computer>(entityManager);
		QComputer computer = QComputer.computer;
		return query.from(computer)
				.limit(limit)
				.offset(offset)
				.orderBy(computer.name.asc())
				.fetch();
	}
	
	public List<Computer> findName(String like,int offset,int limit, String orderBy) {
		JPAQuery<Computer> query = new JPAQuery<Computer>(entityManager);
		QComputer computer = QComputer.computer;
		return query.from(computer)
				.where(computer.name.like("%"+like+"%"))
				.limit(limit)
				.offset(offset)
				.orderBy(computer.name.asc())
				.fetch();
	}
	

	public void deleteComputerFromCompany(int id) {
		QComputer computer = QComputer.computer;
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		queryFactory.delete(computer).where(computer.id.eq(id)).execute();
	}

}

