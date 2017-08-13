package in.api;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenApp extends HttpServlet {

	static TokenApp me;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ServletConfig config;
	
	public static TokenApp getMe(){
		return me;
	}

	public void init(ServletConfig config) throws ServletException {
		this.config = config;
		if(me == null)me = this;
	}
	
	public boolean check(String u, String k){
		String k2 = (String) config.getServletContext().getAttribute("USR_" + u);
		if (k != null && k.equals(k2)) {
			return true;			
		}
		return false;
	}
	
	public String create(String u ){
		String k = UUID.randomUUID().toString();
		config.getServletContext().setAttribute("USR_" + u, k);
		return k;
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) {

		String action = req.getParameter("action");
		String u = req.getParameter("usr");
		String k = req.getParameter("key");
		System.out.println("req.getParameter u " + u + ", k " + k + ". action " + action + ".");
		PrintWriter out = null;
		// ServletOutputStream out = null;
		try {
			out = res.getWriter();
			res.setContentType("text/plain");

			// out = res.getOutputStream();
			if ("c".equals(action)) {
				k = UUID.randomUUID().toString();
				config.getServletContext().setAttribute("USR_" + u, k);
				out.print(k);

			} else if ("v".equals(action)) {

				String k2 = (String) config.getServletContext().getAttribute("USR_" + u);
				System.out.println( " k2 " + k2 + ".");
				if (k != null && k.equals(k2)) {

					out.print("ok");
				} else {
					out.print("no");

				}
			} else if ("data".equals(action)) {
				k = req.getHeader("auth");
				u = req.getHeader("user");
				System.out.println("header u " + u + ", k " + k + ".");
				String k2 = (String) config.getServletContext().getAttribute("USR_" + u);
				System.out.println( " k2 " + k2 + ".");
				if (k != null && k.equals(k2)) {

					out.print("ok");
				} else {
					out.print("no");

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				out.close();
			} catch (Exception e2) {

			}
		}
	}

	void print(OutputStream out, String s) throws Exception {
		byte[] b = s.getBytes("UTF-8");
		out.write(b);
	}

}
