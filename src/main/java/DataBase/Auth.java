package DataBase;

import model.Point;
import model.User;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

@Stateless //Вся бизнес логика про авторизацию
public class Auth {
    @EJB
    private UserDB userDB;

    public String register(String login, String password) { //Регситрация
        String user = userDB.ifExist(login);
        if (user.equals("false1") || user.equals("false2")) { //Проверка на существующего пользователя
            User user2 = userDB.createUser(login, password);
            if (user2 == null) {
                return "false";
            }
            else return "true";
        }
        return "false";

    }

    public String login(String login, String password) { //Вход - передача логина и пароля
        String user = userDB.ifExist(login);
        if (user.equals("false1") || user.equals("false2")){
            return "false;-2";
        }
        else if (userDB.checkPassword(login, password)) {
            //userDB.findUser(user).generateAccessToken();
            //userDB.saveUser(userDB.findUser(user));
            //Кодирование логина и пароля

            return "true;" + login;
        }
        return "false;-1";
    }

    public User getUserByToken(String login){ //Пооск пользователя по переданным данным дальнейшего взятия точек
        return userDB.findUser(login);
    }
}
