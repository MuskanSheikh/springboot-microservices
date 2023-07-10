package com.spring.service.serviceImpl;


import com.spring.InventoryDTO;
import com.spring.OrderDTO;
import com.spring.OrderLineItemDTO;
import com.spring.entity.OrderLineItem;
import com.spring.entity.Orders;
import com.spring.repository.OrderLineItemRepository;
import com.spring.repository.OrderRepository;
import com.spring.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    private final OrderLineItemRepository orderLineItemRepository;

    private final WebClient.Builder webClientBuilder;

    @Override
    public OrderDTO placeOrder(OrderDTO orderDTO) {
        Orders orders = new Orders();
        orders.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItem> item = orderDTO.getOrderLineItemListDTO().stream().map(itemDto -> new OrderLineItem(itemDto.getId()
                , itemDto.getSkuCode(), itemDto.getPrice(), itemDto.getQuantity())).collect(Collectors.toList());
        orderLineItemRepository.saveAll(item);

        List<String> skuCodes = item.stream().map(OrderLineItem::getSkuCode).toList();

        // call inventory service and place order if product is in stock
        InventoryDTO[] result = webClientBuilder.build().get().uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryDTO[].class)
                .block();

        boolean allProductsInStock = Arrays.stream(result).allMatch(InventoryDTO::getIsInStock);

        if(allProductsInStock){
            orders = orderRepository.save(orders);
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Item is out of stock please try again later");
        }
        return getEntity(orders, item);
    }

    public static OrderDTO getEntity(Orders entity, List<OrderLineItem> item) {
        OrderDTO dto = new OrderDTO();
        dto.setId(entity.getId());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setOrderLineItemListDTO(item.stream().map(itemDto -> new OrderLineItemDTO(itemDto.getId()
                , itemDto.getSkuCode(), itemDto.getPrice(), itemDto.getQuantity())).collect(Collectors.toList()));
        return dto;
    }
}
