package com.foxtrotpotato.chickentest;

import com.foxtrotpotato.chickentest.entity.Farm;
import com.foxtrotpotato.chickentest.entity.User;
import com.foxtrotpotato.chickentest.repository.FarmRepository;
import com.foxtrotpotato.chickentest.service.serviceImpl.FarmServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;


//@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class TestGetFarmByLoggedUser_AuthUser_ShouldPass {

    @InjectMocks
    private FarmServiceImpl farmServiceImpl;
    @Mock
    private FarmRepository farmRepository;

    @Mock
    private Farm expectedFarm;

    @Mock
    private User mockedUser = new User("Peter", "Parker", "spiderman", "spiderman@email.com", "");

    @Mock
    List<User> users = new ArrayList<>();


    @BeforeEach
    void setUp() {
        users.add(mockedUser);
        this.expectedFarm = new Farm("farmfarmfarm");
        expectedFarm.setUsers(users);
    }

    @Test
    public void getFarmByLoggedUserOK() {
        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername("spiderman")
                .password("password")
                .roles("ADMIN")
                .build();

        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(auth);
        SecurityContextHolder.setContext(securityContext);

        given(farmRepository.findByUsersUsername("spiderman")).willReturn((expectedFarm));
        //when(farmRepository.findByUsersUsername("spiderman")).thenReturn(expectedFarm);


        Farm actualFarm = farmRepository.findByUsersUsername("spiderman");
        //Farm actualFarm = farmServiceImpl.getFarmByLoggedUser();

        assertEquals(expectedFarm.getFarmName(), actualFarm.getFarmName());
    }


}
