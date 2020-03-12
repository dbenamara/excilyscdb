package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import exceptions.Logging;
import mapper.ComputerMapper;
import model.Company;
import model.Computer;
/**
 * @author djamel
 *
 */
@Repository
public class ComputerDao {
	private static volatile ComputerDao instance = null;
	private Connexion conn;
	private static final String CREATE_COMPUTER = "INSERT INTO computer (  name, introduced, discontinued, company_id) VALUES (?,?,?,?);";
	private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id=?;";
	private static final String DELETE_COMPUTER_SELECTED = "DELETE FROM computer WHERE id=?;";
	private static final String UPDATE_COMPUTER = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?;";
	private static final String GET_ALL_COMPUTER = "SELECT computer.id , computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON company_id=company.id;";
	private static final String GET_COMPUTER_BY_ID = "SELECT * FROM computer LEFT JOIN company ON company_id = company.id WHERE computer.id = ?;";
	private static final String GET_COMPUTER_BY_NAME = "SELECT computer.id, computer.name, computer.introduced , computer.discontinued , company_id, company.name FROM computer LEFT JOIN company ON company_id = company.id WHERE computer.name LIKE ? ORDER BY ";
	private static final String GET_PAGE_COMPUTER = "SELECT computer.id, computer.name, computer.introduced , computer.discontinued , company_id, company.name FROM computer LEFT JOIN company ON company_id = company.id ORDER BY ";
	private static final String FIND_PAGE = " LIMIT ?, ?;";
	private static final String DELETE_ALL_COMPUTER_SELECTED = "DELETE FROM computer WHERE id IN(";
	protected static final String DELETE_COMPUTER_FROM_COMPANY = "DELETE FROM computer WHERE company_id=?;";
	
	
	private Logging log;
	private static final String ERROR_ACCESS = "Impossible de se connecter à la bdd";
	
	
	public ComputerDao(Connexion conn) {
		this.conn = conn;
	}
	

	
	public boolean create(Computer computer) {
		boolean res=false;
		
		try(Connection connect = conn.getConn()) {
			PreparedStatement preparedStatement = connect.prepareStatement(CREATE_COMPUTER);
			preparedStatement.setString(1, computer.getName());
			preparedStatement.setTimestamp(2, (computer.getIntroduced()!=null)?Timestamp.valueOf(computer.getIntroduced()):null);
			preparedStatement.setTimestamp(3, (computer.getDiscontinued()!=null)?Timestamp.valueOf(computer.getDiscontinued()):null);
			preparedStatement.setInt(4, computer.getCompany().getId());
			preparedStatement.executeUpdate();
			preparedStatement.close();
			res=true;
		}catch(SQLException e) {
			Logging.printError(ERROR_ACCESS+e.getMessage());
		}
		return res;
	}
	
	
	public boolean delete(int id) {
		boolean res=false;
		try(Connection connect = conn.getConn()) {
			PreparedStatement preparedStatement = connect.prepareStatement(DELETE_COMPUTER);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			res=true;
		}catch(SQLException e) {
			Logging.printError(ERROR_ACCESS+e.getMessage());
		}
		return res;
	}
	
	public boolean deleteAllComputerSelected(List<Integer> idList) {
		boolean res =false;
		try(Connection connect = conn.getConn()) {
			String interro = "";
			for(int i=0;i<idList.size()-1;i++) {
				interro += "?,";
			}
			interro += "?);";
			PreparedStatement preparedStatement = connect.prepareStatement(DELETE_ALL_COMPUTER_SELECTED+interro);
			for(int i=0;i<idList.size();i++) {
				preparedStatement.setInt(i+1, idList.get(i));
				
			}
			
			preparedStatement.executeUpdate();
			preparedStatement.close();
			res=true;
			
		}catch(SQLException sql) {
			res=false;
			Logging.printError(ERROR_ACCESS+"in deleteAllComputerSelected "+sql.getMessage());
		}
		return res;
	}
	   
