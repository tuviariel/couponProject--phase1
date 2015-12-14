package facade;

import java.sql.SQLException;

import exceptionForCouponSystem.CouponException;

/**
 * the Interface that the different clients of the coupon system are based on.
 * there is one common function to all the clients and that is that they all log in to the system.
 */
public interface Client {
	public boolean login(String	username,String password) throws CouponException, SQLException;
}
