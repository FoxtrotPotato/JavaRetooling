package com.foxtrotpotato.chickentest;

import com.foxtrotpotato.chickentest.entity.Chicken;
import com.foxtrotpotato.chickentest.entity.Farm;
import com.foxtrotpotato.chickentest.entity.Product;
import com.foxtrotpotato.chickentest.repository.ChickenRepository;
import com.foxtrotpotato.chickentest.service.serviceImpl.ChickenServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestFindChicken_ValidId_ShouldPass {
    @InjectMocks
    private ChickenServiceImpl chickenServiceImpl;
    @Mock
    ChickenRepository chickenRepository;
    @Mock
    Farm mockFarm = new Farm("mockFarm");
    @Mock
    Product mockProduct = new Product("chicken", 11.11d, 1, mockFarm);

    @Test
    public void findByIdOK() {
        Chicken expectedChicken = new Chicken(LocalDate.now(), mockFarm, mockProduct);
        when(chickenRepository.findById(1)).thenReturn(Optional.of(expectedChicken));

        Chicken actualChicken = chickenServiceImpl.findById(1);

        Assertions.assertEquals(expectedChicken, actualChicken);
    }


}



