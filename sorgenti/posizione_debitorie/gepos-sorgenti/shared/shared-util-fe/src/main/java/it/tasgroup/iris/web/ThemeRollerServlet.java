package it.tasgroup.iris.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ThemeRollerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] themes = {"paytas", "tuscany"};
        HttpSession session = request.getSession();
        ServletContext application = getServletContext();
        String themeName = request.getParameter("themeName");
        String scope = request.getParameter("scope");
        scope = scope != null ? scope : "session";
        Theme t = new Theme(themeName);
        if (themeName != null) {
            if ("session".equals(scope)) {
                session.setAttribute(Theme.DEFAULT_ATTRIBUTE_NAME, t);
            } else if ("request".equals(scope)) {
                request.setAttribute(Theme.DEFAULT_ATTRIBUTE_NAME, t);
            } else if ("application".equals(scope)) {
                application.setAttribute(Theme.DEFAULT_ATTRIBUTE_NAME, t);
            }
        } else {
            Theme defaultTheme = (Theme) application.getAttribute(Theme.DEFAULT_ATTRIBUTE_NAME);
            themeName = defaultTheme.getCode("name");
            scope = "application";
        }

        request.setAttribute("themeName",themeName);
        request.setAttribute("scope", scope);

        String forwardUrl = request.getParameter("forwardUrl");

        if (forwardUrl == null || "ajax".equalsIgnoreCase(forwardUrl)) {
            response.getOutputStream().println(themeName); // write themename
        }   else {
            RequestDispatcher dispatcher = request.getRequestDispatcher(forwardUrl);
            dispatcher.forward( request, response );
        }
    }
}
