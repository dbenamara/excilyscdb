package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import mapper.ComputerMapper;
import model.Company;
import model.Computer;
import Logger.Logging;
/**
 * @author djamel
 *
 */
public class ComputerDao {
	private static volatile ComputerDao instance = null;
	private Connexion conn;
	private static final String CREATE_COMPUTER = "INSERT INTO computer (id,  name, introduced, discontinued, company_id) VALUES (?,?,?,?,?);";
	private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id=?;";
	private static final String UPDATE_COMPUTER = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?;";
	private static final String GET_ALL_COMPUTER = "SELECT computer.id , computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON company_id=company.id";
	private static final String GET_COMPUTER_BY_ID = "SELECT * FROM computer LEFT JOIN company ON company_id = company.id WHERE computer.id = ?;";
	//private static final String GET_ALL_COMPUTER = "SELECT * FROM COMPUTER";
	private static final String GET_PAGE_COMPUTER = "SELECT computer.id, computer.name, computer.introduced , computer.discontinued , company_id, company.name FROM computer LEFT JOIN company ON company_id = company.id  LIMIT ?,?;";
	
	private Logging log;
	private static final String ERROR_ACCESS = "Impossible de se connecter à la bdd";
	
	private ComputerDao() {
		this.conn = Connexion.getInstance();
		this.log = new Logging();
	}
	
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
		this.conn = Connexion.getInstance();
		conn.connect();
		try {
			PreparedStatement preparedStatement = conn.getConn().prepareStatement(CREATE_COMPUTER);
			preparedStatement.setInt(1, computer.getId());
			preparedStatement.setString(2, computer.getName());
			preparedStatement.setTimestamp(3, (computer.getIntroduced()!=null)?Timestamp.valueOf(computer.getIntroduced()):null);
			preparedStatement.setTimestamp(4, (computer.getDiscontinued()!=null)?Timestamp.valueOf(computer.getDiscontinued()):null);
			preparedStatement.setInt(5, computer.getCompany().getId());
			preparedStatement.executeUpdate();
			preparedStatement.close();
			res=true;
		
		
		}catch(SQLException e) {
			log.printError(ERROR_ACCESS);
		}
		conn.close();
		return res;
	}
	
	
	public boolean delete(int id) {
		boolean res=false;
		this.conn = Connexion.getInstance();
		conn.connect();
		String query = "DELETE FROM computer WHERE id=?;";
		try {
			PreparedStatement preparedStatement = conn.getConn().prepareStatement(DELETE_COMPUTER);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		conn.close();
		return res;
	}
	   
	public boolean update(Computer computer) {
		this.conn = Connexion.getInstance();
		conn.connect();
		boolean res=false, changes=false;
		Optional<Computer> comp = find(computer.getId());
		/*int tmpId=c.getId();
		/*if(id>0) {
			c.setId(id);
			changes=true;
		}
		if(name!=null && name!="") {
			c.setName(name);
			changes=true;
		}
		if(introduced!=null) {
			c.setIntroduced(introduced);
			changes=true;
		}
		if(discontinued!=null) {
			c.setDiscontinued(discontinued);
			changes=true;
		}
		if(company_id>0) {
			c.setManufacturer(company_id);
			changes=true;
		}
		
		if(changes) {*/
			String query="UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?;";
			try {
				PreparedStatement preparedStatement = conn.getConn().prepareStatement(UPDATE_COMPUTER);
				preparedStatement.setInt(5, computer.getId());
				//preparedStatement.setInt(1, (id>0)?id:c.getId());
				preparedStatement.setString(1, (computer.getName()!=null)?computer.getName():null);
				//preparedStatement.setString(2, (name!=null && name!="")?name:c.getName());
				//preparedStatement.setString(2, (computer.getName()!=null)?computer.getName():(comp.getName()==null)?null:comp.getName());
				//(comp.getIntroduced()==null)?null:Timestamp.valueOf(comp.getIntroduced()
				preparedStatement.setTimestamp(2, (computer.getIntroduced()!=null)?Timestamp.valueOf(computer.getIntroduced()):null);
				preparedStatement.setTimestamp(3, (computer.getDiscontinued()!=null)?Timestamp.valueOf(computer.getDiscontinued()):null);
				preparedStatement.setInt(4, computer.getCompany().getId());
				//preparedStatement.setTimestamp(4, (discontinued!=null)?Timestamp.valueOf(discontinued):(c.getDiscontinued()==null)?null:Timestamp.valueOf(c.getDiscontinued()));
				//preparedStatement.setInt(4, (computer.getId()>0)?computer.getId():comp.getCompany().getId());
				preparedStatement.executeUpdate();
				res=true;
			} catch(SQLException e) {
				log.printError(ERROR_ACCESS);
			}
			conn.close();
			return res;
		
	}
		
	
	
	public List<Computer> readAll() {
		List<Computer> list = new ArrayList<Computer>();
		this.conn = Connexion.getInstance();
		conn.connect();
		Company company = new Company();
		try {
			PreparedStatement preparedStatement = conn.getConn().prepareStatement(GET_ALL_COMPUTER);
			System.out.println("TOTOTOTOTO");
			ResultSet result = preparedStatement.executeQuery(); 
		    while(result.next()) {
		    	Computer computer = ComputerMapper.getInstance().getComputer(result);
		    	list.add(computer);
		    }        
		} catch (SQLException e) {
			log.printError(ERROR_ACCESS);
		    }
		conn.close();
		return list;
	}
	
	public Optional<Computer> find(int id) {
		Computer computer = new Computer();
		Company company = new Company();
		String query = "SELECT * FROM computer WHERE id=?";
		this.conn = Connexion.getInstance();
		conn.connect();
		try {
			PreparedStatement preparedStatement = conn.getConn().prepareStatement(GET_COMPUTER_BY_ID);
			preparedStatement.setInt(1, id);
			ResultSet result = preparedStatement.executeQuery();
			computer = ComputerMapper.getInstance().getComputer(result);
				
		} catch (SQLException e){
			log.printError(ERROR_ACCESS);
		}
		conn.close();
		return Optional.ofNullable(computer);
		
	}

	public  Optional<List<Computer>> getPageComputer(int offset, int number) {

		List<Computer> computerlist = new ArrayList<Computer>();
		this.conn = Connexion.getInstance();
		conn.connect();
		try  {
			PreparedStatement statementSelecPage = conn.getConn().prepareStatement(GET_PAGE_COMPUTER);
			statementSelecPage.setInt(1, offset);
			statementSelecPage.setInt(2, number);
			ResultSet resListecomputer = statementSelecPage.executeQuery();
			while (resListecomputer.next()) {
				Computer computer = ComputerMapper.getInstance().getComputer(resListecomputer);

				computerlist.add(computer);
			}

			statementSelecPage.close();
			resListecomputer.close();

		} catch (SQLException e) {
			log.printError(ERROR_ACCESS);
		}
		return Optional.ofNullable(computerlist);
}

	
}
