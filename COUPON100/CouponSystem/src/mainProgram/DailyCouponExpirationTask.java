package mainProgram;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import DAO.CouponDAO;
import DBDAO.CouponDBDAO;
import base.Coupon;
import exceptionForCouponSystem.CouponException;
import exceptionForCouponSystem.CouponMessage;
import facade.CompanyFacade;
/**
 * the class responsible for the daily coupon update- removing all the coupons that are out-dated.
 * it is a thread that repeats itself once every 24hours.
 */
public class DailyCouponExpirationTask implements Runnable{
	//Attributes:
	private boolean quit;
	//Achieving access to the database through the couponDAO methods:
	private CouponDAO couponDB;
	/**
	 * Constructor (enables to throw the exception on to the main)
	 * @throws CouponException 
	 */
	public DailyCouponExpirationTask() throws CouponException{
		couponDB= new CouponDBDAO();
		
	}
	/**
	 * the run() method that executes the daily coupon update.
	 */
	@Override
	public void run(){
		Set<Coupon> removedCoupons= new HashSet<>();
		CouponMessage cm= new CouponMessage();
		try {
			while(!quit){ 
				ArrayList<Coupon> hs = couponDB.getAllCoupon();
				for (Coupon coupon : hs) {
					if(new Date().before(coupon.getEndDate())){
						removedCoupons.add(coupon);
						new CompanyFacade().removeCoupon(coupon);
					}
				}
				if (!removedCoupons.isEmpty())
					System.out.println("These coupons were removed from the database becuase they are out-dated: \n"+removedCoupons);
				Thread.sleep(1000*3600*24);
			}
		}catch (CouponException e) {
			try {
				cm.cem();
			} catch (CouponException e1) {
				e1.printStackTrace();
			}
		}catch (InterruptedException e) {
			try {
				cm.iem();
			} catch (CouponException e1) {
				e1.printStackTrace();
			}
		} 
	}
	/**
	 * the function that ends the thread (used while quitting the system).
	 */
	public void stopTask(){
		quit = true;
	}
}