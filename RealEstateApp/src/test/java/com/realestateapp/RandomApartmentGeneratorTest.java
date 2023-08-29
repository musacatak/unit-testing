package com.realestateapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class RandomApartmentGeneratorTest {

    private static final double MAX_MULTIPLIER = 4.0;


    @Nested
    class GenerateDefaultParamsTests {

        private RandomApartmentGenerator randomApartmentGenerator;

        @BeforeEach
        void setup() {
            this.randomApartmentGenerator = new RandomApartmentGenerator();
        }

        @RepeatedTest(10)
        void should_GenerateCorrectApartment_When_DefaultMinAreaMinPrice() {
            //given
            double minArea = 30.0;
            double maxArea = minArea * MAX_MULTIPLIER;
            BigDecimal minPricePerSquareMeter = new BigDecimal("3000.0");
            BigDecimal maxPricePerSquareMeter = minPricePerSquareMeter.multiply(new BigDecimal(MAX_MULTIPLIER));

            //when
            Apartment apartment = randomApartmentGenerator.generate();
            //then

            assertAll(
                    () -> assertTrue(apartment.getArea() >= minArea),
                    () -> assertTrue(apartment.getArea() <= maxArea),
                    () -> assertTrue(apartment.getPrice().compareTo(BigDecimal.valueOf(apartment.getArea()).multiply(minPricePerSquareMeter)) >= 0),
                    () -> assertTrue(apartment.getPrice().compareTo(BigDecimal.valueOf(apartment.getArea()).multiply(maxPricePerSquareMeter)) <= 0),
                    () -> assertTrue((apartment.getArea() >= minArea) && (apartment.getArea() <= maxArea)),
                    () -> assertTrue(apartment.getPrice().doubleValue() >= 3000.0 * minArea && apartment.getPrice().doubleValue() <= 12000.0 * maxArea)
            );
        }

    }

    @ParameterizedTest
    @CsvSource(value = {"50.0, 5000.0", "50.0, 300000.0", "50.0, 400000.0"})
    void should_GenerateCorrectApartment_When_CustomMinAreaMinPrice(Double minArea, Double minPrice) {
        //given
        RandomApartmentGenerator randomApartmentGenerator = new RandomApartmentGenerator(minArea, new BigDecimal(minPrice));
        double maxMultiplier = 4.0;
        //when
        Apartment apartment = randomApartmentGenerator.generate();
        //then
        assertTrue(apartment.getArea() >= minArea && apartment.getArea() <= minArea * maxMultiplier);
        assertTrue(apartment.getPrice().doubleValue() >= minPrice * minArea && apartment.getPrice().doubleValue() <= minPrice * minArea * maxMultiplier * maxMultiplier);
    }

}