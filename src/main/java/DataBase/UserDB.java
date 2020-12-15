package DataBase;

import model.User;

import javax.ejb.*;
import javax.persistence.*;
import java.util.Base64;

@Stateful // Методы управленя пользователями
public class UserDB {
    private final EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
    private final EntityManager em = factory.createEntityManager();

    public User createUser(String login, String password) { //Создание пользователя
        try {

            final User user = new User();
            user.setLogin(login);
            //entity.setPassword(Base64.getEncoder().encodeToString((password).getBytes())); //Кодирование пароля
            user.setPassword(password);
            user.setLoginController("false");
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();

            return user;

        } catch (PersistenceException e) {
            return null;

        }
    }

    public boolean saveUser(User user) { //Обновление сведений о пользователе
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean checkPassword(String login, String password) { //Проверка пароля
        User user = findUser(login);

        //return user.getPassword().equals(new String(Base64.getDecoder().decode((password)))); //Декодирование
        return user.getPassword().equals(password);
    }

    public String ifExist(String login) { //Поиск существующего логина
        try {
            User user = (User) em.createQuery(" from User u where u.login = :login")
                    .setParameter("login", login).getSingleResult();
            if (user == null)
                return "false1";
            else
                return "true";
        } catch (NoResultException e){ return "false2"; }
    }

    public User findUser(String login) {
        User user = (User) em.createQuery(" from User u where u.login = :login")
                .setParameter("login", login).getSingleResult();
        return user;
    }
}
