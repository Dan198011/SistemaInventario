package com.empresa.filter;

import com.empresa.model.Usuario;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {
    public void init(FilterConfig filterConfig) {}
    public void destroy() {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String path = req.getRequestURI().substring(req.getContextPath().length());

        // rutas públicas
        if (path.startsWith("/login") || path.startsWith("/css") || path.startsWith("/js") || path.startsWith("/images") || path.equals("/")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        Usuario u = null;
        if (session != null) u = (Usuario) session.getAttribute("usuario");

        if (u == null) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // ejemplo: solo ADMIN puede acceder a /reporte
        if (path.startsWith("/reporte") && !"ADMIN".equals(u.getRol())) {
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "Acceso denegado");
            return;
        }

        chain.doFilter(request, response);
    }
}
