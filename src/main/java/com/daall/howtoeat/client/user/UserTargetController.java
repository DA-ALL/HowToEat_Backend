package com.daall.howtoeat.client.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserTargetController {
    private final UserTargetService userTargetService;

//    @GetMapping("")
}
