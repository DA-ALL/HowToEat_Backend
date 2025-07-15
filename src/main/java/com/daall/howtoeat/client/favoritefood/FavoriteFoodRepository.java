package com.daall.howtoeat.client.favoritefood;

import com.daall.howtoeat.domain.favoritefood.FavoriteFood;
import com.daall.howtoeat.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteFoodRepository extends JpaRepository<FavoriteFood, Long>, FavoriteFoodRepositoryQuery {
    List<FavoriteFood> findAllByUser(User user);

    //회원탈퇴 시 연관관계 끊기
    void deleteAllByUser(User user);
}
