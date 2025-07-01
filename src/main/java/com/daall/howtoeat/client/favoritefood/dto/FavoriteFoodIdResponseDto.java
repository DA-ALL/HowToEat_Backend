package com.daall.howtoeat.client.favoritefood.dto;

import com.daall.howtoeat.domain.favoritefood.FavoriteFood;
import lombok.Getter;

//즐겨찾기 추가 완료 시, favoriteFoodId 값을 보내줘, 페이지 새로 고침 없이 즐겨찾기 삭제 버튼에 id값 import 시키기 위한 DTO
@Getter
public class FavoriteFoodIdResponseDto {
    private Long favoriteFoodId;

    public FavoriteFoodIdResponseDto(FavoriteFood favoriteFood) {
        this.favoriteFoodId = favoriteFood.getId();
    }
}
