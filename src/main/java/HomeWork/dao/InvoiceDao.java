package HomeWork.dao;

import HomeWork.ConnectionManager;
import HomeWork.entity.Invoice;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InvoiceDao implements Dao<Integer, Invoice>{

    private static final InvoiceDao INSTANCE = new InvoiceDao();

    private static final String FIND_ALL_SQL= """
            SELECT *
            FROM invoices;
            """;

    private static final String SAVE_SQL = """
            INSERT INTO invoices(INVOICE_ID, NUMBER, CLIENT_ID, INVOICE_TOTAL, PAYMENT_TOTAL, INVOICE_DATE, DUE_DATE, PAYMENT_DATE) 
            VALUES(?,?,?,?,?,?,?,?); 
            """;

    private InvoiceDao(){

    }

    @Override
    public List<Invoice> findAll() {
        try (Connection connection = ConnectionManager.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Invoice invoice = null;
            List<Invoice> invoices = new ArrayList<>();
            while (resultSet.next()){
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
            preparedStatement.setInt(1,entity.getInvoiceId());
            preparedStatement.setString(2,entity.getNumber());
            preparedStatement.setInt(3,entity.getClientId());
            preparedStatement.setBigDecimal(4,entity.getInvoiceTotal());
            preparedStatement.setBigDecimal(5,entity.getPaymentTotal());
            preparedStatement.setDate(6, Date.valueOf(entity.getInvoiceDate()));
            preparedStatement.setDate(7,Date.valueOf(entity.getDueDate()));
            preparedStatement.setDate(8,Date.valueOf(entity.getPaymentDate()));
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Invoice entity) {
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Optional<Invoice> findById(Integer id) {
        return Optional.empty();
    }

    public static InvoiceDao getInstance() {
        return INSTANCE;
    }
}