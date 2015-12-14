package mainProgram;

import base.*;
import exceptionForCouponSystem.CouponException;
import facade.*;

import java.sql.Date;

public class TestMain {
	
	public static void main(String[] args) throws Exception{
		
		
		
		//couponSystem load:
		CouponSystem couponSystem=CouponSystem.getInstance();
		//admin and his functions:
		Client admin = (AdminFacade) couponSystem.Login("admin", "1234", "admin");
		System.out.println("admin is loged in.");
		//(companies)
		Company company1 = new Company("Tuviacom","1234","tuvia@gmail.com");
		Company company2 = new Company("Mudicom","1234","mudi@gmail.com");
		Company company3 = new Company("Meircom","1234","meir@gmail.com");
		Company company4 = new Company("Removecom","1234","remove@gmail.com");
		
		((AdminFacade)admin).createCompany(company1);
		System.out.println("admin create and get company1: "+((AdminFacade)admin).getCompany(1));
		((AdminFacade)admin).createCompany(company2);
		System.out.println("admin create and getAll company1+2: "+((AdminFacade)admin).getAllCompanies());
		try{
			((AdminFacade)admin).createCompany(company2);
		}catch (CouponException e){
			System.out.println("trying to create a company with the same name prints: "+e.getMessage());
		}
		company2.setPassword("4321");
		company2.setEmail("mudi@mudi.com");
		((AdminFacade)admin).updateCompany(company2);
		System.out.println("admin update (password & email) and get company2: "+((AdminFacade)admin).getCompany(2));
		((AdminFacade)admin).createCompany(company3);
		System.out.println("admin create company3 (getAll to show): "+((AdminFacade)admin).getAllCompanies());
		System.out.println("");
		
		//(customers)
		Customer customer1 = new Customer("Tuviacus","1234");
		Customer customer2 = new Customer("Mudicus","1234");
		Customer customer3 = new Customer("Meircus","1234");
		Customer customer4 = new Customer("removecus","1234");
		
		((AdminFacade)admin).createCustomer(customer1);
		System.out.println("admin create and get customer1: "+((AdminFacade)admin).getCustomer(1));
		((AdminFacade)admin).createCustomer(customer2);
		System.out.println("admin create and get customer1+2: "+((AdminFacade)admin).getAllCustomer());
		try{
			((AdminFacade)admin).createCustomer(customer2);
		}catch (CouponException e){
			System.out.println("trying to create a customer with the same name prints: "+e.getMessage());
		}
		customer2.setPassword("4321");
		((AdminFacade)admin).updateCustomer(customer2);
		System.out.println("admin update (password) and get customer2: "+((AdminFacade)admin).getCustomer(2));
		((AdminFacade)admin).createCustomer(customer3);
		System.out.println("admin created customer3 (getAll to show): "+((AdminFacade)admin).getAllCustomer());
		System.out.println("remove customer and company by administrator will be demonstrated towards end of demonstration...");
		System.out.println("");
		
		//company and his functions:
		try{
		Client company_name = (CompanyFacade) couponSystem.Login("Tuviaco","1234","company");
		}catch (CouponException e){
			System.out.println("company logging in with wrong name prints: "+e.getMessage());
		}
		try{
		Client company_pass = (CompanyFacade) couponSystem.Login("Tuviacom","5678","company");
		}catch (CouponException e){
			System.out.println("company logging in with wrong password prints: "+e.getMessage());
		}
		Client company = (CompanyFacade) couponSystem.Login("Tuviacom","1234","company");
		System.out.println("company Tuviacom is loged in.");
		
		Coupon coupon1 = new Coupon("Aroma",new Date((360000000*24*3)+new java.util.Date().getTime()) , 100, CouponType.FOOD, "Coupon to aroma", 50.2,"",new Date(new java.util.Date().getTime()));
		Coupon coupon2 = new Coupon("Lametayel",new Date((360000000*24*3)+new java.util.Date().getTime()) , 100, CouponType.CAMPING, "Coupon to lametayel", 50.2,"",new Date(new java.util.Date().getTime()));
		
		((CompanyFacade)company).createCoupon(coupon1);
		System.out.println("company created coupon1 (getCoupon to show): "+((CompanyFacade)company).getCoupon(1));
		
		try{
			((CompanyFacade)company).createCoupon(coupon1);
		}catch (CouponException e){
			System.out.println("trying to create a coupon with the same name prints: "+e.getMessage());
		}
		
		coupon1.setEndDate(new Date((36000000*24*60)+new java.util.Date().getTime()));
		coupon1.setPrice(57.34);
		((CompanyFacade)company).updateCoupon(coupon1);
		System.out.println("company updates coupon1- end-date and price (getAllCoupon to show): "+((CompanyFacade)company).getAllCoupon());
		((CompanyFacade)company).createCoupon(coupon2);
		System.out.println("company creates coupon2 (getAllCoupon to show): "+((CompanyFacade)company).getAllCoupon());
		System.out.println("company gets coupon by later-date (coupon2- shows only it): "+((CompanyFacade)company).getCouponByLateDate(new Date((36000000*24*30)+new java.util.Date().getTime())));
		System.out.println("company gets coupon by max-price(53) (coupon2- shows only it): "+((CompanyFacade)company).getCouponByMaxPrice(53));
		System.out.println("company gets coupon by type-Food (coupon1- shows only it): "+((CompanyFacade)company).getCouponByType(CouponType.FOOD));
		//remove coupon by company will be demonstrated after customers functions...
		System.out.println("remove coupon by company will be demonstrated towards end of demonstration...\n");
		
		//customer and his functions:
		try{
		Client customer_name = (CustomerFacade) couponSystem.Login("Meircu","1234","customer");
		}catch (CouponException e){
			System.out.println("customer logging in with wrong name prints: "+e.getMessage());
		}
		try{
		Client Customer_pass = (CustomerFacade) couponSystem.Login("Meircus","5678","customer");
		}catch (CouponException e){
			System.out.println("customer logging in with wrong password prints: "+e.getMessage());
		}
		Client customer = (CustomerFacade) couponSystem.Login("Meircus","1234","customer");
		System.out.println("customer Meircus is loged in.");
		((CustomerFacade)customer).purchaseCoupon(coupon1);
		System.out.println("customer purchased coupon1 (getAllPurchasesCoupons to show): "+((CustomerFacade)customer).getAllPurchasesCoupons());
		try{
			((CustomerFacade)customer).purchaseCoupon(coupon1);
		}catch (CouponException e){
			System.out.println("trying to purchase a coupon twice prints: "+e.getMessage());
		}
		((CustomerFacade)customer).purchaseCoupon(coupon2);
		System.out.println("customer purchased coupon2+1 (getAllPurchasesCoupons to show): "+((CustomerFacade)customer).getAllPurchasesCoupons());
		System.out.println("customer gets purchased coupon by max-price(53) (coupon2- shows only it): "+((CustomerFacade)customer).getAllPurchasesCouponsByPrice(53));
		System.out.println("customer gets purchased coupon by type-Food (coupon1- shows only it): "+((CustomerFacade)customer).getAllPurchasesCouponsByType(CouponType.FOOD));
		System.out.println("");
		//admin getting company and customer with their coupons:
		System.out.println("admin's sumory");
		System.out.println("admin geting all the companies with details: "+((AdminFacade)admin).getAllCompanies());
		System.out.println("admin geting all the customers with details: "+((AdminFacade)admin).getAllCustomer());
		System.out.println("admin geting all the companie's names: "+((AdminFacade)admin).AllCompaniesToString());
		System.out.println("admin geting all the customer's names: "+((AdminFacade)admin).AllCustomerToString());
		System.out.println("");
		//company removing coupons- and deletes it from customer:
		System.out.println("removing methods:");
		((CompanyFacade)company).removeCoupon(coupon2);
		System.out.println("company removes coupon2 from both customerDB and companyDB:\n\t(getAllCoupon of company user to show): "+((CompanyFacade)company).getAllCoupon()+
				"\n\t(getAllPurchasesCoupons of customer user to show)"+((CustomerFacade)customer).getAllPurchasesCoupons());
		//admin removing customer and companies:
		try{
			((AdminFacade)admin).removeCustomer(customer4);
		}catch (CouponException e){
			System.out.println("admin trying to remove a customer that doesn't exist prints: "+e.getMessage());
		}
		((AdminFacade)admin).removeCustomer(customer2);
		System.out.println("admin removing customer2 (getAll to show): "+((AdminFacade)admin).getAllCustomer());
		try{
			((AdminFacade)admin).removeCompany(company4);
		}catch (CouponException e){
			System.out.println("admin trying to remove a company that doesn't exist prints: "+e.getMessage());
		}
		((AdminFacade)admin).removeCompany(company2);
		System.out.println("admin removing company2 (getAll to show): "+((AdminFacade)admin).getAllCompanies());
		
		Coupon coupon3 = new Coupon("Aromasdfb2",new Date((36000000*24*31)+new java.util.Date().getTime()) , 100, CouponType.FOOD, "Coupon to aroma", 502.32,"",new Date(new java.util.Date().getTime()));
		Coupon coupon4 = new Coupon("Lametsdfbayel2",new Date((360000000*24*32)+new java.util.Date().getTime()) , 100, CouponType.CAMPING, "Coupon to lametayel", 510.32,"",new Date(new java.util.Date().getTime()));
		Coupon coupon5 = new Coupon("Arodsfma3",new Date((36000000*24*33)+new java.util.Date().getTime()) , 100, CouponType.FOOD, "Coupon to aroma", 560.62,"",new Date(new java.util.Date().getTime()));
		Coupon coupon6 = new Coupon("Lahfsrshmetayel2",new Date((360000000*24*34)+new java.util.Date().getTime()) , 100, CouponType.CAMPING, "Coupon to lametayel", 507.29,"",new Date(new java.util.Date().getTime()));
		
		((CompanyFacade)company).createCoupon(coupon3);
		((CompanyFacade)company).createCoupon(coupon4);
		((CompanyFacade)company).createCoupon(coupon5);
		((CompanyFacade)company).createCoupon(coupon6);

		System.out.println("created more coupons for DBtest");
		System.out.println("shutting down..");
		couponSystem.shutDown();
		//end	
		}		
	}