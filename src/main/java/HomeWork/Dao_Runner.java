package HomeWork;

import HomeWork.dao.InvoiceDao;
import HomeWork.entity.Invoice;

import java.io.FilterOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class Dao_Runner {
    public static void main(String[] args) {
        InvoiceDao invoiceDao = InvoiceDao.getInstance();

        List<Invoice> allInvoices = invoiceDao.findAll();

        allInvoices.forEach(invoice -> System.out.println(invoice));

        Invoice invoice = new Invoice(22,"333-33-33", 2,
                BigDecimal.valueOf(222.22),BigDecimal.valueOf(222.22),
                LocalDate.of(2024,02,19),
                LocalDate.of(2024,05,25),
                LocalDate.of(2024,04,01));
        invoiceDao.save(invoice);

        System.out.println("**************************************************************************");

        Optional<Invoice>  findInvoice = invoiceDao.findById(22);
        System.out.println(findInvoice);

        System.out.println("**************************************************************************");

        Invoice invoice1 = new Invoice(22,"555-55-55", 2 ,
                BigDecimal.valueOf(444.44),BigDecimal.valueOf(555.55), LocalDate.of(2023,03,25),LocalDate.of(2023,05,
                06),LocalDate.of(2023,07,31));
        invoiceDao.update(invoice1);

        System.out.println("**************************************************************************");

        findInvoice = invoiceDao.findById(22);
        System.out.println(findInvoice);

        System.out.println("**************************************************************************");


        invoiceDao.deleteById(22);


        List<Invoice> allInvoices1 = invoiceDao.findAll();
        allInvoices1.forEach(invoice2 -> System.out.println(invoice2));

        System.out.println("**************************************************************************");






    }
}
