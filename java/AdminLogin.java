

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AdminLogin
 */
@WebServlet("/AdminLogin")
public class AdminLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con = null;
	
	void makeConnect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaDB","javaUser","pass");
			System.out.println("Connection established successfully!");
		}
		catch(Exception e) {
			System.out.println("Error: Connection Failed!!!");
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		
		String admin_name = request.getParameter("username");
		String admin_pass = request.getParameter("password");
		
		pw.println("<html><body align=\"center\" bgcolor=\"FFCCCC\"><h1>Surgical Diagnostic Test Center</h1><br><br>");
		
		try {
			makeConnect();
			String query = "select * from login where user=? and pass=? and admin=?;";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, admin_name);
			ps.setString(2, admin_pass);
			ps.setString(3, "true");
			ResultSet rs=ps.executeQuery();  
			if(rs.next()) {
				response.sendRedirect("adminMenu.html");			
			}
			else {
				pw.println("<div align=\"center\" style=\"width:auto;\"><br><h2>Wrong username and password!!!</h2>");
				pw.println("<br><br><a href='index.html'>Return Home</a></div></div></body></html>");							
			}
		}
		catch(Exception e) {	
			pw.println("<br><br>Error: Login Error");			
		}
		finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
		
		pw.close();  		   
	}

}
