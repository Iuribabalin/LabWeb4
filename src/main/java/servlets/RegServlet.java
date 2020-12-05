package servlets;

import DataBase.Auth;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import java.io.IOException;

@Stateful
@SessionScoped
@Path("/reg") //Регистрация на сайте
public class RegServlet{
    @EJB
    private Auth auth;

    @POST
    @Path("/")
    public void regUserInSystem(@FormParam("name") String username, @FormParam("pass") String password, @Context HttpServletRequest req, @Context HttpServletResponse resp) throws IOException {
        String result = auth.register(username, password);
        if(result.equals("User already exists") || result.equals("Error")){
            resp.setStatus(403);
        }
        resp.getWriter().write(result);
    }
}
