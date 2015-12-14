package mainProgram;

import java.sql.SQLException;
import exceptionForCouponSystem.CouponException;
import facade.*;

/**
 * the class with the main coupon system methods:
 */
public class CouponSystem {
	//Attributes
	private static CouponSystem couponSystem=null;
	private ConnectionPool pool;
	private DailyCouponExpirationTask dailyExpiration;
	/**
	 * Constructor (enables to throw the exception on to the main)
	 * @throws CouponException 
	 */
	private CouponSystem() throws CouponException{
		 dailyExpiration = new DailyCouponExpirationTask();
		 pool =  ConnectionPool.getInstance();
		 Thread thread=new Thread(dailyExpiration);
		 thread.start();
	}
	/**
	 * a singleton method creating an instance of the coupon system:
	 * @return couponSystem
	 * @throws CouponException 
	 */
	public static CouponSystem getInstance() throws CouponException{
		if(couponSystem==null){
			couponSystem = new CouponSystem();
			return couponSystem;
		}
		else
			return couponSystem;
	}
	/**
	 * The function that allows all the clients of the system to enter the system using they're login details:
	 * @param name
	 * @param password
	 * @param clientType
	 * @return
	 * @throws CouponException
	 * @throws SQLException
	 */
	public Client Login(String name,String password,String clientType) throws CouponException{
		if (clientType.equalsIgnoreCase("admin")){
			AdminFacade adminFacade=new AdminFacade();
			if(adminFacade.login(name, password))
				return adminFacade;			
		}
		if (clientType.equalsIgnoreCase("customer")){
			CustomerFacade customerFacade=new CustomerFacade();
			if(customerFacade.login(name, password))
				return customerFacade;			
		}
		if (clientType.equalsIgnoreCase("company")){
			CompanyFacade companyFacade=new CompanyFacade();
			if(companyFacade.login(name, password))
				return companyFacade;			
		}	
		return null;
	}
	/**
	 * the function that shuts down the coupon system:
	 * (closes all the connections from the pool 
	 * and stops the daily expiration remove coupon task)
	 * @throws CouponException
	 */
	public void shutDown() throws CouponException{
		
			pool.closeAllConnections();
			dailyExpiration.stopTask();	
	}
}

	