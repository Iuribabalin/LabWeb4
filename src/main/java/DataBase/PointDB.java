package DataBase;

import model.Point;
import model.User;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import java.util.List;

@Stateful //Методы управления точками
public class PointDB {
    private final EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
    private final EntityManager em = factory.createEntityManager();

    public Point createPoint(Double x, Double y, Double r, User user) { //Создание точки

        try {
            final Point point = new Point();
            point.setOwner(user);
            point.setR(r);
            point.setX(x);
            point.setY(y);
            point.check(); //Проверка на зону
            em.getTransaction().begin();
            em.persist(point);
            em.getTransaction().commit();

            return point;
        } catch (PersistenceException e) {
            return null;
        }
    }

    public boolean saveEntry(Point point) { //Обновление сведений о точке
        try {
            em.getTransaction().begin();
            em.persist(point);
            em.getTransaction().commit();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public List findPoint(User owner) { //Поиск точек у пользователя
        return em.createQuery("from Point p where p.owner = :owner ")
                .setParameter("owner", owner).getResultList();
    }
}
