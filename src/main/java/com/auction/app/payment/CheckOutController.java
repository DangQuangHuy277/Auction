package com.auction.app.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CheckOutController {
    private final CheckOutService checkOutService;
    @PostMapping("/orders/{orderId}")
    public ResponseEntity<?> payOrderById(@PathVariable("orderId") Long orderId, @RequestBody PaymentRequest paymentRequest){
        return ResponseEntity.ok(checkOutService.payOrderById(orderId, paymentRequest));
    }

    @GetMapping("/bidders/{bidderId}/orders")
    public ResponseEntity<?> getOrdersOfBidder(@PathVariable("bidderId") Long bidderId){
        List<Order> orders = checkOutService.getOrdersOfBidder(bidderId);
        return ResponseEntity.ok(orders);
    }

}
