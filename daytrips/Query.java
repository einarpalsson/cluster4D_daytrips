package daytrips;
import javax.sql.*;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.io.File;

public class Query {
  private static final String DB_URL = "daytrips" + File.separator + "daytrips.db";


  private static Connection connect() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");

        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_URL);
        } catch (SQLException err) {
            System.err.println(err);
        }

        return connection;
    }
    private static void close(Connection c, Statement s) {
        try {
            s.close();
        } catch (Exception err) {
            /* Ignored */ }
        try {
            c.close();
        } catch (Exception err) {
            /* Ignored */ }
    }
    static CachedRowSet query(String sql) {
        Connection connection = null;
        PreparedStatement statement = null;
        CachedRowSet res = null;
        ResultSet rs = null;

        try {
            connection = connect();
            statement = connection.prepareStatement(sql);
            rs = statement.executeQuery();
            RowSetFactory factory = RowSetProvider.newFactory();
            res = factory.createCachedRowSet();
            res.populate(rs);

        } catch (Exception err) {
            System.err.println(err);
        } finally {
            try {
                rs.close();
            } catch (Exception err) {
                /* Ignored */ }
            close(connection, statement);
        }
        return res;
    }
    static CachedRowSet query(String sql, ArrayList<String> vals) {
        Connection connection = null;
        PreparedStatement statement = null;
        CachedRowSet res = null;
        ResultSet rs = null;

        try {
            connection = connect();
            statement = connection.prepareStatement(sql);
            for (int i = 0; i < vals.size(); i++) {
                statement.setString(i + 1, vals.get(i));
            }
            rs = statement.executeQuery();
            RowSetFactory factory = RowSetProvider.newFactory();
            res = factory.createCachedRowSet();
            res.populate(rs);

        } catch (Exception err) {
            System.err.println(err);
        } finally {
            try {
                rs.close();
            } catch (Exception err) {
                /* Ignored */ }
            close(connection, statement);
        }
        return res;
    }

    static void insert(String sql, ArrayList<String> vals) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;

        connection = connect();
        statement = connection.prepareStatement(sql);

        for (int i = 0; i < vals.size(); i++) {
            statement.setString(i + 1, vals.get(i));
        }

        statement.executeUpdate();
        close(connection, statement);
}
}
