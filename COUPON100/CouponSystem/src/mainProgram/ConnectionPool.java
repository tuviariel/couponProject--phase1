package mainProgram;
import java.sql.*;
import java.util.ArrayList;
import exceptionForCouponSystem.CouponException;
/**
 * this class enables to get a connection to the database in order to retrieve or edit data.
 */
public class ConnectionPool {
	//attributes:
	private static ConnectionPool connectionPool;
	private static int CONNECTIONS=5;
	private ArrayList<Connection> connection = new ArrayList<>();
	//methods:
	/**
	 * a singleton method creating an instance of the connection-pool:
	 * @return connectionPool
	 * @throws CouponException 
	 */
	public static ConnectionPool getInstance() throws CouponException {
		if(connectionPool==null)
			connectionPool=new ConnectionPool();
		return connectionPool;
	}
	/**
	 * the actual algorithm of the connection-pool before it get's the instance (private):	
	 * @throws CouponException
	 */
	private ConnectionPool() throws CouponException {
		try{
			Class.forName("org.apache.derby.jdbc.ClientDriver");
			for(int i=0;i<CONNECTIONS;i++)
				connection.add(DriverManager.getConnection("jdbc:derby://localhost:1527/sample"));
		}catch(Exception e){
			 throw new CouponException("there is a problem with the connection to the database- check to see that it is up and running");
		}
	}
	/**
	 * a synchronized function that enables connection users (DBDAO classes) to get a connection from the pool:
	 * (needs to be synchronized so that connections won't get lost between the pool and the users- 
	 * there is a return function that does the opposite) 
	 * @return
	 * @throws CouponException
	 */
	public synchronized Connection getConnection() throws CouponException { 
		Connection givenConnection;
		while(connection.size()==0)
			try {
				this.wait();
			} catch (InterruptedException e) {
				throw new CouponException("problem with connecting to the database- seems like the system is busy.");
			}
		givenConnection=connection.get(0);
		connection.remove(0);
		return givenConnection;
	}
	/**
	 * a synchronized function that enables connection users (DBDAO classes) to return they're connection to the pool: 
	 * (needs to be synchronized so that connections won't get lost between the users and the pool- 
	 * there is a get function that does the opposite)
	 * @param connection
	 */
	public synchronized void returnConnection(Connection connection){
		this.connection.add(connection);
		if(this.connection.size()==1)
			this.notify();
	}
	/**
	 * this function closes all of the connections. used before shutting down the system. 
	 * @throws CouponException
	 */
	public void closeAllConnections() throws CouponException{
		while(connection.size()>0){
			try {
				connection.get(0).close();
			} catch (SQLException e) {
				throw new CouponException("problem with connecting to the database");
			}
			connection.remove(0);
		}
	}
}