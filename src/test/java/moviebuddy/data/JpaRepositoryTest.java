package moviebuddy.data;

import moviebuddy.MovieBuddyApplication;
import moviebuddy.domain.Movie;
import moviebuddy.domain.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(MovieBuddyApplication.class)
@Transactional
//@Sql("classpath:movies.sql")
class JpaRepositoryTest {

    @Autowired
    MovieRepository movieRepository;

    @Test
    void saveAndFindMovieByDirector() {
        var movie = new Movie("고질라", "아무개");
        Assertions.assertNotNull(movieRepository.save(movie).getId());

        for (int i=0;i<10;i++)
            movieRepository.save(new Movie(String.valueOf(i), String.valueOf(i)));

        var result = movieRepository.findByDirector(movie.getDirector());
        Assertions.assertEquals(movie, result);
        Assertions.assertEquals(movie.getTitle(), result.getTitle());
    }

}