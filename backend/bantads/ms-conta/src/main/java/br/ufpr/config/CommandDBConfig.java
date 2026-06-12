package br.ufpr.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
  basePackages = {"br.ufpr.dataprovider.client.command"},
  entityManagerFactoryRef = "commandEntityManagerFactory",
  transactionManagerRef = "commandTransactionManager"
)
public class CommandDBConfig {

    private final String COMMAND_ENTITY_MANAGER_FACTORY = "commandEntityManagerFactory";
    private final String COMMAND_DATASOURCE_PROPERTIES = "commandDataSourceProperties";
    private final String COMMAND_DATASOURCE = "commandDataSource";


    @Primary
    @Bean(name = COMMAND_DATASOURCE_PROPERTIES)
    @ConfigurationProperties("spring.datasource.command")
    public DataSourceProperties commandDataSourceProperties(){
      return new DataSourceProperties();
    }

    @Primary
    @Bean(name = COMMAND_DATASOURCE)
    public DataSource commandDataSource(@Qualifier(COMMAND_DATASOURCE_PROPERTIES) DataSourceProperties properties){
      return properties.initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean(name = COMMAND_ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean commandEntityManagerFactory(
      EntityManagerFactoryBuilder builder,
      @Qualifier(COMMAND_DATASOURCE) DataSource commandDataSource) {
      return builder
        .dataSource(commandDataSource)
        .packages("br.ufpr.dataprovider.adapter.domain.command")
        .persistenceUnit("command")
        .build();
    }

    @Primary
    @Bean(name = "commandTransactionManager")
    public PlatformTransactionManager commandTransactionManager(
      @Qualifier(COMMAND_ENTITY_MANAGER_FACTORY) EntityManagerFactory commandEntityManagerFactory) {
      return new JpaTransactionManager(commandEntityManagerFactory);
    }
}
