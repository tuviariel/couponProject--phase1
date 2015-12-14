package facade;
import java.util.ArrayList;
import java.util.HashSet;

import base.*;
import exceptionForCouponSystem.CouponException;
import DAO.CompanyDAO;
import DAO.CouponDAO;
import DAO.CustomerDAO;
import DBDAO.*;

/**
 * the class responsible for the administrator of the coupon system facade.
 * implements client interface
 */
public class AdminFacade implements Client {
	//the permanent Administers login info: 
	private static String USERNAME="admin";
	private static String PASSWORD="1234";
	//Achieving access to the database through the DAO classes: 
	private CouponDAO couponDB; 
	private CompanyDAO companyDB;
	private CustomerDAO customerDB; 
	/**
	 * Constructor (enables to throw the exception on to the main)
	 * @throws CouponException 
	 */
	public AdminFacade() throws CouponException{
		couponDB = new CouponDBDAO();
		companyDB = new CompanyDBDAO();
		customerDB = new CustomerDBDAO();
	}
	/**
	 * This function creates a company in the database,
	 * checking there is no duplication of the company's name:
	 * @param customer
	 * @throws CouponException
	 */
	public void createCompany(Company company) throws CouponException{
		if(!companyDB.isCompany(company.getName()))
			companyDB.createCompany(company);
		else 
			throw new CouponException("There is already company with this name");
	}
	/**
	 * This function creates a customer in the database,
	 * checking there is no duplication of the customer's name:
	 * @param customer
	 * @throws CouponException
	 */
	public void createCustomer(Customer customer) throws CouponException{
		if(!customerDB.isCustomer(customer.getCustName()))
			customerDB.createCustomer(customer);
		else 
			throw new CouponException("There is already customer with this name");
	}
	/**
	 * This function removes company from the database. First it removes the 
	 * coupons sold by the company than it removes all of company's coupons.
	 * Finally it removes the company from the database
	 * @param company
	 * @throws Exception
	 */
	public void removeCompany(Company company) throws Exception{
		companyDB.getCompanyById(company.getId());
		ArrayList<Coupon> al = couponDB.getCompanyCoupons(company);
		companyDB.removeCompanyCoupon(company);
		for (Coupon coupon : al) {
			customerDB.removeCustomerCoupon(coupon);
		}
		for (Coupon coupon : al) {
			couponDB.removeCoupon(coupon);
		}
		companyDB.removeCompany(company);	
	}
	/**
	 * This function updates company details according to its name:
	 * @param company
	 * @throws CouponException
	 */
	public void updateCompany(Company company) throws CouponException{
		companyDB.getCompanyById(company.getId());
		companyDB.updateCompany(company);
	}
	/**
	 * This function returns company details according to its id
	 * @param id
	 * @return 
	 * @throws CouponException
	 */
	public Company getCompany(long id) throws CouponException{
		return companyDB.getCompanyById(id);	
	}
	/**
	 * This function returns customer details according to its id
	 * @param id
	 * @return
	 * @throws CouponException
	 */
	public Customer getCustomer(long id) throws CouponException{
		return customerDB.getCustomerById(id);
	}
	/**
	 * This function updates customer details according to its id
	 * @param customer
	 * @throws CouponException
	 */
	public void updateCustomer(Customer customer) throws CouponException{
		customerDB.getCustomerById(customer.getId());
		customerDB.updateCustomer(customer);
	}
	/**
	 * This function removes the indicated customer
	 * after removing all his purchased coupons.
	 * @param customer
	 * @throws CouponException
	 */
	public void removeCustomer(Customer customer) throws CouponException{
		customerDB.getCustomerById(customer.getId());
		customerDB.removeCustomerCoupon(customer);
		customerDB.removeCustomer(customer);
	}
	/**
	 * This function returns all of the companies to a HashSet
	 * @return
	 * @throws CouponException
	 */
	public HashSet<Company> getAllCompanies() throws CouponException{
		return companyDB.getAllCompanies();
	}
	/**
	 * This function returns all of the customers to a HashSet
	 * @return
	 * @throws CouponException
	 */
	public HashSet<Customer> getAllCustomer() throws CouponException{
		return customerDB.getAllCustomer();
	}
	/**
	 * This function enables printing all companies
	 * @return allCoupanies
	 * @throws CouponException
	 */
	public String AllCompaniesToString() throws CouponException{
		HashSet<Company> companies=companyDB.getAllCompanies();
		String allCompanies="";
		if(companies!=null){
			for(Company c:companies)
				allCompanies=allCompanies+c.getName()+",";
			return allCompanies;
		}
		else
			return "";
	}
	/**
	 * This function enables printing all customers
	 * @return
	 * @throws CouponException
	 */	
	public String AllCustomerToString() throws CouponException{
		HashSet<Customer> customers=customerDB.getAllCustomer();
		String allCustumers="";
		for(Customer c:customers)
			allCustumers=allCustumers+c.getCustName()+",";
		return allCustumers;
	}	
	/**
	 * This function enables the Administrator to log in to the system
	 * @param username
	 * @param password
	 * @return true
	 * @throws CouponException
	 */
	@Override
	public boolean login(String username, String password) {
		if(USERNAME.equals(username)&&PASSWORD.equals(password))
			return true;
		else
			return false;
	}
}