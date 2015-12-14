package DBDAO;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

import base.*;
import exceptionForCouponSystem.CouponException;
import mainProgram.ConnectionPool;
import DAO.CustomerDAO;

/**
 * The Class that implements CustomerDAO and is responsible for retrieving the data from the Derby Database:
 *
 */
public class CustomerDBDAO implements CustomerDAO{
	//Creating tools from imported java.sql classes to work with database:
	Connection connection;
	PreparedStatement ps;
	Statement st;
	ResultSet rs;
	//getting an instance of a connection from the ConnectionPool:
	ConnectionPool pool;
	/**
	 * Constructor (enables to throw the exception on to the main)
	 * @throws CouponException 
	 */
	public CustomerDBDAO() throws CouponException{
		pool =  ConnectionPool.getInstance();
	}
	
	/**
	 *Creating a new Customer in the Customer table in the database:
	 *@param customer
	 *@throws CouponException
	 */
	@Override
	public void createCustomer(Customer customer) throws CouponException {
		try {
			connection = pool.getConnection();
			ps = connection.prepareStatement("INSERT INTO APP.CUSTOMER (CUST_NAME, PASSWORD) VALUES(?,?)",Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, customer.getCustName());
			ps.setString(2, customer.getPassword());
			ps.executeUpdate();
			
			rs = ps.getGeneratedKeys();
			
			if (rs != null && rs.next()) {
			    Long key = rs.getLong(1);
			    customer.setId(key); 
			}
			
		} catch (SQLException e) {
			throw new CouponException("there is a problem in the connection to the database.");
		} finally {
			pool.returnConnection(connection);
		}
	}
	/**
	 *Removing a Customer from the Customer table in the database: 
	 *@param customer
	 *@throws CouponException
	 */
	@Override
	public void removeCustomer(Customer customer) throws CouponException {
		try {
			connection = pool.getConnection();
			st = connection.createStatement();
			st.execute("DELETE FROM APP.CUSTOMER WHERE ID = "+customer.getId());
		} catch (SQLException e) {
			throw new CouponException("there is a problem in the connection to the database.");
		} finally {
			pool.returnConnection(connection);
		}
	}
	/**
	 *Updating a Customer in the Customer table in the database: 
	 *@param customer
	 *@throws CouponException
	 */
	@Override
	public void updateCustomer(Customer customer) throws CouponException {
		
		try {
			connection = pool.getConnection();
			ps = connection.prepareStatement("UPDATE APP.CUSTOMER SET CUST_NAME = ?, PASSWORD = ? WHERE ID = ?");
			ps.setString(1, customer.getCustName());
			ps.setString(2, customer.getPassword());
			ps.setLong(3, customer.getId());
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new CouponException("there is a problem in the connection to the database.");
		}  finally {
			pool.returnConnection(connection);
		}
	}
	/**
	 *Show a Customer's details by its ID number from the Customer table in the database: 
	 *@param id
	 *@return customer
	 *@throws CouponException
	 */
	@Override
	public Customer getCustomerById(long id) throws CouponException {
		Customer customer;
		try {
			connection = pool.getConnection();
			st = connection.createStatement();
			rs  = st.executeQuery("SELECT * FROM APP.CUSTOMER WHERE ID = "+id+"");
			if(rs.next()){
				customer = new Customer(rs.getLong("ID"),
							rs.getString("CUST_NAME"), 
							rs.getString("PASSWORD"));
			}else {
				if(id!=0){
					throw new CouponException("There was no Customer found with the id #"+id+" in the database");
				}else{
					throw new CouponException("That Customer was not found in the database.");
				}
			}
		} catch (SQLException e) {
			throw new CouponException("there is a problem in the connection to the database.");
		}  finally {
			pool.returnConnection(connection);
		}
		return customer;
		
	}
	/**
	 *Show a Customer's details by its name (used at login) from the Customer table in the database:
	 *@param name
	 *@return customer
	 *@throws CouponException
	 */
	@Override
	public Customer getCustomerByName(String name) throws CouponException {
		Customer customer;
		try {
			connection = pool.getConnection();
			st = connection.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM APP.CUSTOMER WHERE CUST_NAME = '"+name+"'");
			if(rs.next()){
				customer = new Customer(rs.getLong("ID"), 
						rs.getString("CUST_NAME"), 
						rs.getString("PASSWORD"));
			}else {
				throw new CouponException("There was no Customer found with the name '"+name+"' in the database");
			}			
		} catch (SQLException e) {
			throw new CouponException("there is a problem in the connection to the database.");
		}finally {
			pool.returnConnection(connection);
		}
		return customer;
		
	}
	/**
	 *Creating a list of all the customers in the database for the Administrator's use: 
	 *@param HashSet<Customer>
	 *@return true
	 *@throws CouponException
	 */
	@Override
	public HashSet<Customer> getAllCustomer() throws CouponException {
		Set<Customer> customer = new HashSet<>();
		Set<Long> id=new HashSet<>();
		try {
			connection = pool.getConnection();
			st = connection.createStatement();
			rs = st.executeQuery("SELECT ID FROM APP.CUSTOMER");
			
			while (rs.next()) {
				id.add(rs.getLong(1));
			}
			for(Long l:id){
				customer.add(this.getCustomerById(l));
			}
		} catch (SQLException e) {
			throw new CouponException("there is a problem in the connection to the database.");
		} finally {
			pool.returnConnection(connection);
		}
		return (HashSet<Customer>) customer;
	}
	/**
	 *Creating a list of all the coupons in the database for the Administrator's use: 
	 *@param HashSet<Coupon>
	 *@return true
	 *@throws CouponException
	 */
	@Override
	public HashSet<Coupon> getCoupons(Customer customer) throws CouponException {
		Set<Coupon> coupons = new HashSet<>();
			try {
				connection = pool.getConnection();
				st = connection.createStatement();
				rs = st.executeQuery("SELECT COUPON_ID FROM APP.CUSTOMER_COUPON WHERE CUST_ID =" + customer.getId());
				
				if(rs == null)
					throw new CouponException("There were no coupons found in the database");
				while (rs.next()){
					coupons.add(new CouponDBDAO().getCoupon(rs.getInt(1)));
				}
				return (HashSet<Coupon>) coupons;
				
			} catch (SQLException e) {
				throw new CouponException("there is a problem in the connection to the database.");
			} finally {
				pool.returnConnection(connection);
			}
	}
	/**
	 *Checking a Customer's Password in relation to its Name that appears in database 
	 *in order to allow the customer's representative to login to the system: 
	 *@param custName
	 *@param password
	 *@return true
	 *@throws CouponException
	 */
	@Override
	public boolean login(String custName, String password) throws CouponException {
		ResultSet rs;
		try{
			connection = pool.getConnection();
			st = connection.createStatement();
			rs = st.executeQuery("SELECT PASSWORD FROM APP.CUSTOMER WHERE CUST_NAME = '"+custName+"'");
				if (rs.next()) 
					if(password.equals(rs.getString(1)))
						return true; 
					else
						throw new CouponException("incorrect password.");
				else
					throw new CouponException("there is no such customer name in the database.");
	    } catch (SQLException e) {
			throw new CouponException("there is a problem in the connection to the database.");
		} finally {
			pool.returnConnection(connection);			
		}
	}
	/**
	 *Removing a Coupon from the Joined Customer-Coupon table in the database by the Coupon's ID: 
	 *@param coupon
	 *@throws CouponException
	 */
	@Override
	public void removeCustomerCoupon(Coupon coupon) throws CouponException {
		try {
			connection = pool.getConnection();
			st = connection.createStatement();
			st.execute("DELETE FROM APP.CUSTOMER_COUPON WHERE COUPON_ID = "+coupon.getId()+"");
				
		} catch (SQLException e) {
			throw new CouponException("there is a problem in the connection to the database.");
		} finally {
			pool.returnConnection(connection);	
		}
	}
	/**
	 *Removing a Customer from the Joined Customer-Coupon table in the database by the Customer's ID: 
	 *@param customer
	 *@throws CouponException
	 */
	@Override
	public void removeCustomerCoupon(Customer customer) throws CouponException {
		try {
			connection = pool.getConnection();
			st = connection.createStatement();
			st.execute("DELETE FROM APP.CUSTOMER_COUPON WHERE CUST_ID = "+customer.getId()+"");
			
				
		} catch (SQLException e) {	
			throw new CouponException("there is a problem in the connection to the database.");
		} finally {
			pool.returnConnection(connection);
		}
	}
	/**
	 *Adding Customer's ID and the Customers's Coupon's ID to the Join table in the database: 
	 *@param customer
	 *@param coupon
	 *@throws CouponException
	 */
	@Override
	public void addCustomerCoupon(Customer customer, Coupon coupon) throws CouponException {
		try {
			connection = pool.getConnection();
			ps = connection.prepareStatement("INSERT INTO APP.CUSTOMER_COUPON (CUST_ID, COUPON_ID) VALUES(?,?)");
			ps.setLong(1, customer.getId());
			ps.setLong(2, coupon.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new CouponException("there is a problem in the connection to the database.");
		}  finally {
			pool.returnConnection(connection);
		}
	}
	/**
	 *Checking a Customers Purchased coupons in order to understand 
	 *if that coupon was already purchased by that customer: 
	 *@param customer
	 *@param coupon
	 *@return true
	 *@throws CouponException
	 */
	@Override
	public boolean IsBought(Customer customer, Coupon coupon) throws CouponException{
		try {
			connection = pool.getConnection();
			st = connection.createStatement();
			rs = st.executeQuery("SELECT COUPON_ID FROM APP.CUSTOMER_COUPON"
								+ " WHERE CUST_ID = "+customer.getId()+"");
			while(rs.next()){
				if(coupon.getId() == rs.getLong(1))
					return true;
			}
		} catch (SQLException e) {
			throw new CouponException("there is a problem in the connection to the database.");
		}  finally {
			pool.returnConnection(connection);
		}
		return false;
		
	}
	/**
	 *Checking all of the Customers names so that there won't be a duplication while creating new customer in the database: 
	 *@param custName
	 *@return true
	 *@throws CouponException
	 */
	@Override
	public boolean isCustomer(String custName) throws CouponException {
		try{
			connection = pool.getConnection();
			st = connection.createStatement();
			rs = st.executeQuery("SELECT * FROM APP.CUSTOMER WHERE CUST_NAME = '"+custName+"'");
			if(rs.next())
				return true;
	    }catch(SQLException e){
	    	throw new CouponException("there is a problem in the connection to the database.");
		} finally {
			pool.returnConnection(connection);
		}
		return false;
	}
}
