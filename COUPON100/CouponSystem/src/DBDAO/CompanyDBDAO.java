package DBDAO;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

import exceptionForCouponSystem.CouponException;
import base.Company;
import base.Coupon;
import mainProgram.ConnectionPool;
import DAO.CompanyDAO;
 
/**
 * The Class that implements CompanyDAO and is responsible for retrieving the data from the Derby Database:
 *
 */
public class CompanyDBDAO implements CompanyDAO{
	//Creating tools from imported java.sql classes to work with database: 
	Connection connection;
	PreparedStatement ps;
	ResultSet rs;
	Statement st;
	//getting an instance of a connection from the ConnectionPool:
	ConnectionPool pool;
	/**
	 * Constructor (enables to throw the exception on to the main)
	 * @throws CouponException 
	 */
	public CompanyDBDAO() throws CouponException {
		pool = ConnectionPool.getInstance();	
	}	
		/**
	 *Creating a new Company in the Company table in the database:
	 *@param company
	 *@throws CouponException
	 */
	@Override
	public void createCompany(Company company) throws CouponException {
		try{
			connection= pool.getConnection();
			ps = connection.prepareStatement("INSERT INTO APP.COMPANY (COMP_NAME, PASSWORD, EMAIL) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
			
	        ps.setString(1, company.getName());
	        ps.setString(2, company.getPassword());
	        ps.setString(3, company.getEmail());
	        ps.executeUpdate();
	        
	        rs= ps.getGeneratedKeys();
	        
	        if(rs!= null && rs.next()) {
	        	Long key= rs.getLong(1);
	        	company.setId(key);
	        }
	        
	    }catch (SQLException e){
	    	throw new CouponException("there is a problem in the connection to the database.");
	    }finally {
			pool.returnConnection(connection);
		}
	}
	/**
	 *Removing a Company from the Company table in the database: 
	 *@param company
	 *@throws CouponException
	 */
	@Override
	public void removeCompany(Company company) throws CouponException{
		try{
			connection= pool.getConnection();
			ps = connection.prepareStatement("DELETE FROM APP.COMPANY WHERE ID=?");
	        ps.setLong(1, company.getId());
	        ps.executeUpdate();
	        	
		}catch (SQLException e){
	    	throw new CouponException("there is a problem in the connection to the database.");
	    } finally {
			pool.returnConnection(connection);
		}
	}
	/**
	 *Updating a Company in the Company table in the database: 
	 *@param company
	 *@throws CouponException
	 */
	@Override
	public void updateCompany(Company company) throws CouponException {
		try{
			connection= pool.getConnection();
			ps = connection.prepareStatement("UPDATE APP.COMPANY SET PASSWORD=?,EMAIL=? WHERE ID=?");
	     
	        ps.setString(1, company.getPassword());
	        ps.setString(2, company.getEmail());
	        ps.setLong(3, company.getId());
	       
	        ps.executeUpdate();
		}catch (SQLException e){
	    	throw new CouponException("there is a problem in the connection to the database.");
	    } finally {
			pool.returnConnection(connection);
		}
	}
	/**
	 *Show a Company's details by its ID number from the Company table in the database: 
	 *@param id
	 *@return company
	 *@throws CouponException
	 */
	@Override
	public Company getCompanyById(long id) throws CouponException {
		Company company;
		try{
			connection= pool.getConnection();
			ps = connection.prepareStatement("SELECT * FROM APP.COMPANY WHERE ID="+id);
			rs=ps.executeQuery();
	        if (rs.next()){
	        	company= new Company(rs.getInt("ID"),
	        			rs.getString("COMP_NAME"),
	        			rs.getString("PASSWORD"),
	        			rs.getString("EMAIL"));
	        }else{
	        	if(id!=0){
					throw new CouponException("There was no Company found with the id #"+id+" in the database");
				}else{
					throw new CouponException("That Customer was not found in the database.");
				}
	        }
	        return company;
		}catch (SQLException e){
	    	throw new CouponException("there is a problem in the connection to the database.");
	    } finally {
			pool.returnConnection(connection);
		}
	}
	/**
	 *Show a Company's details by its name (used at login) from the Company table in the database: 
	 *@param name
	 *@return company
	 *@throws CouponException
	 */
	@Override
	public Company getCompanyByName(String name) throws CouponException {
		Company company;
		try{
			connection= pool.getConnection();
			ps = connection.prepareStatement("SELECT * FROM APP.COMPANY WHERE COMP_NAME='"+name+"'");
			rs=ps.executeQuery();
	        if (rs.next()){
	        	company= new Company(rs.getInt("ID"),
	        						rs.getString("COMP_NAME"),
	        						rs.getString("PASSWORD"),
	        						rs.getString("EMAIL"));
	        }else{
	        	throw new CouponException("There is no company named '"+name+"' in the database.");
	        }
	        return company;
		}catch (SQLException e){
	    	throw new CouponException("there is a problem in the connection to the database.");
	    } finally {
			pool.returnConnection(connection);
		}
	}
	/**
	 *Checking all of the Companies names so that there won't be a duplication while creating new company in the database: 
	 *@param compName
	 *@return true
	 *@throws CouponException
	 */
	@Override
	public boolean isCompany(String compName) throws CouponException {
		try{
			connection= pool.getConnection();
			ps = connection.prepareStatement("SELECT * FROM APP.COMPANY WHERE COMP_NAME='"+compName+"'");
			rs= ps.executeQuery();
	       	if(rs.next())
	       		return true;
	       	else
				return false;
		}catch (SQLException e){
	    	throw new CouponException("there is a problem in the connection to the database.");
	    } finally {
			pool.returnConnection(connection);
		}
	}
	/**
	 *Creating a list of all the companies in the database for the Administrator's use: 
	 *@param HashSet<Copmpany>
	 *@return true
	 *@throws CouponException
	 */
	@Override
	public HashSet<Company> getAllCompanies() throws CouponException {
		Set<Company> company= new HashSet<>();
		Set<Integer> id=new HashSet<>();
		try{	
			connection= pool.getConnection();
			ps = connection.prepareStatement("SELECT ID FROM APP.COMPANY");
			rs= ps.executeQuery();
			if (rs==null)
				throw new CouponException("There are no companys found in the database");
			while (rs.next()){ 
				id.add(rs.getInt(1));
			}
			for(Integer i:id)
				company.add(this.getCompanyById(i));
			return (HashSet<Company>) company;
		}catch (SQLException e){
	    	throw new CouponException("there is a problem in the connection to the database.");
	    } finally {
			pool.returnConnection(connection);
		}
        
	}
	/**
	 *Checking a Company's Password in relation to its Name that appears in database 
	 *in order to allow the company's representative to login to the system: 
	 *@param compName
	 *@param password
	 *@return true
	 *@throws CouponException
	 */
	@Override
	public boolean login(String compName, String password) throws CouponException {
		try{
			connection = pool.getConnection();
			st = connection.createStatement();
			rs = st.executeQuery("SELECT PASSWORD FROM APP.COMPANY WHERE COMP_NAME='"+compName+"'");	
			if(rs.next())
				if (password.equals(rs.getString(1))) 	
					return true;
				else	
					throw new CouponException("Incorect Password");
			else
				throw new CouponException("there is no such company name in database");
	    
		}catch(SQLException e){
			throw new CouponException("there is a problem in the connection to the database.");
		} finally {
			pool.returnConnection(connection);
		}
	}
	/**
	 *Adding Company's ID and the Company's Coupon's ID to the Join table in the database: 
	 *@param company
	 *@param coupon
	 *@throws CouponException
	 */ 
	@Override
	public void addCompanyCoupon(Company company, Coupon coupon) throws CouponException {
		try {
			connection = pool.getConnection();
			
			ps = connection.prepareStatement("INSERT INTO APP.COMPANY_COUPON (COMP_ID, COUPON_ID) VALUES(?,?)",Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, company.getId());
			ps.setLong(2, coupon.getId());
			ps.executeUpdate(); 
		}catch(SQLException e){
			throw new CouponException("there is a problem in the connection to the database.");
		} finally {
			pool.returnConnection(connection);
		}	
	}
	/**
	 *Removing a Company from the Joined Company-Coupon table in the database by the Company's ID: 
	 *@param company
	 *@throws CouponException
	 */
	@Override
	public void removeCompanyCoupon(Company company) throws CouponException {
		try {
			connection = pool.getConnection();
			st = connection.createStatement();
			st.execute("DELETE FROM APP.COMPANY_COUPON WHERE COMP_ID = "+company.getId());
		}catch(SQLException e){
			throw new CouponException("there is a problem in the connection to the database.");
		} finally {
			pool.returnConnection(connection);
		}
	}
	/**
	 *Removing a Coupon from the Joined Company-Coupon table in the database by the Coupon's ID: 
	 *@param coupon
	 *@throws CouponException
	 */
	@Override
	public void removeCompanyCoupon(Coupon coupon) throws CouponException{
		try {
			connection = pool.getConnection();
			st = connection.createStatement();
			st.execute("DELETE FROM APP.COMPANY_COUPON WHERE COUPON_ID = "+coupon.getId());
		}catch(SQLException e){
			throw new CouponException("there is a problem in the connection to the database.");
		} finally {
			pool.returnConnection(connection);
		}	
	}
}