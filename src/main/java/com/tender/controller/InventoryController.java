package com.tender.controller;

import com.tender.entity.InventoryItem;
import com.tender.entity.ItemStatus;
import com.tender.entity.ItemStock;
import com.tender.service.InventoryItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);

    @Autowired
    private InventoryItemService itemService;

    // Endpoint for creating a new inventory item
    @PostMapping("/items")
    public ResponseEntity<?> createItem(@Valid @RequestBody InventoryItem item) {
        if (item.getName() == null || item.getName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Item name cannot be empty");
        }

        InventoryItem createdItem = itemService.createItem(item);
        logger.info("Item created: {}", createdItem);
        // Returning response with the created item
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
    }

    // Endpoint for updating item status by ID
    @PutMapping("/items/{id}/status")
    public ResponseEntity<?> updateItemStatus(@PathVariable Long id, @RequestParam ItemStatus status) {
        InventoryItem updatedItem = itemService.updateItemStatus(id, status);
        if (updatedItem != null) {
            logger.info("Item status updated: {}", updatedItem.getStatus());
            // Returning response with updated item
            return ResponseEntity.ok(updatedItem);
        } else {
            logger.warn("Item with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }


    // Endpoint for getting all inventory items
    @GetMapping("/items")
    public ResponseEntity<List<InventoryItem>> getAllItems() {
        // Getting all items using InventoryItemService
        List<InventoryItem> items = itemService.getAllItems();
        logger.info("Fetched {} items", items.size());
        // Returning response with list of items
        return ResponseEntity.ok(items);
    }

    // Endpoint for removing an item by ID
    @DeleteMapping("/items/{id}")
    public ResponseEntity<String> removeItem(@PathVariable Long id) {
        itemService.removeItem(id);
        logger.info("Item with ID {} removed successfully", id);
        // Returning response with status 204 (No Content)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Item removed successfully");
    }

    // Endpoint for getting the stock of items
    @GetMapping("/stock")
    public ResponseEntity<List<ItemStock>> getStock() {
        List<ItemStock> stockList = itemService.getStock();
        // Returning response with stock list
        return ResponseEntity.ok(stockList);
    }

}
