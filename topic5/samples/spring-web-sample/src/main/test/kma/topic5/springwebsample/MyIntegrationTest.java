package kma.topic5.springwebsample;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MyIntegrationTest {

    @Autowired
    ObjectMapper objectMapper;

    @LocalServerPort
    void setPort(int port){
        RestAssured.port = port;
    }

    @BeforeEach
    public void clearBookList() {
        RestAssured.when().get("/book/clear");
    }
    @Test
    public void shouldCreateBook() {
        Book book = Book.of("math", "123", "kozerenko");

        RestAssured
                .given()
                    .param("title","math")
                    .param("author","kozerenko")
                    .param("isbn","123")
                .when()
                    .post("/book/addBookRest")
                .then()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .body("title", CoreMatchers.is(book.getTitle()))
                    .body("isbn", CoreMatchers.is(book.getIsbn()))
                    .body("author", CoreMatchers.is(book.getAuthor()));

    }

    @Test
    public void shouldReturnBooks() {
        Book book = Book.of("math", "123", "kozerenko");
        List<Book> bookList = Collections.singletonList(book);
        RestAssured
                .given()
                    .param("title","math")
                    .param("author","kozerenko")
                    .param("isbn","123")
                .when()
                    .post("/book/addBookRest");
        List<Book> list = RestAssured
                .when()
                    .get("/book/getBooks")
                .then()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .extract()
                    .body()
                    .jsonPath()
                    .getList("$", Book.class);
        assertEquals(bookList, list);
    }

    @Test
    public void shouldReturnBookByParam() {
        Book book1 = Book.of("topology", "123", "kozerenko");
        RestAssured
                .given()
                    .param("title","math")
                    .param("author","kozerenko")
                    .param("isbn","123")
                .when()
                    .post("/book/addBookRest");
        RestAssured
                .given()
                    .param("title","topology")
                    .param("author","kozerenko")
                    .param("isbn","123")
                .when()
                    .post("/book/addBookRest");
        List<Book> list = RestAssured
                .given()
                    .param("searchField","topo")
                .when()
                    .get("/book/books")
                .then()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .extract()
                    .body()
                    .jsonPath()
                    .getList("$", Book.class);
        assertEquals(Collections.singletonList(book1), list);
    }
}
