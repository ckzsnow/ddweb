package com.ddcb.common;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

public class SessionFilter extends OncePerRequestFilter {

	/*private static final Logger logger = LoggerFactory
			.getLogger(SessionFilter.class);*/
	
	private String[] filterUrls;

	public SessionFilter() {
		filterUrls = new String[] { "/view/webview/admin_index.html", "/view/webview/class_list.html", "/view/webview/user_forward_audit.html"};
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String uri = request.getRequestURI();
		boolean doFilter = false;
		for (String url : filterUrls) {
			if (url.equals(uri)) {
				doFilter = true;
				break;
			}
		}
		if (doFilter) {
			String userId = (String)request.getSession().getAttribute("user_id");
			if (null == userId || userId.isEmpty()) {
				logger.debug("user id is null.");
				boolean isAjaxRequest = isAjaxRequest(request);
				if (isAjaxRequest) {
					response.setCharacterEncoding("UTF-8");
					response.sendError(HttpStatus.UNAUTHORIZED.value(),
							"Unauthorized!");
					return;
				}
				response.sendRedirect("/getUserLoginHtml");
				return;
			}
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