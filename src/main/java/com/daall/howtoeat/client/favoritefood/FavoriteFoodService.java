package com.daall.howtoeat.client.favoritefood;

import com.daall.howtoeat.client.consumedfood.ConsumedFoodService;
import com.daall.howtoeat.client.favoritefood.dto.FavoriteFoodAddByConsumedFoodRequestDto;
import com.daall.howtoeat.client.favoritefood.dto.FavoriteFoodResponseDto;
import com.daall.howtoeat.common.enums.ErrorType;
import com.daall.howtoeat.common.exception.CustomException;
import com.daall.howtoeat.domain.consumedfood.ConsumedFood;
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
     */
    @Transactional
    public void createFavoriteFoodByConsumedFood(User loginUser, FavoriteFoodAddByConsumedFoodRequestDto requestDto) {
        ConsumedFood consumedFood = consumedFoodService.getConsumedFood(requestDto.getConsumedFoodId());

        FavoriteFood favoriteFood = new FavoriteFood(loginUser, consumedFood);

        favoriteFoodRepository.save(favoriteFood);

        // consumed_food에 새로 생성된 favoriteFoodId 값 추가하기
        consumedFoodService.linkFavoriteFoodToConsumedFood(requestDto.getConsumedFoodId(), favoriteFood);
        System.out.println("들어왔어유~");
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


    /**
     * 즐겨찾기 음식 삭제
     *
     * @param favoriteFoodId 삭제할 favoriteFoodId
     */
    @Transactional
    public void deleteFavoriteFood(Long favoriteFoodId) {
        FavoriteFood favoriteFood = favoriteFoodRepository.findById(favoriteFoodId).orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_FAVORITE_FOOD));

        List<ConsumedFood> consumedFoods = consumedFoodService.getConsumedFoodByFavoriteFoodId(favoriteFood);

        // consumedFood와 관계 제거하기
        for (ConsumedFood consumedFood : consumedFoods) {
            consumedFood.updateFavoriteFood(null);
        }

        favoriteFoodRepository.delete(favoriteFood);
    }

}
