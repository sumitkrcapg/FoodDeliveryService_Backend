package com.fooddelivery;

import com.fooddelivery.Exception.DuplicateOrderIdException;
import com.fooddelivery.Exception.InvalidOrderIdException;
import com.fooddelivery.Exception.OrdersNotFoundException;
import com.fooddelivery.Repository.OrdersRepository;
import com.fooddelivery.entity.Orders;
import com.fooddelivery.service.OrdersServiceImpl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrdersServiceImplTest {

    @InjectMocks
    private OrdersServiceImpl ordersService;

    @Mock
    private OrdersRepository ordersRepository;

    /**
     * Test to verify that an exception is thrown when trying to place an order with a duplicate order ID.
     * This tests the `placeOrder` method when the order ID already exists in the repository.
     */
    @Test
    public void testPlaceOrder_DuplicateOrderId() {
        Orders order = new Orders(1, new Date(), "Pending");
        Mockito.when(ordersRepository.findById(1)).thenReturn(Optional.of(order));

        assertThrows(DuplicateOrderIdException.class, () -> {
            ordersService.placeOrder(order);
        });
    }

    /**
     * Test to verify that a new order is successfully placed when the order ID does not exist.
     * This tests the `placeOrder` method when the order ID is unique and not already in the repository.
     */
    @Test
    public void testPlaceOrder_Success() throws DuplicateOrderIdException {
        Orders order = new Orders(1, new Date(), "Pending");
        Mockito.when(ordersRepository.findById(1)).thenReturn(Optional.empty());
        Mockito.when(ordersRepository.save(order)).thenReturn(order);

        Orders result = ordersService.placeOrder(order);
        assertNotNull(result);
        assertEquals("Pending", result.getOrder_status());
    }

    /**
     * Test to verify that an order is successfully retrieved by its ID when it exists in the repository.
     * This tests the `getOrders` method when the order ID is valid and found in the repository.
     */
    @Test
    public void testGetOrders_Found() throws OrdersNotFoundException, InvalidOrderIdException {
        Orders order = new Orders(1, new Date(), "Pending");
        Mockito.when(ordersRepository.findById(1)).thenReturn(Optional.of(order));

        Orders result = ordersService.getOrders(1);
        assertNotNull(result);
        assertEquals("Pending", result.getOrder_status());
    }

    /**
     * Test to verify that an exception is thrown when trying to retrieve an order with a non-existent ID.
     * This tests the `getOrders` method when the order ID does not exist in the repository.
     */
    @Test
    public void testGetOrders_NotFound() {
        Mockito.when(ordersRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(OrdersNotFoundException.class, () -> {
            ordersService.getOrders(1);
        });
    }

    /**
     * Test to verify that an exception is thrown when trying to retrieve an order with an invalid ID.
     * This tests the `getOrders` method when the order ID is negative.
     */
    @Test
    public void testGetOrders_InvalidId() {
        assertThrows(OrdersNotFoundException.class, () -> {
            ordersService.getOrders(-1);
        });
    }

    /**
     * Test to verify that an order's status is successfully updated when the order ID is valid.
     * This tests the `updateOrderStatus` method when the order exists and is updated with a new status.
     */
    @Test
    public void testUpdateOrderStatus_Success() throws InvalidOrderIdException, OrdersNotFoundException {
        Orders order = new Orders(1, new Date(), "Pending");
        Mockito.when(ordersRepository.findById(1)).thenReturn(Optional.of(order));
        Mockito.when(ordersRepository.save(order)).thenReturn(order);

        Orders result = ordersService.updateOrderStatus(1, "Delivered");
        assertNotNull(result);
        assertEquals("Delivered", result.getOrder_status());
    }

    /**
     * Test to verify that an exception is thrown when trying to update an order status with an invalid ID.
     * This tests the `updateOrderStatus` method when the order ID is negative.
     */
    @Test
    public void testUpdateOrderStatus_InvalidId() {
        assertThrows(InvalidOrderIdException.class, () -> {
            ordersService.updateOrderStatus(-1, "Delivered");
        });
    }

    /**
     * Test to verify that an exception is thrown when trying to update the status of a non-existent order.
     * This tests the `updateOrderStatus` method when the order ID does not exist in the repository.
     */
    @Test
    public void testUpdateOrderStatus_NotFound() {
        Mockito.when(ordersRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(OrdersNotFoundException.class, () -> {
            ordersService.updateOrderStatus(1, "Delivered");
        });
    }


    /**
     * Test to verify that an exception is thrown when trying to cancel an order with an invalid ID.
     * This tests the `cancelOrder` method when the order ID is negative.
     */
    @Test
    public void testCancelOrder_InvalidId() {
        assertThrows(InvalidOrderIdException.class, () -> {
            ordersService.cancelOrder(-1);
        });
    }

    /**
     * Test to verify that an exception is thrown when attempting to cancel an order that does not exist.
     * This tests the `cancelOrder` method when the order ID is valid but the order is not found in the repository.
     */
    @Test
    public void testCancelOrder_NotFound() {
        Mockito.when(ordersRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(InvalidOrderIdException.class, () -> {
            ordersService.cancelOrder(1);
        });
    }
}
