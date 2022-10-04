package moviebuddy;


import moviebuddy.domain.Movie;
import moviebuddy.domain.MovieRepository;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.SharedEntityManagerBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ReflectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.util.Objects;

@Configuration
@ComponentScan
@EnableTransactionManagement
public class MovieBuddyApplication {

    // JPA로 영화 정보(제목, 감독)를 저장하고 조회하는 애플리케이션 만들기
    public static void main(String[] args) {

    }

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        var vendorAdaptor = new HibernateJpaVendorAdapter();
        vendorAdaptor.setDatabase(Database.H2);
        vendorAdaptor.setGenerateDdl(true);
        vendorAdaptor.setShowSql(true);

        var factory = new LocalContainerEntityManagerFactoryBean();

        factory.setJpaVendorAdapter(vendorAdaptor);
        factory.setPackagesToScan(getClass().getPackageName());
        factory.setDataSource(dataSource);

        return factory;
    }

    // 1. ProxyFactoryBean 을 이용해 MovieRepository 프록시를 빈으로 등록
    // 2. 등록된 MovieRepository 프록시는 이미 잘 구현된 MyJpaRepository 를 사용해서 동작하게 한다.
//    @Bean
//    public ProxyFactoryBean movieRepository(EntityManagerFactory entityManagerFactory) {
//        var sharedEntityManagerBean = new SharedEntityManagerBean();
//        sharedEntityManagerBean.setEntityManagerFactory(entityManagerFactory);
//        sharedEntityManagerBean.afterPropertiesSet();
//
//        var myJpaRepository = new MyJpaRepository(sharedEntityManagerBean.getObject());
//
//        ProxyFactoryBean proxyFactory = new ProxyFactoryBean();
//        proxyFactory.setTarget(myJpaRepository);
//        proxyFactory.setInterfaces(MovieRepository.class);
//        proxyFactory.addAdvice((MethodInterceptor) invocation -> {
//            // invocation.getThis() -> target
//            // invocation.getMethod() -> 호출된 프록시 메소드
//            // invocation.getArguments() -> 호출된 프록시 메소드로 넘겨받은 매개변수
//
//            Object target = invocation.getThis();
//            Method method = ReflectionUtils.findMethod(target.getClass(), invocation.getMethod().getName(), invocation.getMethod().getParameterTypes());
//
//            return method.invoke(target, invocation.getArguments());
//        });
//
//        return proxyFactory;
//    }

    @Bean
    public JpaRepositoryFactoryBean<MovieRepository, Movie, Long> movieRepository() {
        return new JpaRepositoryFactoryBean<>(MovieRepository.class);
    }

    static class MyJpaRepository {
        private EntityManager entityManager;

        public MyJpaRepository(EntityManager entityManager) {
            this.entityManager = entityManager;
        }

        public Movie save(Movie entity) {
            if (Objects.nonNull(entity.getId()))
                return entityManager.merge(entity);

            entityManager.persist(entity);
            return entity;
        }

        public Movie findByDirector(String director) {
            String jpql = "SELECT m FROM Movie m WHERE m.director = :director";
            var query = entityManager.createQuery(jpql, Movie.class);
            query.setParameter("director", director);
            return query.getSingleResult();
        }
    }
}
