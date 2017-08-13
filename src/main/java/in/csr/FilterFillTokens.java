package in.csr;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FilterFillTokens implements Filter {
	public void doFilter(ServletRequest req1, ServletResponse resp1, FilterChain chain)
			throws IOException, ServletException {
		
		if(!(req1 instanceof HttpServletRequest)){
			chain.doFilter(req1, resp1);
			return;
		}
		HttpServletRequest req = (HttpServletRequest)req1;
		HttpServletResponse res = (HttpServletResponse)resp1;
		HttpSession ses = req.getSession(true);
		String token = (String) ses.getAttribute("api-token");
		String u = (String) ses.getAttribute("user");
		if(u == null) {
			u = "u1";
			ses.setAttribute("user", u);
		}
		if(token == null){			
			token = in.api.TokenApp.getMe().create(u);
			ses.setAttribute("api-token", token);
		}
		System.out.println("token " + token + ", u " + u);
		//PrintWriter out = res.getWriter();
		//out.print("filter invoked before ");

		chain.doFilter(req1, resp1);// sends request to next resource

		//out.print("filter is invoked after");
	}

	public void destroy() {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}
