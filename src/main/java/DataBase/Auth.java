package DataBase;

import model.User;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Stateless //Вся бизнес логика про авторизацию
public class Auth {
    @EJB
    private UserDB userDB;

    public String register(String login, String password) { //Регситрация
       /* User user = userDB.findUser(login);
        if (user != null) { //Проверка на существующего пользователя
            return "false";
        }
        user = userDB.createUser(login, password);
        if (user == null) {
            return "false";
        }
        return "true";*/
        return "";
    }

    public String login(String login, String password) { //Вход - передача логина и пароля
        return userDB.findUser(login);
        //User user = userDB.findUser(login);
        /*if (user == null) {
            return "false;-2";
        }
        if (userDB.checkPassword(user, password)) {
            user.generateAccessToken();
            userDB.saveUser(user);
            //Кодирование логина и пароля
            return "true;" + Base64.getEncoder().encodeToString((login + (char) (31) + user.getAccessToken()).getBytes(StandardCharsets.UTF_8));
        }
        return "false;-1";*/
    }

    public User getUserByToken(String data){ //Пооск пользователя по переданным данным дальнейшего взятия точек
        /*String token = new String(Base64.getDecoder().decode(data), StandardCharsets.UTF_8);
        String[] split = token.split(String.valueOf((char) (31)));
        String username = split[0];
        return userDB.findUser(username);*/
        return null;
    }
}
