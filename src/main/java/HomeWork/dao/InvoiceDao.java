package HomeWork.dao;

import HomeWork.ConnectionManager;
import HomeWork.entity.Invoice;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InvoiceDao implements Dao<Integer, Invoice> {

    private static final InvoiceDao INSTANCE = new InvoiceDao();

    public static InvoiceDao getInstance() {
        return INSTANCE;
    }

    private static final String FIND_ALL_SQL = """
            SELECT *
            FROM invoices;
            """;

    private static final String SAVE_SQL = """
            INSERT INTO invoices(INVOICE_ID, NUMBER, CLIENT_ID, INVOICE_TOTAL, PAYMENT_TOTAL, INVOICE_DATE, DUE_DATE, PAYMENT_DATE) 
            VALUES(?,?,?,?,?,?,?,?); 
            """;

    private static final String UPDATE_SQL = """
            UPDATE invoices
            SET number =?,
                client_id = ?,
                invoice_total =?,
                payment_total =?,
                invoice_date =?,
                due_date =?,
                payment_date =?
            WHERE invoice_id=?;
            """;

    private static final String DELETE_SQL = """
            DELETE FROM invoices
            WHERE invoice_id=?;
            """;

    private static final String FIND_BY_ID = """
            SELECT *
            FROM invoices
            WHERE invoice_id=?;
            """;

    private InvoiceDao() {

    }

    @Override
    public List<Invoice> findAll() {
        try (Connection connection = ConnectionManager.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Invoice invoice = null;
            List<Invoice> invoices = new ArrayList<>();
            while (resultSet.next()) {
                invoice = new Invoice(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getBigDecimal(4),
                        resultSet.getBigDecimal(5),
                        resultSet.getObject(6, LocalDate.class),
                        resultSet.getObject(7, LocalDate.class),
                        resultSet.getObject(8, LocalDate.class)
                );
                invoices.add(invoice);

            }
            return invoices;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void save(Invoice entity) {
        try (Connection connection = ConnectionManager.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL)) {
            preparedStatement.setInt(1, entity.getInvoiceId());
            preparedStatement.setString(2, entity.getNumber());
            preparedStatement.setInt(3, entity.getClientId());
            preparedStatement.setBigDecimal(4, entity.getInvoiceTotal());
            preparedStatement.setBigDecimal(5, entity.getPaymentTotal());
            preparedStatement.setDate(6, Date.valueOf(entity.getInvoiceDate()));
            preparedStatement.setDate(7, Date.valueOf(entity.getDueDate()));
            preparedStatement.setDate(8, Date.valueOf(entity.getPaymentDate()));
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Invoice entity) {
        try (Connection connection = ConnectionManager.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, entity.getNumber());
            preparedStatement.setInt(2, entity.getClientId());
            preparedStatement.setBigDecimal(3, entity.getInvoiceTotal());
            preparedStatement.setBigDecimal(4, entity.getPaymentTotal());
            preparedStatement.setDate(5, Date.valueOf(entity.getInvoiceDate()));
            preparedStatement.setDate(6, Date.valueOf(entity.getDueDate()));
            preparedStatement.setDate(7, Date.valueOf(entity.getPaymentDate()));
            preparedStatement.setInt(8, entity.getInvoiceId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (Connection connection = ConnectionManager.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public Optional<Invoice> findById(Integer invoice_id) {
        try (Connection connection = ConnectionManager.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1, invoice_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Invoice invoice = null;
            if (resultSet.next()) {
                invoice = new Invoice(
                        resultSet.getInt("invoice_id"),
                        resultSet.getString("number"),
                        resultSet.getInt("client_id"),
                        resultSet.getBigDecimal("invoice_total"),
                        resultSet.getBigDecimal("payment_total"),
                        resultSet.getObject("invoice_date", LocalDate.class),
                        resultSet.getObject("due_date", LocalDate.class),
                        resultSet.getObject("payment_date", LocalDate.class)
                );

            }
            return Optional.ofNullable(invoice);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}