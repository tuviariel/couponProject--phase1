package DAO;
import java.util.HashSet;

import exceptionForCouponSystem.CouponException;
import base.*;

/**
 *The Interface of the Customer class that is responsible for the connection to related Data:
 *
 */
public interface CustomerDAO {
	public void createCustomer(Customer customer) throws CouponException;
	public void removeCustomer(Customer customer) throws CouponException;
	public void updateCustomer(Customer customer) throws CouponException;
	public HashSet<Customer> getAllCustomer() throws CouponException;
	public HashSet<Coupon> getCoupons(Customer customer) throws CouponException;
	public boolean login(String custName, String password) throws CouponException;
	public Customer getCustomerByName(String name) throws CouponException;
	public Customer getCustomerById(long id) throws CouponException;
	public void removeCustomerCoupon(Coupon coupon) throws CouponException;
	public void removeCustomerCoupon(Customer customer) throws CouponException;
	public void addCustomerCoupon(Customer customer, Coupon coupon) throws CouponException;
	public boolean IsBought(Customer customer, Coupon coupon) throws CouponException;
	boolean isCustomer(String custName) throws CouponException;
	
	

}
