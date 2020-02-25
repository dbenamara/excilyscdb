//package mavencdb.mapper;
//
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.time.format.DateTimeParseException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import dao.ComputerDao;
//import dao.Connexion;
//import mapper.ComputerMapper;
//import model.Computer;
//
//
//
///**
// * @author Djamel
// *
// */
//public class ComputerMapperTest {
//	private Connexion conn;
//	private static final String GET_PAGE_COMPUTER = "SELECT computer.id, computer.name, computer.introduced , computer.discontinued , company_id, company.name FROM computer LEFT JOIN company ON company_id = company.id  LIMIT ?,?;";
//	ComputerDao cpo;
//	private ComputerMapper instance;
//	private PreparedStatement preparedStatement;
//	private ResultSet result;
//	
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@Before
//	public void setUp() throws Exception {
//		//instance = ComputerMapper.getInstance();
//		this.conn = Connexion.getInstance();
//		conn.connect();
//		ComputerDao cpo = ComputerDao.getInstance();
//		instance = ComputerMapper.getInstance();
//	}
//
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@After
//	public void tearDown() throws Exception {
//		conn.close();
//		preparedStatement.close();
//		result.close();
//	}
//	/**
//	 * Test method for {@link mapper.ComputerMapper#getComputer(java.sql.ResultSet)}.
//	 */
//	@Test
//	public void testGetComputer() {
//		List<Computer> cpl = new ArrayList<Computer>();
//		//cpo.getPageComputer(0,65).get();
//		//fail("Not yet implemented");
//		
//		
//		
//		
//	/*	try  {
//			PreparedStatement statementSelecPage = conn.getConn().prepareStatement(GET_PAGE_COMPUTER);
//			statementSelecPage.setInt(1, offset);
//			statementSelecPage.setInt(2, number);
//		} catch (DateTimeParseException e) {
//			fail("le teste ne devait pas echouer");
//		}*/
//	}
//
//}
