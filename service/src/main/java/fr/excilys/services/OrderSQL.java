package fr.excilys.services;

/**
 * @author Djamel
 *
 */
public class OrderSQL {
	public  static String orderSort(String order) {
		String orderBy = "computer.name";
		if (order.equals("computer")) {
			orderBy = "computer.name";
		} else if (order.equals("introduced")) {
			orderBy = "computer.introduced";
		} else if (order.equals("discontinued")) {
			orderBy = "computer.discontinued";
		} else if(order.equals("company")) {
			orderBy = "company.name";
		}
		return orderBy;
	}
}
