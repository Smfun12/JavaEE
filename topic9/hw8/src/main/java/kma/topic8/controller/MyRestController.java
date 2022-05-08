package kma.topic8.controller;

import kma.topic8.model.Book;
import kma.topic8.model.User;
import kma.topic8.model.WishList;
import kma.topic8.repository.UserRepo;
import kma.topic8.repository.WishListRepo;
import kma.topic8.service.BookServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@AllArgsConstructor
public class MyRestController {

   BookServiceImpl bookService;
   UserRepo userRepo;
   WishListRepo wishListRepo;

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
        List<Book> books = bookService.getBooks();
        books.forEach(book -> book.setWishlist(null));
        return books;
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

    @GetMapping("/wishlist/add/{id}")
    public String addToWishList(@PathVariable(name = "id") String bookId,
                           Authentication authentication){
        if (authentication == null)
            return "No authenticated";
        User byLogin = userRepo.findByLogin(authentication.getName()).get();
        WishList wishList = byLogin.getWishList();
        if (wishList == null)
            wishList = new WishList();
        wishList.getBooks().add(bookService.findById(Long.parseLong(bookId)));
        wishList.setUser(byLogin);
        wishListRepo.save(wishList);
        return "Action approved for " + authentication.getAuthorities();
    }
}
