package com.realestateapp;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApartmentRaterTest {
    @Nested
    class ApartmentRateTests {
        @ParameterizedTest
        @CsvSource(value = { "50.0, 5000.0, 0", "50.0, 300000.0, 1", "50.0, 400000.0, 2"})
        void should_ReturnCorrectRating_When_Correct_Apartment(Double area, Double price, int expectedRating){
            //given
            Apartment apartment = new Apartment(area, new BigDecimal(price));
            //when
            int rating = ApartmentRater.rateApartment(apartment);
            //then
            assertEquals(expectedRating, rating);
        }

        @Test
        void should_ReturnErrorValue_When_IncorrectApartment() {

            Apartment apartment = new Apartment(0.0, new BigDecimal("0.0"));

            int rating = ApartmentRater.rateApartment(apartment);

            assertEquals(-1, rating);

        }

    }

    @Nested
    class ApartmentAverageRatingTests {
        @Test
        void should_CalculateAverageRating_When_CorrectApartmentList() {

            List<Apartment> apartmentList = new ArrayList<>();

            apartmentList.add(new Apartment(50.0, new BigDecimal("5000.0")));
            apartmentList.add(new Apartment(50.0, new BigDecimal("300000.0")));
            apartmentList.add(new Apartment(50.0, new BigDecimal("400000.0")));

            double averageRating = ApartmentRater.calculateAverageRating(apartmentList);

            assertEquals(1.0, averageRating);

        }

        @Test
        void should_ThrowException_When_EmptyApartmentList() {

            List<Apartment> apartmentList = new ArrayList<>();

            // when
            Executable executable = () -> ApartmentRater.calculateAverageRating(apartmentList);

            assertThrows(RuntimeException.class, executable);

        }
    }




}