package servlets;

import DataBase.Auth;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login") //Логин на сайт
public class AuthServlet extends HttpServlet{
    @EJB
    private Auth auth;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/login").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("name");
        String password = req.getParameter("pass");
        String result = auth.login(username, password);
        if (result.equals("No such user found") || result.equals("Not authorized")){
            resp.setStatus(403);
        }
        resp.getWriter().write(result);
    }
}
