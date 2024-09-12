package com.fooddelivery;

import com.fooddelivery.Exception.CustomerNotFoundException;
import com.fooddelivery.Exception.DuplicateCustomerIDException;
import com.fooddelivery.Repository.CustomersRepository;
import com.fooddelivery.Repository.OrdersRepository;
import com.fooddelivery.Repository.RatingsRepository;
import com.fooddelivery.dto.Role;
import com.fooddelivery.entity.Customers;
import com.fooddelivery.entity.Orders;
import com.fooddelivery.entity.Ratings;
import com.fooddelivery.service.CustomersServiceImpl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CustomersServiceImplTest {

    @InjectMocks
    private CustomersServiceImpl customersService;

    @Mock
    private CustomersRepository customersRepository;

    @Mock
    private OrdersRepository ordersRepository;

    @Mock
    private RatingsRepository ratingsRepository;

    /**
     * Test to verify that an exception is thrown when no customers are found.
     * This tests the behavior of the `getAllCustomers` method when the repository returns an empty list.
     */
    @Test
    public void testGetAllCustomers_NoCustomersFound() {
        Mockito.when(customersRepository.findAll()).thenReturn(new ArrayList<>());
        
        Exception exception = assertThrows(CustomerNotFoundException.class, () -> {
            customersService.getAllCustomers();
        });

        assertEquals("No customers found", exception.getMessage());
    }

    /**
     * Test to verify that customers are correctly retrieved from the repository.
     * This tests the `getAllCustomers` method when the repository returns a list with one customer.
     */
    @Test
    public void testGetAllCustomers_Success() {
        Customers customer = new Customers(1, "John Smith", "john@example.com", "+1234567890", "123456", Role.customer);
        List<Customers> customers = List.of(customer);
        
        Mockito.when(customersRepository.findAll()).thenReturn(customers);
        
        List<Customers> result = customersService.getAllCustomers();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("John Smith", result.get(0).getCustomer_name());
    }

    /**
     * Test to verify that an exception is thrown when trying to add a customer with an existing ID.
     * This tests the `addCustomer` method when the customer ID already exists in the repository.
     */
    @Test
    public void testAddCustomer_DuplicateCustomerId() {
        Customers customer = new Customers(1, "John Smith", "john@example.com", "+1234567890", "123456", Role.customer);
        Mockito.when(customersRepository.findById(1)).thenReturn(Optional.of(customer));
        
        Exception exception = assertThrows(DuplicateCustomerIDException.class, () -> {
            customersService.addCustomer(customer);
        });

        assertEquals("Customer with Id 1 already exists", exception.getMessage());
    }

    /**
     * Test to verify that a new customer is successfully added when the customer ID does not exist.
     * This tests the `addCustomer` method when the customer ID is unique and not already in the repository.
     */
    @Test
    public void testAddCustomer_Success() throws DuplicateCustomerIDException {
        Customers customer = new Customers(1, "John Smith", "john@example.com", "+1234567890", "123456", Role.customer);
        Mockito.when(customersRepository.findById(1)).thenReturn(Optional.empty());
        Mockito.when(customersRepository.save(customer)).thenReturn(customer);
        
        Customers result = customersService.addCustomer(customer);
        assertNotNull(result);
        assertEquals("John Smith", result.getCustomer_name());
    }

    /**
     * Test to verify that a customer is correctly retrieved by their ID.
     * This tests the `getCustomerById` method when the customer ID exists in the repository.
     */
    @Test
    public void testGetCustomerById_Found() {
        Customers customer = new Customers(1, "John Smith", "john@example.com", "+1234567890", "123456", Role.customer);
        Mockito.when(customersRepository.findById(1)).thenReturn(Optional.of(customer));
        
        Customers result = customersService.getCustomerById(1);
        assertNotNull(result);
        assertEquals("John Smith", result.getCustomer_name());
    }

    /**
     * Test to verify that null is returned when trying to retrieve a customer with a non-existent ID.
     * This tests the `getCustomerById` method when the customer ID does not exist in the repository.
     */
    @Test
    public void testGetCustomerById_NotFound() {
        Mockito.when(customersRepository.findById(1)).thenReturn(Optional.empty());
        
        Customers result = customersService.getCustomerById(1);
        assertNull(result);
    }

    /**
     * Test to verify that a customer's details are successfully updated.
     * This tests the `updateCustomer` method when the customer details are updated and saved correctly.
     */
    @Test
    public void testUpdateCustomer() {
        Customers customer = new Customers(1, "John Smith", "john@example.com", "1234567890", "123456", Role.customer);
        Mockito.when(customersRepository.save(customer)).thenReturn(customer);
        
        Customers result = customersService.updateCustomer(customer);
        assertNotNull(result);
        assertEquals("John Smith", result.getCustomer_name());
    }

    /**
     * Test to verify that a customer is successfully deleted when the customer ID exists.
     * This tests the `deleteCustomerById` method when the customer is found and deleted from the repository.
     */
    @Test
    public void testDeleteCustomerById_Found() {
        Customers customer = new Customers(1, "John Smith", "john@example.com", "1234567890", "123456", Role.customer);
        Mockito.when(customersRepository.findById(1)).thenReturn(Optional.of(customer));
        
        boolean result = customersService.deleteCustomerById(1);
        assertTrue(result);
        Mockito.verify(customersRepository, Mockito.times(1)).delete(customer);
    }

    /**
     * Test to verify that a customer is not deleted when the customer ID does not exist.
     * This tests the `deleteCustomerById` method when the customer is not found in the repository.
     */
    @Test
    public void testDeleteCustomerById_NotFound() {
        Mockito.when(customersRepository.findById(1)).thenReturn(Optional.empty());
        
        boolean result = customersService.deleteCustomerById(1);
        assertFalse(result);
        Mockito.verify(customersRepository, Mockito.never()).delete(Mockito.any());
    }

    /**
     * Test to verify that orders for a specific customer are correctly retrieved.
     * This tests the `getOrdersByCustomerId` method when there are orders associated with the customer.
     */
    @Test
    public void testGetOrdersByCustomerId() {
        Orders order = new Orders();
        List<Orders> orders = List.of(order);
        Mockito.when(ordersRepository.findOrdersByCustomerId(1)).thenReturn(orders);
        
        List<Orders> result = customersService.getOrdersByCustomerId(1);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }
}
