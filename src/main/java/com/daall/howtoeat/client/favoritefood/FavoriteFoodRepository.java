package com.daall.howtoeat.client.favoritefood;

import com.daall.howtoeat.domain.favoritefood.FavoriteFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteFoodRepository extends JpaRepository<FavoriteFood, Long> {
}
