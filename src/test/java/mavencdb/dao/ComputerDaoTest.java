package mavencdb.dao;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dao.ComputerDao;
import dao.Connexion;
import mapper.ComputerMapper;
import model.Company;
import model.Computer;
import springconfig.AppConfig;

/**
 * @author Djamel
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class ComputerDaoTest {
	private Connexion conn;
	Company newCompany = new Company(42,"Research In Motion");
	private Computer newComputer;
	private ComputerDao computerDao;
	private ComputerMapper computerMapper;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	@Autowired
	public void setComputerDao(ComputerDao computerDao, ComputerMapper computerMapper) {
		this.computerDao = computerDao;
		this.computerMapper = computerMapper; 
	}


	/**
	 * Test method for {@link dao.ComputerDao#create(model.Computer)}.
	 */
	@Test
	public void testCreate() {
		try {

			
			newComputer = new Computer.ComputerBuilder().setIdCompagny(new Company.CompanyBuilder().setId(5).build())
					.setId(51).setDiscontinued(null).setIntroduced(null).setName("toto").build();
			System.out.println(newComputer);
			assertTrue(computerDao.create(newComputer));
			

		} catch(AssertionError e) {
			fail("Problème à la création : " + e.getMessage());
		}
	}

	/**
	 * Test method for {@link dao.ComputerDao#delete(int)}.
	 */
	@Test
	public void testDelete() {
		try {
			newComputer = computerDao.find(50).get();
			assertTrue(newComputer != null);
			assertTrue(newComputer.getName().equals("Commodore PET"));
			assertTrue(computerDao.delete(50));
			
		} catch(AssertionError e) {
			fail("Erreur au delete "+ e.getMessage());
		}
	}

	/**
	 * Test method for {@link dao.ComputerDao#update(model.Computer)}.
	 */
	@Test
	public void testUpdate() {
		newComputer = computerDao.find(30).get();
		newComputer.setName("poulet");
	}

	/**
	 * Test method for {@link dao.ComputerDao#readAll()}.
	 */
	@Test
	public void testReadAll() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link dao.ComputerDao#find(int)}.
	 */
	@Test
	public void testFind() {
		try {
			newComputer = computerDao.find(1).get();
			assertTrue(newComputer.getId() == 1);
			assertTrue(newComputer.getName().equals("MacBook Pro 15.4 inch"));
			assertTrue(newComputer.getIntroduced() == null);
			assertTrue(newComputer.getDiscontinued() == null);
			assertTrue(newComputer.getCompany().getId() == 1);
			assertTrue(newComputer.getCompany().getName().equals("Apple Inc."));
		} catch (AssertionError e) {
			fail("Erreur de la méthode find : " + e.getMessage());
		}
	}

	/**
	 * Test method for {@link dao.ComputerDao#getPageComputer(int, int)}.
	 */
	@Test
	public void testGetPageComputer() {
		//fail("Not yet implemented");
	}

}
