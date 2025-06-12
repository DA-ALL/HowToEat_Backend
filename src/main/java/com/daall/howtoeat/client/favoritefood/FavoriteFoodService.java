package com.daall.howtoeat.client.favoritefood;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoriteFoodService {
    private final FavoriteFoodRepository favoriteFoodRepository;
}
