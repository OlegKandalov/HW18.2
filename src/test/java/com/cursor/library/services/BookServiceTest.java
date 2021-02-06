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

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    private final BookService bookService;

    @Mock
    private BookDao bookDao = new BookDao();

    public BookServiceTest() {
        bookService = new BookService(bookDao);
    }

    @BeforeAll
    void setUp() {
        bookDao = Mockito.mock(BookDao.class);
    }

    @Test
    void getBookByIdSuccessTest() {
        String bookId = "book-id";

        Mockito.when(bookDao.getById(bookId)).thenReturn(new Book(bookId));

        Book bookFromDB = bookService.getById(bookId);

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
        example.setName("Anna Karenina");
        example.setAuthors(List.of("Leo Tolstoy"));
        example.setDescription("");
        example.setYearOfPublication(1877);
        example.setNumberOfWords(864368);
        example.setRating(8);
        Book actual = bookService.getById(id);
        assertEquals(example.getName(), actual.getName());
    }

    @Test
    public void createBookTest() {
        CreateBookDto createBookDto = new CreateBookDto();
        createBookDto.setName("Doctor Who");
        createBookDto.setAuthors(List.of("Dmitriy Donskoy"));
        createBookDto.setDescription("Book about love");
        createBookDto.setYearOfPublication(2020);
        createBookDto.setNumberOfWords(325340);
        createBookDto.setRating(7);
        Book actual = bookService.createBook(createBookDto);
        assertEquals(null, actual);
    }
}
