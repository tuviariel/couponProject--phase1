package base;

import java.util.*;

/**
 *Definition of Customer's (who will purchase coupons) Attributes and Methods: 
 */
public class Customer {
	//Customer's Attributes:
	private long id;
	private String custName;
	private String password;
	private HashSet<Coupon> coupons = new HashSet<Coupon>();
	
	//Customer's Constructor:
	public Customer(){
	}
	/**
	 * This constructor is with no ID (self generated) or Coupons (added by Company later):
	 * @param custName
	 * @param password
	 */
	public Customer(String custName, String password) {
		setCustName(custName);
		setPassword(password);
	}
	/**
	 * This constructor is with no Coupons (added by Company later):
	 * @param custName
	 * @param password
	 */
	public Customer(long id, String custName, String password) {
		this.id =id;
		this.custName= custName;
		this.password= password;
	}
	//Customers's Methods (getters and setters):
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public HashSet<Coupon> getCoupons() {
		return coupons;
	}
	public void AddCoupons(Coupon coupons) {
		this.coupons.add(coupons);
	}
	
	/**
	 * This function returns string including all the Customer's details:
	 * @return
	 */
	@Override
	public String toString() {
		return "Customer [id=" + id + ", custName=" + custName + ", password="
				+ password + ", coupons=" + coupons + "]";
	}

}

