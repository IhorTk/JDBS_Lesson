package HomeWork;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class Main {

    private static final String CREATE_SQL = """
            CREATE TABLE jdbc1
            (
            id int auto_increment primary key,
            info text
            );
            """;

    private static final String INSERT_SQL = """
            INSERT INTO jdbc1(info)
            VALUES ('Test1'),
                   ('Test2'),
                   ('Test3')
            """;

    private static final String UPDATE_SQL = """
            UPDATE jdbc1
            SET info ='test33'
            WHERE id=3;
            """;

    private static final String DELETE_SQL = """
            DELETE FROM jdbc1
            WHERE id=3;
            """;

    private static final String SELECT_SQL = """
            SELECT id,info FROM jdbc1 ;
            """;


    public static void main(String[] args) {

//        try (Connection connection = ConnectionManager.openConnection()) {
//            System.out.println("connection.getTransactionIsolation() = " + connection.getTransactionIsolation());
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

//
//        try(Connection connection = ConnectionManager.openConnection()){
//            Statement statement=connection.createStatement();
//            boolean execute=statement.execute(CREATE_SQL);
//            System.out.println(execute);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }


//        try (Connection connection = ConnectionManager.openConnection()) {
//            Statement statement = connection.createStatement();
//            int update = statement.executeUpdate(INSERT_SQL);
//            System.out.println("update = " + update);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

//        try (Connection connection = ConnectionManager.openConnection()) {
//            Statement statement = connection.createStatement();
//            int update = statement.executeUpdate(UPDATE_SQL);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

//        try (Connection connection = ConnectionManager.openConnection()) {
//            Statement statement = connection.createStatement();
//            int update = statement.executeUpdate(DELETE_SQL);
//            System.out.println("update = " + update);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

//        try (Connection connection = ConnectionManager.openConnection()) {
//            Statement statement = connection.createStatement();
//            DatabaseMetaData metaData = connection.getMetaData();
//            System.out.println("metaData.getDriverVersion() = " + metaData.getDriverVersion());
//            System.out.println("metaData.getURL() = " + metaData.getURL());
//            ResultSet catalogs = metaData.getCatalogs();
//            while (catalogs.next()){
//                System.out.println("catalogs.getObject(1) = " + catalogs.getObject(1));
//            }
//            System.out.println("-------------------------------------------------");
//
//            ResultSet resultSet = statement.executeQuery(SELECT_SQL);
//            while (resultSet.next()) {
//                int anInt = resultSet.getInt("id");
//                String string = resultSet.getString("info");
//                System.out.println(anInt + " " + string);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }


        String invoiceId ="1 or 1=1";
        List<Integer> integers = sqlInjectionMethod(invoiceId);
        integers.forEach(x-> System.out.println(x));

    }


    static List<Integer> sqlInjectionMethod(String invoiceId) {
        String sql_injection = """
                select invoice_id,number, client_id, due_date
                from invoices
                where invoice_id = %s
                """.formatted(invoiceId);

        try (Connection connection = ConnectionManager.openConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql_injection);
            List<Integer> invoiceIds = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("invoice_id");
                invoiceIds.add(id);
            }
            return invoiceIds;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}