package moviebuddy.domain;

public interface MovieRepository {
    Movie save(Movie entity);

    Movie findByDirector(String director);
}
