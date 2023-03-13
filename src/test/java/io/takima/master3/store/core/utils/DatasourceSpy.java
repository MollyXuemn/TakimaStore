package io.takima.master3.store.core.utils;

import com.zaxxer.hikari.HikariDataSource;
import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;
import net.ttddyy.dsproxy.listener.QueryExecutionListener;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
// ... imports omitted for sake of brevity

/**
 * Monitor SQL queries.
 * Tracks all queries made through the configured datasource.
 *
 * // credits : https://vladmihalcea.com/how-to-detect-the-n-plus-one-query-problem-during-testing/
 */
public interface DatasourceSpy {
    /**
     * @return All the SQL queries registered since the last call {@link DatasourceSpy#reset()}.
     */
    String[] getQueries();

    /**
     * Clear the registered queries.
     */
    void reset();

}

@Configuration
class ProxyDatasourceConfig {

    private DatasourceSpyImpl datasourceSpy = new DatasourceSpyImpl();

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public DataSource dataSource(DataSourceProperties properties) {
        HikariDataSource dataSource = properties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();

        if (StringUtils.hasText(properties.getName())) {
            dataSource.setPoolName(properties.getName());
        }

        return ProxyDataSourceBuilder
                .create(dataSource)
                .countQuery()
                .listener(new QueryExecutionListener() {
                    @Override
                    public void beforeQuery(ExecutionInfo executionInfo, List<QueryInfo> list) {

                    }

                    @Override
                    public void afterQuery(ExecutionInfo executionInfo, List<QueryInfo> list) {
                        datasourceSpy.getQueryList().addAll(list.stream().map(QueryInfo::getQuery).collect(Collectors.toList()));
                    }
                })
                .build();
    }

    @Bean
    DatasourceSpy getDatasourceSpy() {
        return this.datasourceSpy;
    }

    private static class DatasourceSpyImpl implements DatasourceSpy {
        private List<String> queries = new LinkedList<>();

        /**
         * Reset the statement recorder
         */
        public void reset() {
            this.queries.clear();
        }

        @Override
        public String[] getQueries() {
            return this.queries.toArray(String[]::new);
        }

        private List<String> getQueryList() {
            return this.queries;
        }
    }
}
