package com.daall.howtoeat.admin;

import com.daall.howtoeat.common.enums.UserRole;
import com.daall.howtoeat.domain.user.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class AdminAccountControllerTest {
    @Autowired
    AdminAccountService adminAccountService;

    @Test
    void createAdminMaster(){
        AdminAccountRequestDto mock1 = Mockito.mock(AdminAccountRequestDto.class);

        when(mock1.getAccountId()).thenReturn("admin");
        when(mock1.getPassword()).thenReturn("1234");

        User userMock = Mockito.mock(User.class);
        when(userMock.getId()).thenReturn(1L);
        when(userMock.getUserRole()).thenReturn(UserRole.MASTER);
        when(userMock.getBirth()).thenReturn(LocalDate.now());
        when(userMock.getName()).thenReturn("admin");

        adminAccountService.createAdminAccount(new User(), mock1);
    }
}