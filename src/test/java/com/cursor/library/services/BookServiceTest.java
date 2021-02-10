package com.cursor.library.services;

import com.cursor.library.daos.BookDao;
import com.cursor.library.exceptions.BadIdException;
import com.cursor.library.exceptions.BookNameIsNullException;
import com.cursor.library.exceptions.BookNameIsTooLongException;
import com.cursor.library.models.Book;
import com.cursor.library.models.CreateBookDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    private final BookService bookService;

    @Mock
    private final BookService forCreateBookTest;

    @Mock
    private BookDao bookDao = new BookDao();

    public BookServiceTest() {
        forCreateBookTest = new BookService(bookDao);
        bookService = new BookService(bookDao);
    }

    @BeforeAll
    void setUp() {
        bookDao = Mockito.mock(BookDao.class);
    }

    @Test
    void getBookByIdSuccessTest() {
        String bookId = "book-id";
        Book book = new Book();
        book.setBookId(bookId);
        Mockito.when(bookDao.getById(bookId)).thenReturn(book);

        Book bookFromDB = bookDao.getById(bookId);

        assertEquals(
                bookId,
                bookFromDB.getBookId()
        );
    }

    @Test
    void getBookByIdBadIdExceptionTest() {
        assertThrows(
                BadIdException.class,
                () -> bookService.getById("       ")
        );
    }

    @Test
    void getValidatedBookNameExpectBookNameIsNullExceptionTest() {
        assertThrows(
                BookNameIsNullException.class,
                () -> bookService.getValidatedBookName(null)
        );
    }

    @Test
    void getValidatedBookShouldBookNameIsTooLongException() {
        String tooLongString = "abcdefghijk";
        assertThrows(BookNameIsTooLongException.class,
                () -> bookService.getValidatedBookName(tooLongString));
    }

    @Test
    void getValidatedShouldTrimString() {
        String example = "trim";
        String errorString = " trim ";
        String actual = bookService.getValidatedBookName(errorString);
        assertEquals(example, actual);
    }

    @Test
    void getBookByIdTestByName() {
        String id = "random_id_value_1";
        Book example = new Book();
        example.setBookId(id);
        example.setName("Anna Karenina");
        example.setAuthors(List.of("Leo Tolstoy"));
        example.setDescription("");
        example.setYearOfPublication(1877);
        example.setNumberOfWords(864368);
        example.setRating(8);
        Book actual = bookService.getById(id);
        assertEquals(example, actual);
    }

    @Test
    public void createNewBookServiceTest() {

        CreateBookDto createBookDto = new CreateBookDto();
        createBookDto.setName("Doctor Who");
        createBookDto.setAuthors(List.of("Dmitriy Donskoy"));
        createBookDto.setDescription("Book about love");
        createBookDto.setYearOfPublication(2020);
        createBookDto.setNumberOfWords(325340);
        createBookDto.setRating(7);

        Book exampleBook = new Book();
        exampleBook.setBookId("12");
        exampleBook.setName(createBookDto.getName());
        exampleBook.setAuthors(createBookDto.getAuthors());
        exampleBook.setDescription(createBookDto.getDescription());
        exampleBook.setYearOfPublication(createBookDto.getYearOfPublication());
        exampleBook.setNumberOfWords(createBookDto.getNumberOfWords());
        exampleBook.setRating(createBookDto.getRating());

        Mockito.when(forCreateBookTest.createBook(createBookDto)).thenReturn(exampleBook);
        Book actual = forCreateBookTest.createBook(createBookDto);

        assertEquals(exampleBook, actual);
    }
}
