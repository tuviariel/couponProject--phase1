package base;

import java.sql.Date;

/**
 *Definition of Coupon's (who will create coupons) Attributes and Methods: 
 */
public class Coupon {
	//Coupon's attributes:
	private long id;
	private String title;
	private Date startDate;
	private Date endDate;
	private int amount;
	private CouponType type;
	private String message;
	private double price;
	private String image;
	
	//Coupon's constructors:
	public Coupon(){
	}
	/**
	 * This constructor get the following parameters in order to create new Coupon:
	 * 		title,end date, amount, type, message, price, image path and start date
	 * @param title
	 * @param endDate
	 * @param amount
	 * @param type
	 * @param message
	 * @param price
	 * @param image
	 * @param startDate
	 */
	public Coupon(String title,Date endDate,int amount,CouponType type,String message,double price,String image,Date startDate){
		this.title=title;
		this.endDate=endDate;
		this.amount=amount;
		this.type=type;
		this.message=message;
		this.price=price;
		this.image=image;
		this.startDate=startDate;
	}
	/**
	 * This constructor get the following parameters in order to create new Coupon:
	 * 		id,title,end date, amount, type, message, price, image path and start date
	 * @param id
	 * @param title
	 * @param endDate
	 * @param amount
	 * @param type
	 * @param message
	 * @param price
	 * @param image
	 * @param startDate
	 */
public Coupon(long id,String title,Date endDate,int amount,CouponType type,String message,double price,String image,Date startDate){
		this.id=id;
		this.title=title;
		this.endDate=endDate;
		this.amount=amount;
		this.type=type;
		this.message=message;
		this.price=price;
		this.image=image;
		this.startDate=startDate;
	}
	
	//Coupon's Methods (getters and setters):
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setId(long id) {
		this.id=id;
	}
	public Date getStartDate() {
		return startDate;
	}
	public long getId() {
		return id;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public CouponType getType() {
		return type;
	}
	public void setType(CouponType type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	/**
	 * This function returns string including all the Coupon details.
	 */
	public String toString(){
		return "id="+id+",title="+title+
				",start date="+startDate+",end date="+endDate+
				",amount="+amount+",type="+type.toString()+
				",meassage="+message+",price="+price+",image path="+image;
	}

	/**
	 * This function allows you to compare between to coupons.
	 */
	@Override
	public boolean equals(Object coupon){
		if(coupon instanceof Coupon)
			if(this.getId()==((Coupon)coupon).getId())
				return true;
		return false;
}
}
