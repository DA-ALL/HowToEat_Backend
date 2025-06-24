package com.daall.howtoeat.admin.favoritefood;

import com.daall.howtoeat.admin.favoritefood.dto.AdminFavoriteFoodResponseDto;
import com.daall.howtoeat.admin.favoritefood.dto.AdminFavoriteFoodWithUserResponseDto;
import com.daall.howtoeat.client.favoritefood.FavoriteFoodRepository;
import com.daall.howtoeat.domain.favoritefood.FavoriteFood;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminFavoriteFoodService {
    private final FavoriteFoodRepository favoriteFoodRepository;

    public Page<AdminFavoriteFoodWithUserResponseDto> getUserRegisteredFoods(int page, int size, String name, String orderBy, String adminShared) {
        return favoriteFoodRepository.searchFavoriteFoods(page, size, name, orderBy, adminShared);
    }

    public AdminFavoriteFoodResponseDto getUserRegisteredFood(Long favoriteFoodId) {
        Optional<FavoriteFood> favoriteFood = favoriteFoodRepository.findById(favoriteFoodId);
        AdminFavoriteFoodResponseDto responseDto = null;

        if(favoriteFood.isPresent()){
            responseDto = new AdminFavoriteFoodResponseDto(favoriteFood.get());
        }

        return responseDto;
    }
}
