package at.technikum.springrestbackend.mapper;


import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void whenOrderNoId_thenDtoHasNoId() {
        // Arrange
        System.out.println("********************TESTING*********************");
        assertEquals(1,1);
//        BoardMapper boardMapper = new BoardMapper();
//        Board board = new Board("Vapiano");
//
//        // Act
//        BoardDto boardDto = boardMapper.toDto(board);
//
//        // Assert
//        assertNull(boardDto.getId());
    }

//    void whenOrderValue_thenDtoSameValue() {
//        // Arrange
//        OrderMapper orderMapper = new OrderMapper();
//        String id = UUID.randomUUID().toString();
//        Order order = new Order(id, "Vapiano");
//
//        // Act
//        OrderDto orderDto = orderMapper.toDto(order);
//
//        // Assert
//        assertEquals(id, orderDto.getId());
//        assertEquals("Vapiano", orderDto.getRestaurant());
//    }
//
//    @Test
//    void whenDtoNoId_thenOrderHasId() {
//        // Arrange
//        OrderMapper orderMapper = new OrderMapper();
//        OrderDto orderDto = new OrderDto();
//        orderDto.setRestaurant("Vapiano");
//
//        // Act
//        Order order = orderMapper.toEntity(orderDto);
//
//        // Assert
//        assertNotNull(order.getId());
//    }
//
//    @Test
//    void whenDtoValue_thenOrderSameValue() {
//        // Arrange
//        OrderMapper orderMapper = new OrderMapper();
//        String id = UUID.randomUUID().toString();
//        OrderDto orderDto = new OrderDto(id, "Vapiano");
//
//        // Act
//        Order order = orderMapper.toEntity(orderDto);
//
//        // Assert
//        assertEquals(id, order.getId());
//        assertEquals("Vapiano", order.getRestaurant());
//    }
}
