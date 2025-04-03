package org.tqs.deti.ua.MoliceiroUniRestaurants.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.tqs.deti.ua.MoliceiroUniRestaurants.models.Restaurant;
import org.tqs.deti.ua.MoliceiroUniRestaurants.repositories.RestaurantRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantService restaurantService;

    private Restaurant restaurant1;
    private Restaurant restaurant2;
    private final long RESTAURANT1_ID = 1L;
    private final long RESTAURANT2_ID = 2L;
    private final String RESTAURANT1_NAME = "Restaurant One";
    private final String RESTAURANT2_NAME = "Restaurant Two";

    @BeforeEach
    void setUp() {
        restaurant1 = new Restaurant();
        restaurant1.setId(RESTAURANT1_ID);
        restaurant1.setName(RESTAURANT1_NAME);
        restaurant1.setWeatherId(101);

        restaurant2 = new Restaurant();
        restaurant2.setId(RESTAURANT2_ID);
        restaurant2.setName(RESTAURANT2_NAME);
        restaurant2.setWeatherId(102);
    }

    @Test
    void getAllRestaurants_ShouldReturnAllRestaurants() {
        // Arrange
        when(restaurantRepository.findAll()).thenReturn(Arrays.asList(restaurant1, restaurant2));

        // Act
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();

        // Assert
        assertEquals(2, restaurants.size());
        assertTrue(restaurants.contains(restaurant1));
        assertTrue(restaurants.contains(restaurant2));
        verify(restaurantRepository, times(1)).findAll();
    }

    @Test
    void getRestaurant_WithValidId_ShouldReturnRestaurant() {
        // Arrange
        when(restaurantRepository.findById(RESTAURANT1_ID)).thenReturn(restaurant1);

        // Act
        Restaurant found = restaurantService.getRestaurant(RESTAURANT1_ID);

        // Assert
        assertNotNull(found);
        assertEquals(RESTAURANT1_ID, found.getId());
        assertEquals(RESTAURANT1_NAME, found.getName());
        verify(restaurantRepository, times(1)).findById(RESTAURANT1_ID);
    }

    @Test
    void getRestaurant_WithInvalidId_ShouldReturnNull() {
        // Arrange
        when(restaurantRepository.findById(999L)).thenReturn(null);

        // Act
        Restaurant found = restaurantService.getRestaurant(999L);

        // Assert
        assertNull(found);
        verify(restaurantRepository, times(1)).findById(999L);
    }

    @Test
    void getRestaurantByName_WithValidName_ShouldReturnRestaurant() {
        // Arrange
        when(restaurantRepository.findByName(RESTAURANT1_NAME)).thenReturn(restaurant1);

        // Act
        Restaurant found = restaurantService.getRestaurantByName(RESTAURANT1_NAME);

        // Assert
        assertNotNull(found);
        assertEquals(RESTAURANT1_ID, found.getId());
        assertEquals(RESTAURANT1_NAME, found.getName());
        verify(restaurantRepository, times(1)).findByName(RESTAURANT1_NAME);
    }

    @Test
    void createRestaurant_ShouldSaveAndReturnRestaurant() {
        // Arrange
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant1);

        // Act
        Restaurant created = restaurantService.createRestaurant(restaurant1);

        // Assert
        assertNotNull(created);
        assertEquals(RESTAURANT1_ID, created.getId());
        verify(restaurantRepository, times(1)).save(restaurant1);
    }

    @Test
    void updateRestaurant_WithExistingRestaurant_ShouldUpdateAndReturn() {
        // Arrange
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant1);
        restaurant1.setName("Updated Name");

        // Act
        Restaurant updated = restaurantService.updateRestaurant(restaurant1);

        // Assert
        assertNotNull(updated);
        assertEquals("Updated Name", updated.getName());
        verify(restaurantRepository, times(1)).save(restaurant1);
    }

    @Test
    void deleteRestaurant_WithValidId_ShouldDelete() {
        // Arrange
        when(restaurantRepository.findById(RESTAURANT1_ID)).thenReturn(restaurant1);
        doNothing().when(restaurantRepository).delete(restaurant1);

        // Act
        restaurantService.deleteRestaurant(RESTAURANT1_ID);

        // Assert
        verify(restaurantRepository, times(1)).findById(RESTAURANT1_ID);
        verify(restaurantRepository, times(1)).delete(restaurant1);
    }

    @Test
    void deleteRestaurant_WithInvalidId_ShouldDoNothing() {
        // Arrange
        when(restaurantRepository.findById(999L)).thenReturn(null);

        // Act
        restaurantService.deleteRestaurant(999L);

        // Assert
        verify(restaurantRepository, times(1)).findById(999L);
        verify(restaurantRepository, never()).delete(any());
    }

    @Test
    void deleteRestaurant_WhenRepositoryReturnsNull_ShouldNotThrowException() {
        // Arrange
        when(restaurantRepository.findById(anyLong())).thenReturn(null);

        // Act & Assert (should not throw exception)
        assertDoesNotThrow(() -> restaurantService.deleteRestaurant(999L));

        verify(restaurantRepository, never()).delete(any());
    }
}