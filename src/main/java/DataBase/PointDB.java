package DataBase;

import model.Point;
import model.User;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import java.util.List;

@Stateful //Методы управления точками
public class PointDB {
    private final EntityManager entityManager = Persistence.
            createEntityManagerFactory("default").
            createEntityManager();

    public Point createPoint(Double x, Double y, Double r, User user) { //Создание точки

        try {
            final Point entity = new Point();
            entity.setOwner(user);
            entity.setR(r);
            entity.setX(x);
            entity.setY(y);
            entity.check(); //Проверка на зону
            entityManager.persist(entity);
            entityManager.flush();

            return entity;
        } catch (PersistenceException e) {
            return null;
        }
    }

    public boolean saveEntry(Point point) { //Обновление сведений о точке
        try {
            entityManager.merge(point);
            entityManager.flush();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public List<Point> findPoint(User owner) { //Поиск точек у пользователя
        return entityManager.createQuery("from ENTRY where owner = :owner ", Point.class)
                .setParameter("owner", owner).getResultList();
    }
}
