package doctor;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UpdateReferral
 */
@WebServlet("/UpdateReferral")
public class UpdateReferral extends HttpServlet {
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
		makeConnect();
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		String pname = request.getParameter("pname");
		String appoint_date = request.getParameter("appoint_date");

		pw.println("<html><body align=\"center\" bgcolor=\"f0f0f0\"><h1>Surgical Diagnostic Test Center</h1><br><hr><br>");
		
		try {
			String query = "update referral set appoint_date=? where pname=?;";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, appoint_date);
			ps.setString(2, pname);
			int rows = ps.executeUpdate();
			if(rows > 0) {
				pw.println("<br><br><div style=\"margin:20px;\"><p style=\"color:green;\">Patient Name: " + pname + "<br>Updated appointment date: " + appoint_date);
				pw.println("<br>Record updated successfully!");								
			}
			else {
				pw.println("<br>Patient Name: " + pname + " <br>Appointment Date: " + appoint_date);
				pw.println("<br><br><p>No records to update!!!</p>");
			}
		}
		catch(Exception e) {
			pw.println("<br><br><p style=\"color:red;\">Error: Record could not be updated!</p>");	
		}
		finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		pw.println("<br><br><a href='doctorMenu.html'>Return Home</a></div></body></html>");
	}

}
