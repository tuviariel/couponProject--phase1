package DAO;
import java.sql.Date;
import java.util.ArrayList;
import base.*;
import exceptionForCouponSystem.CouponException;

/**
 *The Interface of the Coupon class that is responsible for the connection to related Data:
 */
public interface CouponDAO {
	public void createCoupon(Coupon coupon) throws CouponException;
	public void removeCoupon(Coupon coupon) throws CouponException;
	public void updateCoupon(Coupon coupon) throws CouponException;
	public Coupon getCoupon(long id) throws CouponException  ;
	public ArrayList<Coupon> getAllCoupon() throws CouponException ;
	public ArrayList<Coupon> getCopounByType(CouponType type) throws CouponException  ;
	public ArrayList<Coupon> getCouponByLateDate(Company company, Date endDate) throws CouponException;
	public ArrayList<Coupon> getCouponByMaxPrice(Company company, double price) throws CouponException;
	public ArrayList<Coupon> getCouponByType(Company company, CouponType type) throws CouponException;
	public ArrayList<Coupon> getCompanyCoupons(Company company) throws CouponException ;
	public ArrayList<Coupon> getCustomerCoupons(Customer customer) throws CouponException  ;
	public ArrayList<Coupon> getAllPurchasedCouponsByType(Customer customer, CouponType type) throws CouponException ;
	public ArrayList<Coupon> getAllPurchasedCouponsByPrice(Customer customer, double price) throws CouponException  ;
	public boolean isCoupon(String name) throws CouponException;
}