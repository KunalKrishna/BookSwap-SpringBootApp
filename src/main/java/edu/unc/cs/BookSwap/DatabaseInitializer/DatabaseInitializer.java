package edu.unc.cs.BookSwap.DatabaseInitializer;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DatabaseInitializer {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseInitializer(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate;}

    @EventListener(ContextRefreshedEvent.class)
    public void initializeDatabase() {
        createPreventExcessBooksTrigger();
    }

    private void createPreventExcessBooksTrigger() {
        String dropTriggerSQL = "DROP TRIGGER IF EXISTS prevent_excess_books;";
        String createTriggerSQL = "CREATE TRIGGER prevent_excess_books " +
                "BEFORE INSERT ON book_ownership " +
                "FOR EACH ROW " +
                "BEGIN " +
                "    DECLARE book_count INT; " +
                "    SELECT COUNT(*) INTO book_count " +
                "    FROM book_ownership " +
                "    WHERE id = NEW.id; " +
                "    IF book_count >= 10 THEN " +
                "        SIGNAL SQLSTATE '45000' " +
                "        SET MESSAGE_TEXT = 'User cannot own more than 10 books.'; " +
                "    END IF; " +
                "END;";

        try {
            // Drop the trigger if it exists
            jdbcTemplate.execute(dropTriggerSQL);

            // Create the trigger
            jdbcTemplate.execute(createTriggerSQL);

            System.out.println("Trigger 'prevent_excess_books' created successfully.");
        } catch (Exception e) {
            System.err.println("Error creating trigger: " + e.getMessage());
        }
    }
}