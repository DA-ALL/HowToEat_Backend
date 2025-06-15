package com.daall.howtoeat.client.favoritefood;

import com.daall.howtoeat.client.favoritefood.dto.FavoriteFoodAddBySearchRequestDto;
import com.daall.howtoeat.client.favoritefood.dto.FavoriteFoodResponseDto;
import com.daall.howtoeat.common.enums.ErrorType;
import com.daall.howtoeat.common.enums.MealTime;
import com.daall.howtoeat.common.exception.CustomException;
import com.daall.howtoeat.domain.favoritefood.FavoriteFood;
import com.daall.howtoeat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoriteFoodService {
    private final FavoriteFoodRepository favoriteFoodRepository;

    /**
     * 즐겨찾기 음식 추가
     *
     * @param loginUser 현재 로그인한 유저
     * @param requestDto 즐겨찾기 음식 정보
     * @param foodImageFile 이미지 파일
     */
    public void addFavoriteFoodBySearch(User loginUser, FavoriteFoodAddBySearchRequestDto requestDto, MultipartFile foodImageFile) {
        FavoriteFood favoriteFood = new FavoriteFood(loginUser, requestDto);
        favoriteFoodRepository.save(favoriteFood);
    }


    /**
     * 즐겨찾기 음식 전체 조회
     *
     * @param loginUser 현재 로그인한 유저
     */
    public List<FavoriteFoodResponseDto> getFavoriteFoodList(User loginUser) {

        List<FavoriteFood> favoriteFoods = favoriteFoodRepository.findAllByUser(loginUser);

        List<FavoriteFoodResponseDto> favoriteFoodResponseDtoList = new ArrayList<>();

        for (FavoriteFood favoriteFood : favoriteFoods) {
            favoriteFoodResponseDtoList.add(new FavoriteFoodResponseDto(favoriteFood));
        }

        return favoriteFoodResponseDtoList;
    }

}
