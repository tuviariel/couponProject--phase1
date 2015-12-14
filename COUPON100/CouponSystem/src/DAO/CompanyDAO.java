package DAO;

import java.util.HashSet;
import exceptionForCouponSystem.CouponException;
import base.*;

/**
 *The Interface of the Company class that is responsible for the connection to related Data: 
 *
 */
public interface CompanyDAO {
	public void createCompany(Company company) throws CouponException;
	public void removeCompany(Company company) throws CouponException;
	public void updateCompany(Company company) throws CouponException;
	public Company getCompanyById(long id) throws CouponException;
	public Company getCompanyByName(String name) throws CouponException;
	public boolean isCompany(String compName) throws CouponException;
	public HashSet<Company> getAllCompanies() throws CouponException;
	public boolean login (String compName, String password) throws CouponException;
	public void addCompanyCoupon(Company company, Coupon coupon) throws CouponException;
	public void removeCompanyCoupon(Company company) throws CouponException;
	public void removeCompanyCoupon(Coupon coupon) throws CouponException;
}
