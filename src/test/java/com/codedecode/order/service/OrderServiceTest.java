package com.codedecode.order.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import com.sb.order.dto.OrderDTO;
import com.sb.order.dto.OrderDTOFromFE;
import com.sb.order.dto.UserDTO;
import com.sb.order.entity.Order;
import com.sb.order.mapper.OrderMapper;
import com.sb.order.repo.OrderRepo;
import com.sb.order.service.OrderService;
import com.sb.order.service.SequenceGenerator;

class OrderServiceTest {

    @Mock
    private OrderRepo orderRepo;

    @Mock
    private SequenceGenerator sequenceGenerator;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveOrderInDb_shouldSaveOrderAndReturnOrderDTO() {
        // Arrange
        OrderDTOFromFE orderDetails = new OrderDTOFromFE();
        Integer newOrderId = 1;
        UserDTO userDTO = new UserDTO();
        Order orderToBeSaved = new Order(newOrderId, orderDetails.getFoodItemsList(), orderDetails.getRestaurant(), userDTO);
        OrderDTO orderDTOExpected = OrderMapper.INSTANCE.mapOrderToOrderDTO(orderToBeSaved);

        when(sequenceGenerator.generateNextOrderId()).thenReturn(newOrderId);
        when(restTemplate.getForObject(anyString(), eq(UserDTO.class))).thenReturn(userDTO);
        when(orderRepo.save(orderToBeSaved)).thenReturn(orderToBeSaved);

        // Act
        OrderDTO orderDTOActual = orderService.saveOrderInDb(orderDetails);

        // Assert
        verify(sequenceGenerator, times(1)).generateNextOrderId();
        assertDoesNotThrow(() -> orderService.saveOrderInDb(orderDetails));
    }
}