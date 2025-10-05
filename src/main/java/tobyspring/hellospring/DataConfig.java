package tobyspring.hellospring;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class DataConfig {

    // Datasource를 직접 Spring Bean으로 등록합니다.
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
    }

    // EntityManagerFactory를 직접 Spring Bean으로 등록합니다.
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource());
        emf.setPackagesToScan("tobyspring.hellospring");
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter() { // Hibernate에게 유용한 설정을 한다.
            // Double-Brace 사용으로, HibernateJpaVendorAdapter를 상속받는 서브 클래스를 생성하고 아래 메서드로 초기화한다.
            {
                setDatabase(Database.H2);
                setGenerateDdl(true);
                setShowSql(true);
            }
        });

        return emf;
    }
}
