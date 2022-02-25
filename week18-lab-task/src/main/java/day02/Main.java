package day02;

import org.flywaydb.core.Flyway;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        MariaDbDataSource dataSource;
        try {
            dataSource = new MariaDbDataSource("jdbc:mariadb://localhost:3306/bookstore");
            dataSource.setUser("bookstore");
            dataSource.setPassword("bookstore");
        } catch (
                SQLException exception) {
            throw new IllegalStateException("SQL error!", exception);
        }

        Flyway flyway = Flyway.configure().locations("db/migration/bookstore").dataSource(dataSource).load();
        flyway.migrate();

        BooksRepository booksRepository = new BooksRepository(dataSource);
        booksRepository.updatePieces(1, 20);
        //booksRepository.insertBook("Fekete István", "Vuk", 3000, 10);
        System.out.println(booksRepository.findBooksByWriter("F"));
    }
}
