package facade;
import java.sql.Date;
import java.util.ArrayList;

import base.*;
import exceptionForCouponSystem.CouponException;
import DAO.CompanyDAO;
import DAO.CouponDAO;
import DAO.CustomerDAO;
import DBDAO.*;
/**
 * the class responsible for a company's facade in the coupon system.
 * implements client interface
 */
public class CompanyFacade implements Client{
	////Achieving access to the database through the DAO classes: 
	private CouponDAO couponDB; 
	private CompanyDAO companyDB;
	private CustomerDAO customerDB; 
	private Company company;
	/**
	 * Constructor (enables to throw the exception on to the main)
	 * @throws CouponException 
	 */
	public CompanyFacade() throws CouponException{
		couponDB = new CouponDBDAO();
		companyDB = new CompanyDBDAO();
		customerDB = new CustomerDBDAO();
	}
	/**
	 * This function creates a coupon on the database,
	 * checking the coupon does not duplicate another coupon's name
	 * @throws CouponException
	 */
	public void createCoupon(Coupon coupon) throws CouponException{
			if (!couponDB.isCoupon(coupon.getTitle())){
			couponDB.createCoupon(coupon);
			companyDB.addCompanyCoupon(company, coupon);
		}
		else
			throw new CouponException("A coupon with that name already exsists in the database.");
	}
	/**
	 * This function removes a coupon from the database-
	 * first from the Customer-Coupon joined table and from the Company-Coupon joined table,
	 * and at last from the coupon table.
	 * @param coupon
	 * @throws CouponException
	 */
	public void removeCoupon(Coupon coupon) throws CouponException{
		customerDB.removeCustomerCoupon(coupon);
		companyDB.removeCompanyCoupon(coupon);
		couponDB.removeCoupon(coupon);
	}
	/**
	 * This function updates a coupon info in the database-
	 * @param coupon
	 * @throws CouponException
	 */
	public void updateCoupon(Coupon coupon) throws CouponException{
		couponDB.updateCoupon(coupon);
	}
	/**
	 * This function gets a coupon from the database by its id.
	 * Checking if it exists.
	 * @throws CouponException
	 */
	public Coupon getCoupon(int id) throws CouponException{
		if(couponDB.getCoupon(id)==null)
			throw new CouponException("there is no coupon maching the request");
		else
			return couponDB.getCoupon(id);
	}
	/**
	 * This function gets all of the company's coupons from the database.
	 * Checking if they exist.
	 * @throws CouponException
	 */
	public ArrayList<Coupon> getAllCoupon() throws CouponException{
		if(couponDB.getCompanyCoupons(company)==null)
			throw new CouponException("there are no coupons maching the request");
		else
			return couponDB.getCompanyCoupons(company);
	}
	/**
	 * This function gets all of the company's coupons by their type from the database.
	 * Checking if they exist.
	 * @param type
	 * @throws CouponException
	 */
	public ArrayList<Coupon> getCouponByType(CouponType type) throws CouponException{
		if(couponDB.getCouponByType(company, type)==null)
			throw new CouponException("there are no coupons maching the request");
		else
			return couponDB.getCouponByType(company, type);
	}
	/**
	 * This function gets all of the company's coupons by their max price from the database.
	 * Checking if they exist.
	 * @param price
	 * @throws CouponException
	 */
	public ArrayList<Coupon> getCouponByMaxPrice(double price) throws CouponException{
		if(couponDB.getCouponByMaxPrice(company, price)==null)
			throw new CouponException("there are no coupons maching the request");
		else
			return couponDB.getCouponByMaxPrice(company, price);
	}
	/**
	 * This function gets all of the company's coupons by their end-date from the database.
	 * Checking if they exist.
	 * @param endDate
	 * @throws CouponException
	 */
	public ArrayList<Coupon> getCouponByLateDate(Date endDate) throws CouponException{
		if(couponDB.getCouponByLateDate(company, endDate)==null)
			throw new CouponException("there are no coupons maching the request");
		else
			return couponDB.getCouponByLateDate(company, endDate);
	}
	/**
	 * This function enables a Company to log in to the system:
	 * @param username
	 * @param password
	 * @return true
	 * @throws CouponException
	 */
	@Override
	public boolean login(String username, String password) throws CouponException{
		if(companyDB.login(username, password)){
			this.company = companyDB.getCompanyByName(username);
			return true;
		}
		return false;
	}
}