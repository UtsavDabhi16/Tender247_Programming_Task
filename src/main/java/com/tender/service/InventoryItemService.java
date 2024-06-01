package com.tender.service;

import com.tender.entity.InventoryItem;
import com.tender.entity.ItemStatus;
import com.tender.entity.ItemStock;
import com.tender.repository.InventoryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryItemService {
    @Autowired
    private InventoryItemRepository itemRepository;

    // Method to create a new inventory item
    public InventoryItem createItem(InventoryItem item) {
        return itemRepository.save(item);
    }

    // Method to update item status by ID
    public InventoryItem updateItemStatus(Long id, ItemStatus status) {
        InventoryItem item = itemRepository.findById(id).orElse(null);
        if (item != null) {
            item.setStatus(status);
            itemRepository.save(item);
        }
        return item;
    }

    // Method to get all inventory items
    public List<InventoryItem> getAllItems() {
        return itemRepository.findAll();
    }

    // Method to remove an item by ID
    public void removeItem(Long id) {
        itemRepository.deleteById(id);
    }

    // Method to get the stock of items
    public List<ItemStock> getStock() {
        // Retrieve all items from the database
        List<InventoryItem> items = itemRepository.findAll();
        List<ItemStock> stockList = new ArrayList<>();
        for (InventoryItem item : items) {
            // Add item stock information (name and quantity) to the stock list
            stockList.add(new ItemStock(item.getName(), item.getQuantity()));
        }
        return stockList;
    }
}
