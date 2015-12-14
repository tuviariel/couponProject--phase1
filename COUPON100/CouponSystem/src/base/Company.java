 package base;

import java.util.HashSet;
import java.util.Set;

/**
 *Definition of Company's (who will create coupons) Attributes and Methods: 
 */
public class Company {
	//Company's attributes:
	private long id;
	private String name;
	private String password;
	private String email;
	private Set<Coupon> coupons= new HashSet<>();
	
	//Company's constructors:
	public Company(){}
	/**
	 * This constructor is with no ID (self generated) or Coupons (added by Company later):
	 * @param name
	 * @param password
	 * @param email
	 */
	public Company(String name, String password, String email) {
		this.name = name;
		this.password = password;
		this.email = email;
	}
	/**
	 * This constructor is with no ID (self generated):
	 * @param name
	 * @param password
	 * @param email
	 * @param coupons
	 */
	public Company(String name, String password, String email, HashSet<Coupon> coupons) {
		this.name = name;
		this.password = password;
		this.email = email;
		this.coupons = coupons;
	}
	/**
	 * This constructor is with no Coupons (added by Company later):
	 * @param id
	 * @param name
	 * @param password
	 * @param email
	 */
	public Company(int id, String name, String password, String email) {
		this.id= id;
		this.name = name;
		this.password = password;
		this.email = email;
	}
	/**
	 * This constructor includes all attributes:
	 * @param id
	 * @param name
	 * @param password
	 * @param email
	 * @param coupons
	 */
	public Company(int id, String name, String password, String email, HashSet<Coupon> coupons) {
		this.id= id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.coupons = coupons;
	}
	//Company's Methods (getters and setters):
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Set<Coupon> getCoupons() {
		return coupons;
	}
	public void addCoupon(Coupon coupons) {
		this.coupons.add(coupons);
	}
	/**
	 * This function returns string including all the Coupon details:
	 * @return
	 */
	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", password="
				+ password + ", email=" + email + ", coupons=" + coupons + "]";
	}
	
}