package com.daall.howtoeat.client.favoritefood;

import com.daall.howtoeat.admin.favoritefood.dto.AdminFavoriteFoodWithUserResponseDto;
import org.springframework.data.domain.Page;

public interface FavoriteFoodRepositoryQuery {
    Page<AdminFavoriteFoodWithUserResponseDto> searchFavoriteFoods(int page, int size, String name, String orderBy, String adminShared);
}
