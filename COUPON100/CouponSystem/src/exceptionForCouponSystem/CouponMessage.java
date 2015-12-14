package exceptionForCouponSystem;
/**
 * this class enables for messages to be thrown from the mainProgram.DailyCouponExpirationTask.run() method
 * as it won't throw the Exception by-itself because it overrides that method from Runnable.
 * @see mainProgram.DailyCouponExpirationTask.run()
 */
public class CouponMessage{
	/**
	 * message thrown in case of CouponException and SQLException in the task
	 * @return
	 * @throws CouponException
	 */
	public String cem() throws CouponException{
		throw new CouponException("there was a problem activating the DailyCouponExpirationTask because of problems connecting to the database.");
	}
	/**
	 * message thrown in case of InteruptedException in the task
	 * @return
	 * @throws CouponException
	 */
	public String iem() throws CouponException{
		throw new CouponException("there was a problem activating the DailyCouponExpirationTask because another program is interfearing with the task.");
	}
}
