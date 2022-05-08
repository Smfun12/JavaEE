package kma.topic8.repository;

import kma.topic8.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepo extends JpaRepository<WishList, Long> {
}
