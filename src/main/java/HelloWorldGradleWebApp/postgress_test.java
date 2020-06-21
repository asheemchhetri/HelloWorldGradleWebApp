package HelloWorldGradleWebApp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.math3.random.ISAACRandom;

import jdk.jfr.consumer.RecordedStackTrace;

@WebServlet("/FirstSql")

public class postgress_test extends HttpServlet{
	public static Statement stmt;
	private static Connection conn;
	
	/**
	* Constructor creates a live connection ready for use
	 * @return connection
	*/
	static {
		try {

			Class.forName("org.postgresql.Driver");
			String azure_url = "jdbc:postgresql://asheem-postgres-test.postgres.database.azure.com:5432/user";
			String azure_username = "postgres@asheem-postgres-test";
			String azure_password = "SET56?ra";
			
			String local_url = "jdbc:postgresql://localhost:5432/user";
			String local_username = "postgres";
			String local_password = "SET56?ra";
			conn = DriverManager.getConnection(azure_url, azure_username, azure_password);
			System.out.println(conn);
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		List<Map> list = new ArrayList<Map>();
		Map map = new HashMap();
		
		//Fetch the input parameter from user.jsp
		String first_name_from_request = request.getParameter("f_name");
		// Capitalize the first character
		first_name_from_request = first_name_from_request.substring(0, 1).toUpperCase() + first_name_from_request.substring(1);
		System.out.println(first_name_from_request);
		
		String query = "SELECT account.first_name, account.last_name, account.email FROM account WHERE account.first_name = '" + first_name_from_request + "'";
		
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String first_name = rs.getString("first_name");
				String last_name = rs.getString("last_name");
				String email = rs.getString("email");

				map.put("first_name", first_name);
				map.put("last_name", last_name);
				map.put("email", email);
				System.out.println("First Name = " + first_name);
				System.out.println("Last Name = " + last_name);
				System.out.println("Email = " + email);
				list.add(map);

				for (Map m : list) {
					System.out.println(m);
				}
			}

			if (map.size() <= 0) {
				request.setAttribute("user_not_found", first_name_from_request + " is not present in our database.");// Put list collection data into request for sharing
				request.getRequestDispatcher("/hello.jsp").forward(request, response);
				return;
			}
			rs.close();
//			stmt.close();
		}

		catch (Exception e) {
			System.out.println(query);
			System.out.println("Exception in DataConnection get ResultSet.");
			e.printStackTrace();
		}

		System.out.println("Jump to hello.jsp");
		request.setAttribute("key_list", list);// Put list collection data into request for sharing
		request.getRequestDispatcher("/hello.jsp").forward(request, response);
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	/**
//	* Method kills the open statement & connection object
//	*/
////	public static void killOpenObjects()	{
////		try	{
////			if (stmt != null)	 {
////				stmt.close();
////				}
////			if (conn != null)	 {
////				conn.close();
////				}
////			}
////		catch (java.sql.SQLException e)	 {
////			System.out.println("Exception in killOpenObjects");
////			e.printStackTrace();
////			}
////		}
//
//	/**
//	* @param query A String containing the query to be executed
//	* @param killTrigger A boolean that signifys whether to call the killOpenObjects or leave the connection live
//	 * @return 
//	* @returns rs A java.sql.ResultSet containing the required data
//	* It should be noted that the connection may be killed even thought the resultset is still to be used in MySQL ONLY
//	*/
//	public void service (HttpServletRequest request, HttpServletResponse response) //
//			throws ServletException, IOException{
//		Connection c = DataConnection();
//		Statement stmt = null;
//		ResultSet rs = null;
//		String query = "SELECT * FROM account;";
//		List <Map> list = new ArrayList<Map>();
//		try	{
//			if (null != conn) {
//				stmt = conn.createStatement();
//			}
//				rs = stmt.executeQuery(query);
//				while ( rs.next() ) {
//		            int id = rs.getInt("user_id");
//		            String  first_name = rs.getString("first_name");
//		            String  last_name = rs.getString("last_name");
//		            String  email = rs.getString("email");
//		            
//		            Map map = new HashMap();
//		            map.put("first_name", first_name);
//		            map.put("last_name", last_name);
//		            map.put("email", email);
////		            System.out.println( "First Name = " + first_name );
////		            System.out.println( "Last Name = " + last_name );
////		            System.out.println( "Email = " + email );
//		            System.out.println(map);
//		            
//		            for (Map m: list) {
//		            	System.out.println(m);
//		            }
//			}
//		}
//		
//		catch (Exception e)	{
//			System.out.println(query);
//			System.out.println("Exception in DataConnection get ResultSet");
//			e.printStackTrace();
//			}
//		
////		finally	{
////			if (killTrigger)	{
////				killOpenObjects();
////				}
////			}
////		return rs;
//		System.out.println("Jump");
//		request.setAttribute("key_list",list);//Put list collection data into request for sharing
//		request.getRequestDispatcher("/hello.jsp").forward(request, response);
//		}
//
//	/**
//	* @param query A String containing the query to be executed
//	* @param killTrigger A boolean that signifys whether to call the killOpenObjects or leave the connection live
//	 * @throws IOException 
//	 * @throws ServletException 
//	* @returns rows An integer signifying how many rows were effected by the query
//	* It should be noted that the connection may be killed even thought the resultset is still to be used in MySQL ONLY
//	*/
////	public int updateTable(String query, boolean killTrigger)	{
////		int rows = 0;
////		try	{
////			rows = stmt.executeUpdate(query);
////			}
////		catch (java.sql.SQLException e)	{
////			if (e.getMessage().indexOf("Invalid argument value: Duplicate entry") == -1)	 {
////				System.out.println(e.getMessage());
////				System.out.println(query);
////				}
////			}
////		finally	{
////			if (killTrigger)	{
////				killOpenObjects();
////				}
////			}
////		return rows;
////		}
//	
//	
//	
//	
//	
//	
////   public static void main( String args[] ) throws ServletException, IOException {
////      Connection c = null;
////      Statement stmt = null;
////      try {
////         Class.forName("org.postgresql.Driver");
////         c = DriverManager
////            .getConnection("jdbc:postgresql://localhost:5432/user",
////            "postgres", "SET56?ra");
////         c.setAutoCommit(false);
////         System.out.println("Opened database successfully");
////
////         stmt = c.createStatement();
////         ResultSet rs = stmt.executeQuery( "SELECT * FROM account;" );
////         while ( rs.next() ) {
////            int id = rs.getInt("user_id");
////            String  first_name = rs.getString("first_name");
////            String  last_name = rs.getString("last_name");
////            String  email = rs.getString("email");
//////            System.out.println( "ID = " + id );
////            System.out.println( "First Name = " + first_name );
////            System.out.println( "Last Name = " + last_name );
////            System.out.println( "Email = " + email );
////            System.out.println();
////         }
//////         rs.close();
////         stmt.close();
////         c.close();
////      } catch ( Exception e ) {
////         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
////         System.exit(0);
////      }
////      System.out.println("Operation done successfully");
////	   Connection c = DataConnection();
////	   HttpServletRequest req = null;
////	   HttpServletResponse resp = null;
////	   service(req, resp, "SELECT * FROM account;", c, false);
////   }
}
