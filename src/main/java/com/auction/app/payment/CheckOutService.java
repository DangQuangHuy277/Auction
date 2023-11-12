package com.auction.app.payment;

import com.auction.app.exception.ResourceNotFoundException;
import com.auction.app.user.entity.Bidder;
import com.auction.app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckOutService {
    private final RestTemplate paymentTemplate;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public URI payOrderById(Long orderId, PaymentRequest paymentRequest) {
        Optional<Order> order = orderRepository.findById(orderId);
        return null;
    }

    public List<Order> getOrdersOfBidder(Long bidderId) {
        Bidder bidder = (Bidder) userRepository.findById(bidderId)
                .orElseThrow(() -> new ResourceNotFoundException("The Bidder is not found"));
        return bidder.getCart();
    }
}
