package com.cart.cartbookstore.repository;

import com.cart.cartbookstore.model.BookModel;
import com.cart.cartbookstore.model.CartModel;
import com.cart.cartbookstore.model.UserModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartModel,Long> {
//    @Query(value = "select * from userbookdb.user where user.user_id=:userId", nativeQuery = true)
//    Optional<UserModel> getUserId(Long userId);
//    @Query(value = "select * from book_bookstoredb.book where book.book_id=:bookId", nativeQuery = true)
//    Optional<BookModel> getBookId(Long bookId);
    @Query(value = "select * from cart_bookstoreappdb.cart where cart.user_id=:userId",nativeQuery = true)
    Optional<CartModel> findByUserId(long userId);
    @Transactional
    @Modifying
    @Query(value = "delete from cart_bookstoreappdb.cart where cart.cart_id=:id",nativeQuery = true)
    void deleteById(long id);
}
