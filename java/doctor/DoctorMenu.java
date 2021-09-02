package doctor;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DoctorMenu
 */
@WebServlet("/DoctorMenu")
public class DoctorMenu extends HttpServlet {
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
			response.sendRedirect("addOrder.html");
		}
		else if(val.equals("2")) {
			response.sendRedirect("updateResult.html");			
		}
		else if(val.equals("3")) {
			response.sendRedirect("addToWaitingList.html");
		}	
		else if(val.equals("4")) {
			response.sendRedirect("removeFromWaitingList.html");			
		}
		else if(val.equals("5")) {
			try {
				makeConnect();
				String query = "select * from surgicalWaitingList;";
				PreparedStatement ps = con.prepareStatement(query);
				ResultSet rs = ps.executeQuery();
				int i=1;
				pw.println("<html><body align=\"center\" bgcolor=\"f0f0f0\"><h1>Surgical Diagnostic Test Center</h1><br><br>");
				pw.println("<br><h2 align=\"center\">Patients in surgical waiting list</h2><hr><br>");			
				while(rs.next()) {
					pw.println("<br><b>Patient No. " + i++ + "</b>");
					pw.println("<br>Patient Name: " + rs.getString(1));
					pw.println("<br>Type of Operation: " + rs.getString(2));
					pw.println("<br>Priority: " + rs.getString(3));
					pw.println("<br>Surgeon Name: " + rs.getString(4));
					pw.println("<br>Placement Date: " + rs.getString(5) + "<hr><br>");
				}
			}
			catch(Exception e) {	
				pw.println("<br><br><p style=\"color:red;\">Error: Record could not be displayed</p>");			
			}
			finally {
				try {
					con.close();
					pw.println("<br><br><a href='doctorMenu.html'>Return Home</a></div></body></html>");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}			
		}
		else if(val.equals("6")) {
			response.sendRedirect("addReferral.html");		
		}
		else if(val.equals("7")) {
			response.sendRedirect("removeReferral.html");		
		}
		else if(val.equals("8")) {
			response.sendRedirect("updateRefDate.html");	
		}
		else {
			pw.println("Invalid Choice!!!");
			pw.print("<a href='index.html'>Go Home</a>");
		}
		pw.close();  	
	}

}
