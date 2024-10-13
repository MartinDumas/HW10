package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;

@WebServlet(value="/time")
public class TimeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        String timeZone = req.getParameter("timeZone");

        ZoneId zoneId = (timeZone != null && !timeZone.isEmpty()) ? ZoneId.of(timeZone) : ZoneOffset.UTC;

        ZonedDateTime now = ZonedDateTime.now(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String date = now.format(formatter);

        resp.getWriter().write("<html><body>");
        resp.getWriter().write("<h1>Current Time: " + date + "</h1>");
        resp.getWriter().write("</body></html>");

        resp.getWriter().close();
    }
}
