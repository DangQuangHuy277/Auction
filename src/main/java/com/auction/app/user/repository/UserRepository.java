package com.auction.app.user.repository;

import com.auction.app.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select count(u) from Users u where u.role = 'Seller'", nativeQuery = true)
    long countAllSellers();
    @Query(value = "select count(u) from Users u where u.role = 'Bidder'", nativeQuery = true)
    long countAllBidders();
    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
