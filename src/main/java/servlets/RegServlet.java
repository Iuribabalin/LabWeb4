package servlets;

import DataBase.Auth;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/reg") //Регистрация на сайте
public class RegServlet extends HttpServlet{
    @EJB
    private Auth auth;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("name");
        String password = req.getParameter("pass");
        String result = auth.register(username, password);
        if(result.equals("User already exists") || result.equals("Error")){
            resp.setStatus(403);
        }
        resp.getWriter().write(result);
    }
}
