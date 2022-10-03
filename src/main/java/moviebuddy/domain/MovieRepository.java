package moviebuddy.domain;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;

public interface MovieRepository extends PagingAndSortingRepository<Movie, Long> {

    Movie findByDirector(String director);
}
