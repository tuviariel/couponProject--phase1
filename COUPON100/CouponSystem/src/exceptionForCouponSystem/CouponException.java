package exceptionForCouponSystem;

/**
 * the class that created the CouponException to be used in this system.
 * every exception that may arise within the system is converted to a 
 * CouponException and it's message was edited to fit the users needs.
 */
@SuppressWarnings("serial")
public class CouponException extends Exception {
	public CouponException(String message){
		super(message);
	}
}
