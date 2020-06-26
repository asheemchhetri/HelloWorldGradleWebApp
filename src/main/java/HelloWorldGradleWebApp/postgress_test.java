package HelloWorldGradleWebApp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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
//			conn = DriverManager.getConnection(local_url, local_username, local_password);
			conn = DriverManager.getConnection(azure_url, azure_username, azure_password);
			System.out.println(conn);
			conn.setAutoCommit(true);
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
		
		// Get all column in table
		ResultSet rsCount;
		int columnCount = 0;
		try {
			rsCount = stmt.executeQuery("SELECT * FROM account");
			ResultSetMetaData rsmd = rsCount.getMetaData();
			columnCount = rsmd.getColumnCount();
			rsCount.close();
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		System.out.println("Number of columns are: " + columnCount);
		
		// Add new column
		int countCheck = 4;
		if (columnCount <= countCheck) {
			
			try {
				
//				PreparedStatement alterQueryString = conn.prepareStatement("ALTER TABLE account ADD COLUMN column_5 VARCHAR(50);");
						
				// For non-SELECT statements we need to use executeUpdate not executeQuery
				
				int number_of_rows_Affected = stmt.executeUpdate("ALTER TABLE account ADD COLUMN column_5 VARCHAR(50)");
				System.out.println("ALTER TABLE account ADD COLUMN column_5 VARCHAR(50);" + "\nAdded new column to table <Account> ==>" + number_of_rows_Affected);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		
		
		
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
		
		try {
			conn.close();
			System.out.println("Connection is closed...");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
