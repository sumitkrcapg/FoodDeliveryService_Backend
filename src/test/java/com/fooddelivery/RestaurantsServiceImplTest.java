package com.fooddelivery;

import com.fooddelivery.Exception.DuplicateRestaurantIDException;
import com.fooddelivery.Exception.InvalidRestaurantIdException;
import com.fooddelivery.Exception.NoSuchRestaurantIDException;
import com.fooddelivery.Repository.MenuItemsRepository;
import com.fooddelivery.Repository.RestaurantsRepository;
import com.fooddelivery.entity.Restaurants;
import com.fooddelivery.service.RestaurantsServiceImpl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RestaurantsServiceImplTest {

    @InjectMocks
    private RestaurantsServiceImpl restaurantsService;

    @Mock
    private RestaurantsRepository restaurantsRepository;

    /**
     * Test to verify that the `getAllRestaurants` method returns all restaurants from the repository.
     * It checks that the method does not return an empty list and correctly retrieves restaurant details.
     */
    @Test
    public void testGetAllRestaurants() {
        Restaurants restaurant = new Restaurants(1, "Tasty Bites", "123 Main St", "+1234567890");
        List<Restaurants> restaurants = List.of(restaurant);
        Mockito.when(restaurantsRepository.findAll()).thenReturn(restaurants);

        List<Restaurants> result = restaurantsService.getAllRestaurants();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Tasty Bites", result.get(0).getRestaurant_name());
    }

    /**
     * Test to verify that the `updateRestaurant` method successfully updates a restaurant's details
     * when a valid restaurant ID is provided. It checks that the restaurant's name is updated correctly.
     */
    @Test
    public void testUpdateRestaurant_Success() throws InvalidRestaurantIdException {
        Restaurants existingRestaurant = new Restaurants(1, "Tasty Bites", "123 Main St", "+1234567890");
        Restaurants updatedRestaurant = new Restaurants(1, "Tasty Burgers", "123 Main St", "+1234567890");

        Mockito.when(restaurantsRepository.findById(1)).thenReturn(Optional.of(existingRestaurant));
        Mockito.when(restaurantsRepository.save(existingRestaurant)).thenReturn(updatedRestaurant);

        Restaurants result = restaurantsService.updateRestaurant(updatedRestaurant);
        assertNotNull(result);
        assertEquals("Tasty Burgers", result.getRestaurant_name());
    }

    /**
     * Test to verify that an `InvalidRestaurantIdException` is thrown when trying to update a restaurant
     * with an invalid (non-existent) ID. It checks that the exception is correctly thrown when the restaurant ID is not found.
     */
    @Test
    public void testUpdateRestaurant_InvalidId() {
        Restaurants restaurant = new Restaurants(1, "Tasty Bites", "123 Main St", "+1234567890");
        Mockito.when(restaurantsRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(InvalidRestaurantIdException.class, () -> {
            restaurantsService.updateRestaurant(restaurant);
        });
    }

    /**
     * Test to verify that the `getRestaurantById` method correctly retrieves a restaurant by its ID when
     * the restaurant exists in the repository. It checks that the retrieved restaurant has the correct name.
     */
    @Test
    public void testGetRestaurantById_Found() throws NoSuchRestaurantIDException {
        Restaurants restaurant = new Restaurants(1, "Tasty Bites", "123 Main St", "+1234567890");
        Mockito.when(restaurantsRepository.findById(1)).thenReturn(Optional.of(restaurant));

        Restaurants result = restaurantsService.getRestaurantById(1);
        assertNotNull(result);
        assertEquals("Tasty Bites", result.getRestaurant_name());
    }

    /**
     * Test to verify that a `NoSuchRestaurantIDException` is thrown when trying to retrieve a restaurant
     * by an ID that does not exist in the repository. It checks that the exception is thrown as expected.
     */
    @Test
    public void testGetRestaurantById_NotFound() {
        Mockito.when(restaurantsRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NoSuchRestaurantIDException.class, () -> {
            restaurantsService.getRestaurantById(1);
        });
    }

    /**
     * Test to verify that the `addRestaurant` method successfully adds a new restaurant when the restaurant ID is unique.
     * It checks that the restaurant is saved correctly in the repository.
     */
    @Test
    public void testAddRestaurant_Success() {
        Restaurants restaurant = new Restaurants(1, "Tasty Bites", "123 Main St", "+1234567890");
        Mockito.when(restaurantsRepository.existsById(1)).thenReturn(false);
        Mockito.when(restaurantsRepository.save(restaurant)).thenReturn(restaurant);

        Restaurants result = restaurantsService.addRestaurant(restaurant);
        assertNotNull(result);
        assertEquals("Tasty Bites", result.getRestaurant_name());
    }

    /**
     * Test to verify that a `DuplicateRestaurantIDException` is thrown when trying to add a restaurant
     * with an ID that already exists in the repository. It checks that the exception is thrown as expected.
     */
    @Test
    public void testAddRestaurant_DuplicateId() {
        Restaurants restaurant = new Restaurants(1, "Tasty Bites", "123 Main St", "+1234567890");
        Mockito.when(restaurantsRepository.existsById(1)).thenReturn(true);

        assertThrows(DuplicateRestaurantIDException.class, () -> {
            restaurantsService.addRestaurant(restaurant);
        });
    }
}
