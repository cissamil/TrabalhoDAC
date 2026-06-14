package br.ufpr.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
  // ATENÇÃO: Aponte para o pacote onde estão os seus repositórios de LEITURA
  basePackages = {"br.ufpr.dataprovider.client.query"},
  entityManagerFactoryRef = "queryEntityManagerFactory",
  transactionManagerRef = "queryTransactionManager"
)
public class QueryDBConfig {

  private final String QUERY_DATASOURCE_PROPERTIES = "queryDataSourceProperties";
  private final String QUERY_ENTITY_MANAGER_FACTORY = "queryEntityManagerFactory";
  private final String QUERY_DATASOURCE = "queryDataSource";


  @Bean(name = QUERY_DATASOURCE_PROPERTIES)
  @ConfigurationProperties("spring.datasource.query")
  public DataSourceProperties queryDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean(name = QUERY_DATASOURCE)
  public DataSource queryDataSource(@Qualifier(QUERY_DATASOURCE_PROPERTIES) DataSourceProperties properties) {
    return properties.initializeDataSourceBuilder().build();
  }

  @Bean(name = QUERY_ENTITY_MANAGER_FACTORY)
  public LocalContainerEntityManagerFactoryBean queryEntityManagerFactory(
    EntityManagerFactoryBuilder builder,
    @Qualifier(QUERY_DATASOURCE) DataSource queryDataSource) {
    return builder
      .dataSource(queryDataSource)
      .packages("br.ufpr.dataprovider.adapter.domain.query")
      .persistenceUnit("query")
      .build();
  }

  @Bean(name = "queryTransactionManager")
  public PlatformTransactionManager queryTransactionManager(
    @Qualifier(QUERY_ENTITY_MANAGER_FACTORY) EntityManagerFactory queryEntityManagerFactory) {
    return new JpaTransactionManager(queryEntityManagerFactory);
  }

  @Bean
  public CommandLineRunner initQueryDatabase(@Qualifier(QUERY_DATASOURCE) DataSource queryDataSource) {
    return args -> {
      ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
      populator.addScript(new ClassPathResource("data-query.sql"));
      populator.execute(queryDataSource);
      System.out.println("Banco de Leitura (Query) populado com sucesso!");
    };
  }
}
