package mavencdb.dao;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dao.ComputerDao;
//import dao.Connexion;
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
	//private Connexion conn;
	Company newCompany = new Company(42,"Research In Motion");
	private Computer newComputer;
	@Autowired
	private ComputerDao computerDao;
	@Autowired
	private ComputerMapper computerMapper;
	
	/**
	 * @throws java.lang.Exception
	 */
	


	/**
	 * Test method for {@link dao.ComputerDao#create(model.Computer)}.
	 */
	@Test
	public void testCreate() {
		try {
			Company company = new Company.CompanyBuilder().setId(1).build();
			
			newComputer = new Computer.ComputerBuilder().setCompany(company).setName("testComputerencache")
					.setIntroduced(LocalDateTime.now().minusYears(3)).setDiscontinued(LocalDateTime.now().minusYears(1)).build();
			computerDao.create(newComputer);
			

		} catch(AssertionError e) {
			fail("Problème à la création : " + e.getMessage());
		} catch (Exception e) {
			fail("Problème à la création : " + e.getMessage());
		}
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

}
