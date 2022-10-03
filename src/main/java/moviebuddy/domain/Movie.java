package moviebuddy.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Setter(AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;       // 영화 제목
    private String director;    // 영화 감독

    protected Movie() {}

    public Movie(String title, String director) {
        this.title = title;
        this.director = director;
    }
}
