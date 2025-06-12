package com.daall.howtoeat.client.favoritefood;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class FavoriteFoodController {
    private final FavoriteFoodService favoriteFoodService;
}
