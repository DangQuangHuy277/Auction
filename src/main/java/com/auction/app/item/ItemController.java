package com.auction.app.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/sellers/{sellerId}/items")
    public ResponseEntity<?> getItemsBySellerId(@PathVariable("sellerId") Long sellerId) {
        List<Item> sellerInventory = itemService.getItemsBySellerId(sellerId);
        return ResponseEntity.ok(sellerInventory);
    }

    @PostMapping("/sellers/{sellerId}/items")
    public ResponseEntity<?> addItemForSeller(@PathVariable("sellerId") Long sellerId,@Valid @RequestBody Item item) {
        Item returnItem = itemService.addNewItemForSeller(sellerId, item);
        return ResponseEntity.created(URI.create("/api/v1/sellers/" + sellerId + "/items/" + returnItem.getId()))
                .body(returnItem);
    }

    @PatchMapping("/items/{itemId}")
    public ResponseEntity<?> updateItem(@PathVariable("itemId")Long itemId, @RequestBody Item patchItem){
        Item returnItem = itemService.updateItemById(itemId, patchItem);
        return ResponseEntity.ok(returnItem);
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<?>  deleteItem(@PathVariable("itemId") Long itemId){
        itemService.deleteItemById(itemId);
        return ResponseEntity.noContent().build();
    }
}
