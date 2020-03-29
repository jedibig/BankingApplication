import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.dto.User;

@WebServlet("/homeServlet")

public class HomeServlet extends GenericServlet {

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		PrintWriter writer = resp.getWriter();
		
		//getting the username and password
		User userSession = (User) req.getSession().getAttribute("signin");
		
		writer.println("<!DOCTYPE html>");
		writer.println("<html>");
		writer.println("<head>");
		writer.println("<title>Home Page</title></head>");
		writer.println("<body>");
		writer.println("<h2>Welcome " + userSession.getName() + "</h2>");
		writer.println("<p>Your account number is " + userSession.getAccNum() + "</p><br>");
		writer.println("<p>What would you like to do today ? </p>");
		writer.println("<form action='balance'><input type='submit' value='See Balance'/></form>");
		writer.println("</html>");
		
	}

	
}
