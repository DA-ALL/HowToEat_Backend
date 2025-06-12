package com.daall.howtoeat.client.favoritefood;

import com.daall.howtoeat.client.favoritefood.dto.FavoriteFoodAddBySearchRequestDto;
import com.daall.howtoeat.domain.favoritefood.FavoriteFood;
import com.daall.howtoeat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoriteFoodService {
    private final FavoriteFoodRepository favoriteFoodRepository;


    public void addFavoriteFoodBySearch(User loginUser, FavoriteFoodAddBySearchRequestDto requestDto) {
        FavoriteFood favoriteFood = new FavoriteFood(loginUser, requestDto);

        favoriteFoodRepository.save(favoriteFood);
    }
}
