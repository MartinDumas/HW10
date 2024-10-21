package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        String timeZone = req.getParameter("timeZone");
        if (timeZone == null || timeZone.isEmpty()) {
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("lastTimezone".equals(cookie.getName())) {
                        timeZone = cookie.getValue();
                        break;
                    }
                }
            }
        }

        ZoneId zoneId = (timeZone != null && !timeZone.isEmpty()) ? ZoneId.of(timeZone) : ZoneId.of("UTC");
        ZonedDateTime now = ZonedDateTime.now(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentTime = now.format(formatter);

        Cookie cookie = new Cookie("lastTimezone", zoneId.toString());
        resp.addCookie(cookie);

        req.setAttribute("currentTime", currentTime);
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
