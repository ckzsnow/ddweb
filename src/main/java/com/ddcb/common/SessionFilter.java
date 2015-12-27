package com.ddcb.common;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class SessionFilter extends OncePerRequestFilter {

	/*private static final Logger logger = LoggerFactory
			.getLogger(SessionFilter.class);*/
	
	private String[] filterUrls;

	public SessionFilter() {
		filterUrls = new String[] { "/admin/", "/company/", "/accounter/",
				"/companyUser/"};
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String uri = request.getRequestURI();
		boolean doFilter = false;
		if(uri.indexOf("/files/company") == -1) {
			for (String url : filterUrls) {
				if (uri.indexOf(url) != -1) {
					doFilter = true;
					break;
				}
			}
		}
		if (doFilter) {
			
		}
		filterChain.doFilter(request, response);
	}

	public static boolean isAjaxRequest(HttpServletRequest request) {
		String header = request.getHeader("X-Requested-With");
		if (header != null && "XMLHttpRequest".equals(header))
			return true;
		else
			return false;
	}

}