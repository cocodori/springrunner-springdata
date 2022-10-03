package moviebuddy.data;

import moviebuddy.MovieBuddyApplication;
import moviebuddy.domain.Movie;
import moviebuddy.domain.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(MovieBuddyApplication.class)
@Transactional
class JpaRepositoryTest {

    @Autowired
    MovieRepository movieRepository;

    @Test
    void saveAndFindMovieByDirector() {
        var movie = new Movie("고질라", "아무개");
        Assertions.assertNotNull(movieRepository.save(movie).getId());

        var result = movieRepository.findByDirector(movie.getDirector());
        Assertions.assertEquals(movie, result);
        Assertions.assertEquals(movie.getTitle(), result.getTitle());
    }

}