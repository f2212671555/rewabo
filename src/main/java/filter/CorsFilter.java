package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "CorsFilter")
public class CorsFilter implements Filter {

  public void destroy() {
  }

  public void doFilter(ServletRequest req, ServletResponse resp,
      FilterChain chain) throws ServletException, IOException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) resp;
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader(
        "Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
    response.setHeader("Access-Control-Max-Age", "3600");
    response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization");
    response.setHeader("Access-Control-Allow-Credentials", "true");
    chain.doFilter(request, response);
  }

  public void init(FilterConfig config) throws ServletException {

  }

}
