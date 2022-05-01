package kma.topic7;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@AllArgsConstructor
public class MyRestController {

   BookServiceImpl bookService;

    @RequestMapping(value = "/addBookRest", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Book> addBookWithRest(
            @RequestParam("title") String title,
            @RequestParam("isbn") String isbn,
            @RequestParam("author") String author){
        Book book = new Book(title, isbn, author);
        bookService.addBook(book);
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @GetMapping("/getBooks")
    @ResponseBody
    public List<Book> getBooks(){
            return bookService.getBooks();
    }

    @GetMapping("/books")
    @ResponseBody
    public List<Book> getBooksByParam(
            @RequestParam(name = "searchField",required = false) String field,
            @RequestParam(name = "value", required = false)String value){
        if (field == null || field.isEmpty()) {
            return bookService.getBooks();
        }
        return bookService.findByField(field, value);
    }

    @GetMapping("")
    public List<Book> booksPaginated(
            @RequestParam(name = "page") String page,
            @RequestParam(name = "size") String size){
        return bookService.getBooksPaginated(Integer.parseInt(size), Integer.parseInt(page));
    }
}
