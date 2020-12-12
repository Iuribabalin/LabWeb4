package navigation;

import DataBase.Auth;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/reg")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class RegPath {
    @EJB
    private Auth auth;

    @POST
    public String checkReg(String json) throws JSONException {
        JSONObject object = new JSONObject(json);
        String username = object.optString("username");
        String password = object.optString("pass");

        String result = auth.register(username, password);

        if(result.equals("User already exists") || result.equals("Error")){
            return "403";
        }
        else return result;
    }
}

