//package moviebuddy.data;
//
//import moviebuddy.domain.Movie;
//import moviebuddy.domain.MovieRepository;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.TypedQuery;
//import java.util.Objects;
//
//@Repository
//public class JpaRepository implements MovieRepository {
//
//    private EntityManager entityManager;
//
//    @Override
//    @Transactional
//    public Movie save(Movie entity) {
//        if (Objects.nonNull(entity.getId())) {
//            // 갱신
//            return entityManager.merge(entity);
//        }
//
//        //저장
//        entityManager.persist(entity);
//        return entity;
//    }
//
//    @Override
//    public Movie findByDirector(String director) {
//        String jpql = "SELECT m FROM Movie m WHERE m.director = :director";
//        var query = entityManager.createQuery(jpql, Movie.class);
//        query.setParameter("director", director);
//
//        return query.getSingleResult();
//    }
//
//    @PersistenceContext
//    public void setEntityManager(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }
//}
