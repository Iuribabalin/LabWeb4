package servlets;

import DataBase.Auth;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import java.io.IOException;


@Singleton
@Path(value = "/login")//Логин на сайт
public class AuthServlet{
    @EJB
    private Auth auth;

    @Path("/login")
    @POST
    public void checkAuth(@FormParam("name") String username, @FormParam("pass") String password, @Context HttpServletResponse resp, @Context HttpServletRequest req){
        String result = auth.login(username, password);
        if (result.equals("No such user found") || result.equals("Not authorized")){
            resp.setStatus(403);
        }
        try {
            resp.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