	public Computer update(Computer computer) {
		
			try(Connection connect = conn.getConn()) {
				PreparedStatement preparedStatement = connect.prepareStatement(UPDATE_COMPUTER);
				preparedStatement.setInt(5, computer.getId());
				preparedStatement.setString(1, (computer.getName()!=null)?computer.getName():null);
				preparedStatement.setTimestamp(2, (computer.getIntroduced()!=null)?Timestamp.valueOf(computer.getIntroduced()):null);
				preparedStatement.setTimestamp(3, (computer.getDiscontinued()!=null)?Timestamp.valueOf(computer.getDiscontinued()):null);
				preparedStatement.setInt(4, computer.getCompany().getId());
				preparedStatement.executeUpdate();
				preparedStatement.close();
				
			} catch(SQLException sql) {
				Logging.printError("DANS UPDATE" + sql.getMessage());
			}
			return find(computer.getId()).get();
		
	}
		
	
	
	public List<Computer> readAll() {
		List<Computer> list = new ArrayList<Computer>();
		
		Company company = new Company();
		try(Connection connect = conn.getConn()) {
			
			PreparedStatement preparedStatement = connect.prepareStatement(GET_ALL_COMPUTER);
			
			ResultSet result = preparedStatement.executeQuery(); 
		    while(result.next()) {
		    	Computer computer = new ComputerMapper().getComputer(result).get();
		    	list.add(computer);
		    }  
		} catch (SQLException sql) {
			Logging.printError(ERROR_ACCESS+sql.getMessage());
		}
		return list;
	}
	
	public Optional<Computer> find(int id) {
		Computer computer = new Computer();
		Company company = new Company();
		
		try(Connection connect = conn.getConn()) {
			//this.conn = Connexion.getInstance().getConn();
			PreparedStatement preparedStatement = connect.prepareStatement(GET_COMPUTER_BY_ID);
			preparedStatement.setInt(1, id);
			ResultSet result = preparedStatement.executeQuery();
			result.next();
			computer = new ComputerMapper().getComputer(result).get();
			
		} catch (SQLException sql){
			Logging.printError(ERROR_ACCESS + sql.getMessage());
		}
		return Optional.ofNullable(computer);
		
	}

	public  List<Computer> getPageComputer(int offset, int number, String orderBy) {

		List<Computer> computerlist = new ArrayList<Computer>();

		try (Connection connect = conn.getConn()) {
			PreparedStatement statementSelecPage = connect.prepareStatement(GET_PAGE_COMPUTER+orderBy+FIND_PAGE);
			statementSelecPage.setInt(1, offset);
			statementSelecPage.setInt(2, number);
			ResultSet resListecomputer = statementSelecPage.executeQuery();
			while (resListecomputer.next()) {
				Computer computer = new ComputerMapper().getComputer(resListecomputer).get();

				computerlist.add(computer);
			}
			
			statementSelecPage.close();
			resListecomputer.close();
		} catch (SQLException sql) {
			Logging.printError(ERROR_ACCESS+sql.getMessage());
		}
		return computerlist;
	}
	
	public List<Computer> findName(String name, int offset,int number, String orderBy) {
		Computer computer;
		ResultSet result;
		List<Computer> computerList = new ArrayList<Computer>();

		try (Connection connect = conn.getConn()){
			//this.conn = Connexion.getInstance().getConn();
			PreparedStatement stmtFindName = connect.prepareStatement(GET_COMPUTER_BY_NAME+ orderBy+FIND_PAGE);
			stmtFindName.setString(1, "%"+name+"%");
			stmtFindName.setInt(2, offset);
			stmtFindName.setInt(3, number);
			result = stmtFindName.executeQuery();
			while(result.next()) {
				computer = new ComputerMapper().getComputer(result).get();
				computerList.add(computer);
			}
			stmtFindName.close();
			result.close();
		} catch (SQLException sql) {
			Logging.printError(ERROR_ACCESS+sql.getMessage());
		}
		return computerList;
	}
	
	

	
}

