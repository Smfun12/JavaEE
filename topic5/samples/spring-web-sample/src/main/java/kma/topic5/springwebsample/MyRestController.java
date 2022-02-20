package kma.topic5.springwebsample;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/book")
public class MyRestController {

    List<Book> bookList = new ArrayList<>();

    @GetMapping("/clear")
    @ResponseBody
    public void clear(){
        bookList.clear();
    }

    @RequestMapping(value = "/addBookRest", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Book> addBookWithRest(
            @RequestParam("title") String title,
            @RequestParam("isbn") String isbn,
            @RequestParam("author") String author){
        Book book = Book.of(title, isbn, author);
        bookList.add(book);
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @GetMapping("/getBooks")
    @ResponseBody
    public List<Book> getBooks(){
            return bookList;
    }

    @GetMapping("/books")
    @ResponseBody
    public List<Book> getBooksByParam(
            @RequestParam(name = "searchField",required = false) String field){
        if (field == null || field.isEmpty()) {
            return bookList;
        }
        return bookList.stream().filter(book -> book.getTitle().contains(field)
                || book.getIsbn().contains(field)).collect(Collectors.toList());
    }
}
