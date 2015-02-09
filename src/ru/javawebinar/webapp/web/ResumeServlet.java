package ru.javawebinar.webapp.web;

import ru.javawebinar.webapp.WebAppConfig;
import ru.javawebinar.webapp.model.Resume;
import ru.javawebinar.webapp.storage.IStorage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * GKislin
 * 09.02.2015.
 */
public class ResumeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
/*
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        Writer w = response.getWriter();
        String name = request.getParameter("name");
        w.write("Тест сервелет: " + name);
        w.close();
*/
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        Resume r;
        IStorage storage = WebAppConfig.get().getStorage();

        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("list");
                return;
            case "create":
                r = Resume.EMPTY;
                break;
            case "view":
            case "edit":
                r = storage.load(uuid);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}
