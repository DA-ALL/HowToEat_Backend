package com.daall.howtoeat.client.favoritefood;

import com.daall.howtoeat.client.consumedfood.ConsumedFoodService;
import com.daall.howtoeat.client.favoritefood.dto.FavoriteFoodAddByConsumedFoodRequestDto;
import com.daall.howtoeat.client.favoritefood.dto.FavoriteFoodResponseDto;
import com.daall.howtoeat.domain.favoritefood.FavoriteFood;
import com.daall.howtoeat.domain.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteFoodService {
    private final FavoriteFoodRepository favoriteFoodRepository;
    private final ConsumedFoodService consumedFoodService;

    /**
     * 즐겨찾기 음식 추가
     *
     * @param loginUser 현재 로그인한 유저
     * @param requestDto 즐겨찾기 음식 정보
     * @param foodImageFile 이미지 파일
     */
    @Transactional
    public void createFavoriteFoodByConsumedFood(User loginUser, FavoriteFoodAddByConsumedFoodRequestDto requestDto, MultipartFile foodImageFile) {
        FavoriteFood favoriteFood = new FavoriteFood(loginUser, requestDto);

        favoriteFoodRepository.save(favoriteFood);

        // consumed_food에 새로 생성된 favoriteFoodId 값 추가하기
        consumedFoodService.linkFavoriteFoodToConsumedFood(requestDto.getConsumedFoodId(), favoriteFood);
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
