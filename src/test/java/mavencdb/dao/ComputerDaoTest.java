package mavencdb.dao;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.SQLException;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.ComputerDao;
import dao.Connexion;
import model.Company;
import model.Computer;

/**
 * @author Djamel
 *
 */
public class ComputerDaoTest {
	private Connexion conn;
	Company newCompany = new Company(42,"Research In Motion");
	private Computer newComputer;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		System.setProperty("testState", "Running");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		System.setProperty("testState", "Ending");
	}


	/**
	 * Test method for {@link dao.ComputerDao#create(model.Computer)}.
	 */
	@Test
	public void testCreate() {
		this.conn = Connexion.getInstance();
		conn.connect();
		try {

			
			newComputer = new Computer.ComputerBuilder().setIdCompagny(new Company.CompanyBuilder().setId(5).build())
					.setId(51).setDiscontinued(null).setIntroduced(null).setName("toto").build();
			System.out.println(newComputer);
			assertTrue(ComputerDao.getInstance().create(newComputer));
			

		} catch(Exception e) {
			fail("Problème à la création : " + e.getMessage());
		}
		conn.close();
	}

	/**
	 * Test method for {@link dao.ComputerDao#delete(int)}.
	 */
	@Test
	public void testDelete() {
		this.conn = Connexion.getInstance();
		conn.connect();
		try {
			newComputer = ComputerDao.getInstance().find(50).get();
			assertTrue(newComputer != null);
			assertTrue(newComputer.getName().equals("Commodore PET"));
			assertTrue(ComputerDao.getInstance().delete(50));
			
		} catch(Exception e) {
			fail("Erreur au delete "+ e.getMessage());
		}
		conn.close();
	}

	/**
	 * Test method for {@link dao.ComputerDao#update(model.Computer)}.
	 */
	@Test
	public void testUpdate() {
		newComputer = ComputerDao.getInstance().find(30).get();
		newComputer.setName("poulet");
		//assertTrue(ComputerDao.getInstance().update(newComputer));
		//assertTrue(ComputerDao.getInstance().find(newComputer.getId()).get().getName().equals("poulet"));
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
		this.conn = Connexion.getInstance();
		conn.connect();
		try {
			newComputer = ComputerDao.getInstance().find(1).get();
			assertTrue(newComputer.getId() == 1);
			assertTrue(newComputer.getName().equals("MacBook Pro 15.4 inch"));
			assertTrue(newComputer.getIntroduced() == null);
			assertTrue(newComputer.getDiscontinued() == null);
			assertTrue(newComputer.getCompany().getId() == 1);
			assertTrue(newComputer.getCompany().getName().equals("Apple Inc."));
		} catch (Exception e) {
			fail("Erreur de la méthode find : " + e.getMessage());
		}
		conn.close();
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link dao.ComputerDao#getPageComputer(int, int)}.
	 */
	@Test
	public void testGetPageComputer() {
		//fail("Not yet implemented");
	}

}
