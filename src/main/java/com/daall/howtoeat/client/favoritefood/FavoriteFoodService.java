package com.daall.howtoeat.client.favoritefood;

import com.daall.howtoeat.client.favoritefood.dto.FavoriteFoodAddBySearchRequestDto;
import com.daall.howtoeat.domain.favoritefood.FavoriteFood;
import com.daall.howtoeat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FavoriteFoodService {
    private final FavoriteFoodRepository favoriteFoodRepository;

    public void addFavoriteFoodBySearch(User loginUser, FavoriteFoodAddBySearchRequestDto requestDto, MultipartFile foodImageFile) {
        FavoriteFood favoriteFood = new FavoriteFood(loginUser, requestDto);
        favoriteFoodRepository.save(favoriteFood);
    }
}
