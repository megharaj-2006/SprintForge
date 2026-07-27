package org.SprintForge.modules.auth.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.io.IOException;
import java.util.*;

public class UserHeaderFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest httpRequest) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal principal) {
                Long userId = principal.getUser().getId();
                HttpServletRequest wrappedRequest = new HeaderRequestWrapper(httpRequest, userId);
                chain.doFilter(wrappedRequest, response);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    private static class HeaderRequestWrapper extends HttpServletRequestWrapper {
        private final String userIdStr;

        public HeaderRequestWrapper(HttpServletRequest request, Long userId) {
            super(request);
            this.userIdStr = String.valueOf(userId);
        }

        @Override
        public String getHeader(String name) {
            if ("X-User-Id".equalsIgnoreCase(name) || "X-Actor-ID".equalsIgnoreCase(name)) {
                return userIdStr;
            }
            return super.getHeader(name);
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            if ("X-User-Id".equalsIgnoreCase(name) || "X-Actor-ID".equalsIgnoreCase(name)) {
                return Collections.enumeration(Collections.singletonList(userIdStr));
            }
            return super.getHeaders(name);
        }

        @Override
        public Enumeration<String> getHeaderNames() {
            Set<String> names = new HashSet<>();
            Enumeration<String> originalNames = super.getHeaderNames();
            while (originalNames.hasMoreElements()) {
                names.add(originalNames.nextElement());
            }
            names.add("X-User-Id");
            names.add("X-Actor-ID");
            return Collections.enumeration(names);
        }
    }
}
