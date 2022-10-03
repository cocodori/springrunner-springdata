package moviebuddy;

import moviebuddy.domain.Movie;
import moviebuddy.domain.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkDynamicProxyTests {

    @Test
    void useDynamicProxy() {

        ClassLoader loader = JdkDynamicProxyTests.class.getClassLoader();
        Class<?>[] interfaces = new Class[] { MovieRepository.class };
        InvocationHandler handler = new MovieRepositoryHandler();

        MovieRepository proxy = (MovieRepository) Proxy.newProxyInstance(loader, interfaces, handler);

        Movie movie = new Movie("극한직업", "아무개");
        Assertions.assertEquals(movie.getTitle(), proxy.save(movie).getTitle());
        Assertions.assertEquals(movie.getDirector(), proxy.findByDirector(movie.getDirector()).getDirector());

    }

    static class MovieRepositoryHandler implements InvocationHandler {

        @Override
        public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
            String methodName = method.getName();

            if ("save".equals(methodName)) {
                return objects[0];
            } else if ("findByDirector".equals(methodName)) {
                return new Movie("", String.valueOf(objects[0]));
            }

            return null;
        }
    }
}
