package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import exceptions.Logging;
import mapper.ComputerMapper;
import model.Company;
import model.Computer;
/**
 * @author djamel
 *
 */
public class ComputerDao {
	private static volatile ComputerDao instance = null;
	private Connection conn;
	private static final String CREATE_COMPUTER = "INSERT INTO computer (  name, introduced, discontinued, company_id) VALUES (?,?,?,?);";
	private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id=?;";
	private static final String UPDATE_COMPUTER = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?;";
	private static final String GET_ALL_COMPUTER = "SELECT computer.id , computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON company_id=company.id";
	private static final String GET_COMPUTER_BY_ID = "SELECT * FROM computer LEFT JOIN company ON company_id = company.id WHERE computer.id = ?;";
	private static final String GET_PAGE_COMPUTER = "SELECT computer.id, computer.name, computer.introduced , computer.discontinued , company_id, company.name FROM computer LEFT JOIN company ON company_id = company.id  LIMIT ?,?;";
	
	private Logging log;
	private static final String ERROR_ACCESS = "Impossible de se connecter à la bdd";
	
	private ComputerDao() {}
	
	public final static ComputerDao getInstance() {

		if (ComputerDao.instance == null) {

			synchronized (ComputerDao.class) {
				if (ComputerDao.instance == null) {
					ComputerDao.instance = new ComputerDao();
				}
			}
		}

		return ComputerDao.instance;
	}

	
	public boolean create(Computer computer) {
		boolean res=false;
		
		try {
			this.conn = Connexion.getInstance().getConn();
			PreparedStatement preparedStatement = conn.prepareStatement(CREATE_COMPUTER);
			preparedStatement.setString(1, computer.getName());
			preparedStatement.setTimestamp(2, (computer.getIntroduced()!=null)?Timestamp.valueOf(computer.getIntroduced()):null);
			preparedStatement.setTimestamp(3, (computer.getDiscontinued()!=null)?Timestamp.valueOf(computer.getDiscontinued()):null);
			preparedStatement.setInt(4, computer.getCompany().getId());
			preparedStatement.executeUpdate();
			preparedStatement.close();
			res=true;
			conn.close();
		}catch(SQLException e) {
			Logging.printError(ERROR_ACCESS+e.getMessage());
		}
		return res;
	}
	
	
	public boolean delete(int id) {
		boolean res=false;
		try {
			this.conn = Connexion.getInstance().getConn();
			PreparedStatement preparedStatement = conn.prepareStatement(DELETE_COMPUTER);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			res=true;
			conn.close();
		}catch(SQLException e) {
			Logging.printError(ERROR_ACCESS+e.getMessage());
		}
		return res;
	}
	   
	public Computer update(Computer computer) {
		
			try {
				this.conn = Connexion.getInstance().getConn();
				PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_COMPUTER);
				preparedStatement.setInt(5, computer.getId());
				preparedStatement.setString(1, (computer.getName()!=null)?computer.getName():null);
				preparedStatement.setTimestamp(2, (computer.getIntroduced()!=null)?Timestamp.valueOf(computer.getIntroduced()):null);
				preparedStatement.setTimestamp(3, (computer.getDiscontinued()!=null)?Timestamp.valueOf(computer.getDiscontinued()):null);
				preparedStatement.setInt(4, computer.getCompany().getId());
				preparedStatement.executeUpdate();
				preparedStatement.close();
				
				conn.close();
			} catch(SQLException e) {
				Logging.printError("DANS UPDATE" + e.getMessage());
			}
			return find(computer.getId()).get();
		
	}
		
	
	
	public List<Computer> readAll() {
		List<Computer> list = new ArrayList<Computer>();
		
		Company company = new Company();
		try {
			this.conn = Connexion.getInstance().getConn();
			PreparedStatement preparedStatement = conn.prepareStatement(GET_ALL_COMPUTER);
			
			ResultSet result = preparedStatement.executeQuery(); 
		    while(result.next()) {
		    	Computer computer = ComputerMapper.getInstance().getComputer(result).get();
		    	list.add(computer);
		    }        
		} catch (SQLException e) {
			Logging.printError(ERROR_ACCESS+e.getMessage());
		}
		return list;
	}
	
	public Optional<Computer> find(int id) {
		Computer computer = new Computer();
		Company company = new Company();
		
		try {
			this.conn = Connexion.getInstance().getConn();
			PreparedStatement preparedStatement = conn.prepareStatement(GET_COMPUTER_BY_ID);
			preparedStatement.setInt(1, id);
			ResultSet result = preparedStatement.executeQuery();
			result.next();
			computer = ComputerMapper.getInstance().getComputer(result).get();
				
		} catch (SQLException e){
			Logging.printError(ERROR_ACCESS + e.getMessage());
		}
		return Optional.ofNullable(computer);
		
	}

	public  List<Computer> getPageComputer(int offset, int number) {

		List<Computer> computerlist = new ArrayList<Computer>();

		try  {
			this.conn = Connexion.getInstance().getConn();
			PreparedStatement statementSelecPage = conn.prepareStatement(GET_PAGE_COMPUTER);
			statementSelecPage.setInt(1, offset);
			statementSelecPage.setInt(2, number);
			ResultSet resListecomputer = statementSelecPage.executeQuery();
			while (resListecomputer.next()) {
				Computer computer = ComputerMapper.getInstance().getComputer(resListecomputer).get();

				computerlist.add(computer);
			}
			
			statementSelecPage.close();
			resListecomputer.close();

		} catch (SQLException e) {
			Logging.printError(ERROR_ACCESS+e.getMessage());
		}
		return computerlist;
}

	
}
