package org.example;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.ZoneId;

@WebServlet(value = "/time")
public class TimezoneValidateFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String timeZone = req.getParameter("timezone");

        if (timeZone != null || !timeZone.isEmpty()) {
            try {
                ZoneId.of(timeZone);
            }catch (Exception e) {
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                res.getWriter().write("<html><body>");
                res.getWriter().write("<h1>Invalid Timezone</h1>");
                res.getWriter().write("</body></html>");
                return;
            }
        }
        chain.doFilter(req, res);
    }
}
