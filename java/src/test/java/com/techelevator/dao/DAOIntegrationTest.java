package com.techelevator.dao;

import java.sql.SQLException;
import javax.sql.DataSource;
import com.techelevator.Application;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public abstract class DAOIntegrationTest {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    private static boolean isDatabaseConfigured = false;

    /* Using this particular implementation of DataSource so that
     * every database interaction is part of the same database
     * session and hence the same database transaction */
    private static SingleConnectionDataSource dataSource;

    /* Before any tests are run, this method initializes the datasource for testing. */
    @Before
    public void setupDataSource() {
        if (!isDatabaseConfigured) {
            dataSource = new SingleConnectionDataSource();
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            /* The following line disables autocommit for connections
             * returned by this DataSource. This allows us to rollback
             * any changes after each test */
            dataSource.setAutoCommit(false);
            isDatabaseConfigured = true;
        }
    }

    /* After all tests have finished running, this method will close the DataSource */
    @AfterClass
    public static void closeDataSource() throws SQLException {
        dataSource.destroy();
    }

    /* After each test, we rollback any changes that were made to the database so that
     * everything is clean for the next test */
    @After
    public void rollback() throws SQLException {
        dataSource.getConnection().rollback();
    }

    /* This method provides access to the DataSource for subclasses so that
     * they can instantiate a DAO for testing */
    protected DataSource getDataSource() {
        return dataSource;
    }
}
