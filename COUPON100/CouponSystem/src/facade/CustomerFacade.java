package facade;

import java.sql.Date;
import java.util.ArrayList;
import DAO.*;
import DBDAO.*;
import base.*;
import exceptionForCouponSystem.CouponException;
/**
 * the class responsible for a customer's facade in the coupon system.
 * implements client interface
 */
public class CustomerFacade implements Client {
	//Achieving access to the database through the DAO classes: 
	private Customer customer;
	private CustomerDAO customerDB;
	private CouponDAO couponDB;
	/**
	 * Constructor (enables to throw the exception on to the main)
	 * @throws CouponException 
	 */
	public CustomerFacade() throws CouponException{
		couponDB = new CouponDBDAO();
		customerDB = new CustomerDBDAO();
	}
	/**
	 * This function purchases a coupon from the database,
	 * checking the coupon is not out-dated, 
	 * that there are more coupons in the database
	 * and that the customer did not already purchase that coupon:
	 * @throws CouponException
	 */
	public void purchaseCoupon(Coupon coupon) throws CouponException{
		if(new Date(new java.util.Date().getTime()-3600000*24).before(coupon.getEndDate())){
			if (coupon.getAmount() > 0){
				if(!customerDB.IsBought(customer, coupon)){
					customerDB.addCustomerCoupon(customer, coupon);
					coupon.setAmount(coupon.getAmount() - 1);
					couponDB.updateCoupon(coupon);
				}else throw new CouponException("it seems like you already purchased this coupon- it already exsists in the database.");
			}else throw new CouponException("there are no more of this coupon in the stack. limited amount in the database.");
		}else throw new CouponException("this coupon is out-dated.");
	}
	/**
	 * This function gets a HashSet of the the purchased coupons by the customer:
	 * @throws CouponException
	 */
	public ArrayList<Coupon> getAllPurchasesCoupons() throws CouponException{
		return couponDB.getCustomerCoupons(customer);
	}
	/**
	 * This function gets a HashSet of the the purchased coupons of a specific type by the customer:
	 * @param type
	 * @throws CouponException
	 */
	public ArrayList<Coupon> getAllPurchasesCouponsByType(CouponType type) throws CouponException{
		return couponDB.getAllPurchasedCouponsByType(customer, type);		
	}
	/**
	 * This function gets a HashSet of the the purchased coupons of a specific max price by the customer:
	 * @param price
	 * @throws CouponException
	 */
	public ArrayList<Coupon> getAllPurchasesCouponsByPrice(double price) throws CouponException{
		return couponDB.getAllPurchasedCouponsByPrice(customer, price);
	}
	/**
	 * This function enables a Customer to log in to the system:
	 * @param username
	 * @param password
	 * @return true
	 * @throws CouponException
	 */
	@Override
	public boolean login(String name, String password) throws CouponException{
		if(customerDB.login(name, password)){
			this.customer = customerDB.getCustomerByName(name);
			return true;
		}
		return false;
			
	}
}
