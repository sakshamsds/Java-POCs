package http.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestHeaderInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("Inside preHandle method");

		if (StringUtils.isEmpty(request.getHeader("student-auth-key"))) {
			System.out.println("failing");
			return false;
		}
		System.out.println("passing");
		return true;
	}
}