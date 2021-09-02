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
 * Servlet implementation class SurgicalWaiting
 */
@WebServlet("/SurgicalWaiting")
public class SurgicalWaiting extends HttpServlet {
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		makeConnect();
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		String pname = request.getParameter("pname");
		String type = request.getParameter("type");
		String priority = request.getParameter("priority");
		String sname = request.getParameter("sname");
		String place_date = request.getParameter("place_date");
		String removal_date = null;

		pw.println("<html><body align=\"center\" bgcolor=\"f0f0f0\"><h1>Surgical Diagnostic Test Center</h1><br><hr><br>");
		pw.println("<div style=\"margin:20px;\">Patient Name: " + pname + " <br>Operation Type: " + type + "<br>Priority: " + priority + "<br>Surgeon Name: " + sname);
		pw.println("<br>Placement date: " + place_date);
		
		try {
			String query = "insert into surgicalWaitingList values (?,?,?,?,?,?);";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, pname);
			ps.setString(2, type);
			ps.setString(3, priority);
			ps.setString(4, sname);
			ps.setString(5, place_date);
			ps.setString(6, removal_date);
			ps.execute();
			pw.println("<br><br><p style=\"color:green;\">Record inserted successfully!</p>");
		}
		catch(Exception e) {
			pw.println("<br><br><p style=\"color:red;\">Error: Record could not be inserted!</p>");			
		}
		finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		pw.print("<br><br><a href='doctorMenu.html'>Return Home</a></div></body></html>");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		String pname = request.getParameter("pname");
		String removal_date = request.getParameter("removal_date");

		pw.println("<html><body align=\"center\" bgcolor=\"f0f0f0\"><h1>Surgical Diagnostic Test Center</h1><br><hr><br>");
		
		try {
			makeConnect();
			String query = "delete from surgicalWaitingList where pname=?;";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, pname);
			int rows = ps.executeUpdate();
			if(rows > 0) {
				pw.println("<br><br><div style=\"margin:20px;\"><p style=\"color:green;\">Record deleted successfully!</p>");		
				pw.println("<br>Patient Name: " + pname + " removed on: " + removal_date + " successfully!!</p>");
			}
			else {
				pw.println("<br><br><p>No records to delete!!!</p>");
			}
		}
		catch(Exception e) {
			pw.println("<br><br><p style=\"color:red;\">Error: Record could not be deleted!</p>");	
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
