package org.tqs.deti.ua;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.slf4j.LoggerFactory.getLogger;

public class BooksSearchSteps {

    static final Logger log = getLogger(lookup().lookupClass());

    private Library library;
    private List<Book> books;


    /*
	create a registered type named iso8601Date to map a string pattern from the feature
	into a custom datatype. Extracted parameters should be strings.
	 */
    @ParameterType("([0-9]{4})-([0-9]{2})-([0-9]{2})")
    public LocalDate iso8601Date(String year, String month, String day){
        return Utils.localDateFromDateParts(year, month, day);
    }


    /**
     * load a data table from the feature (tabular format) and call this method
     * for each row in the table. Injected parameter is a map with column name --> value
     */
    @DataTableType
    public Book bookEntry(Map<String, String> tableEntry){
        return new Book(tableEntry.get("title"), tableEntry.get("author"), Utils.isoTextToLocalDate( tableEntry.get("published") ) );
    }

    @Given("a library with the following books")
    public void givenALibraryWithTheFollowingBooks(DataTable dataTable){
        library = new Library();
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> row : rows) {
            Book book = bookEntry(row);
            library.add(book);
        }
    }


    @When("the client search books made by the author {string}")
    public void searchBooksByAuthor(String author){
        this.books = library.findBooksByAuthor(author);
    }

    @When("the client search books published between the years {iso8601Date} and {iso8601Date}")
    public void searchBooksByYearInterval(LocalDate from, LocalDate to){
        this.books = library.findBooks(from, to);
    }

    @When("the client search a book by the title {string}")
    public void searchBooksByYearInterval(String title){
        this.books = library.findBooksByTitle(title);
    }

    @Then("{int} books should have been found")
    public void booksShouldHaveBeenFound(int number, DataTable dataTable){
        assertEquals(number, books.size());
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (int i = 0; i < number; i++) {
            Book book = books.get(i);
            Map<String, String> row = rows.get(i);
            assertEquals(book.getTitle(), row.get("title"));
            assertEquals(book.getAuthor(), row.get("author"));
            String[] date_str = row.get("published").split("-");
            assertEquals(book.getPublished(), iso8601Date( date_str[0], date_str[1], date_str[2]));
        }
    }
}
