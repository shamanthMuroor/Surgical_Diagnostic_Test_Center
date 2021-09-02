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
 * Servlet implementation class DiagnosticTest
 */
@WebServlet("/DiagnosticTest")
public class DiagnosticTest extends HttpServlet {
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
		
		String testname = request.getParameter("testname");
		String pname = request.getParameter("patient");
		String sname = request.getParameter("surgeon");
		String dorder = request.getParameter("orderdate");
		pw.println("<html><body align=\"center\" bgcolor=\"f0f0f0\"><h1>Surgical Diagnostic Test Center</h1><br><hr><br>");
		pw.println("<div style=\"margin:20px;\">Test Name: " + testname + " <br>Patient Name: " + pname + "<br>Surgeon: " + sname + "<br>Order date: " + dorder);
		
		try {
			String result="-", dres=null;
			String query = "insert into diagnostic values (?,?,?,?,?,?);";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, testname);
			ps.setString(2, pname);
			ps.setString(3, sname);
			ps.setString(4, result);
			ps.setString(5, dorder);
			ps.setString(6, dres);
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
		makeConnect();
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		String testname = request.getParameter("testname");
		String pname = request.getParameter("patient");
		String res = request.getParameter("result");
		String resdate = request.getParameter("resdate");
		pw.println("<html><body align=\"center\" bgcolor=\"f0f0f0\"><h1>Surgical Diagnostic Test Center</h1><br><hr><br>");
		pw.println("<div style=\"margin:20px;\">Test Name: " + testname + " <br>Patient Name: " + pname + " <br>Result: " + res + "<br>Result Date: " + resdate);
		
		try {
			String query = "update diagnostic set result=?, dres=? where testname=? and pname=?;";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, res);
			ps.setString(2, resdate);
			ps.setString(3, testname);
			ps.setString(4, pname);
			int rows = ps.executeUpdate();
			if(rows > 0) {
				pw.println("<br><br><p style=\"color:green;\">" + rows + " results updated successfully!</p>");						
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
