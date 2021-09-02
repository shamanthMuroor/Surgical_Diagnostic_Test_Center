

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
 * Servlet implementation class menu
 */
@WebServlet("/menu")
public class menu extends HttpServlet {
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
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		String val = request.getParameter("choice");
		if(val.equals("1")) {
			response.sendRedirect("addPatient.html");
		}
		else if(val.equals("2")) {
			response.sendRedirect("removePatient.html");			
		}
		else if(val.equals("3")) {
			try {
				makeConnect();
				String query = "select * from patient;";
				PreparedStatement ps = con.prepareStatement(query);
				ResultSet rs = ps.executeQuery();
				int i=1;
				pw.println("<br><h2 align=\"center\">All Patient Records</h2><hr>");
				while(rs.next()) {
					pw.println("<br><b>Patient No." + i++ + "</b>");
					pw.println("<br>Name: " + rs.getString(1));
					pw.println("<br>Date of Birth: " + rs.getString(2));
					pw.println("<br>Gender: " + rs.getString(3) + "<hr><br>");
				}
			}
			catch(Exception e) {	
				pw.println("\n\nError: Record could not be displayed");			
			}
			finally {
				try {
					con.close();
					pw.println("<br><br><a href='adminMenu.html'>Go Home</a>");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		else if(val.equals("4")) {
			response.sendRedirect("addSurgeon.html");			
		}
		else if(val.equals("5")) {
			response.sendRedirect("removeSurgeon.html");			
		}
		else if(val.equals("6")) {
			try {
				makeConnect();
				String query = "select * from surgeon;";
				PreparedStatement ps = con.prepareStatement(query);
				ResultSet rs = ps.executeQuery();
				int i=1;
				pw.println("<html><body align=\"center\" bgcolor=\"f0f0f0\"><h1>Surgical Diagnostic Test Center</h1><br><br>");
				pw.println("<h2 align=\"center\">Surgeons Available</h2><hr><br>");			
				while(rs.next()) {
					pw.println("<br><b>Surgeon No." + i++ + "</b>");
					pw.println("<br>Name: " + rs.getString(1));
					pw.println("<br>Speciality: " + rs.getString(2) + "<hr><br>");
				}
			}
			catch(Exception e) {	
				pw.println("<br><br><p style=\"color:red;\">Error: Record could not be displayed</p>");			
			}
			finally {
				try {
					con.close();
					pw.println("<br><br><a href='adminMenu.html'>Return Home</a></div></body></html>");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		else {
			pw.println("<br>Invalid Choice!!!");
			pw.print("<br><a href='index.html'>Return Home</a>");
		}
		pw.close();  	
	}

}
