package com.auction.app.item;

import com.auction.app.user.entity.Seller;
import com.auction.app.user.repository.UserRepository;
import com.auction.app.utils.exception.ResourceNotFoundException;
import com.auction.app.utils.exception.WrongTypeEntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public List<Item> getItemsBySellerId(Long sellerId) {
        try {
            Seller seller = (Seller) userRepository.findById(sellerId).
                    orElseThrow(() -> new ResourceNotFoundException("The user is not found"));
            return seller.getItemInventory();
        } catch (ClassCastException ex) {
            throw new WrongTypeEntityException("This user is not a seller");
        }
    }

    public Item addNewItemForSeller(Long sellerId, Item item) {
        try {
            Seller seller = (Seller) userRepository.findById(sellerId).
                    orElseThrow(() -> new ResourceNotFoundException("The user is not found"));
            item.setStatus(Item.Status.IN_INVENTORY);
            seller.getItemInventory().add(item);
            item = itemRepository.save(item);
            userRepository.save(seller);
            return item;
        } catch (ClassCastException ex) {
            throw new WrongTypeEntityException("This user is not a seller");
        }
    }

    public Item updateItemById(Long itemId, Item patchItem) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("The item is not found"));
        for (Field field : patchItem.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object fieldValue = field.get(patchItem);
                if (fieldValue == null) continue;
                field.set(item, fieldValue);
            } catch (IllegalAccessException e) {
                System.err.println(e.getMessage());
            }
        }
        return itemRepository.save(item);
    }

    public void deleteItemById(Long itemId) {

        itemRepository.deleteById(itemId);
    }
}
