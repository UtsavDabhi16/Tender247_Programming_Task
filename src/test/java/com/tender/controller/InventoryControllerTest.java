package com.tender.controller;

import static org.mockito.Mockito.when;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tender.entity.InventoryItem;
import com.tender.entity.ItemStatus;
import com.tender.service.InventoryItemService;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {InventoryController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class InventoryControllerTest {
  @Autowired
  private InventoryController inventoryController;

  @MockBean
  private InventoryItemService inventoryItemService;

  /**
   * Method under test: {@link InventoryController#createItem(InventoryItem)}
   */
  @Test
  void testCreateItem() throws Exception {
    // Arrange
    InventoryItem inventoryItem = new InventoryItem();
    inventoryItem.setCategory("Category");
    inventoryItem.setId(1L);
    inventoryItem.setName("Name");
    inventoryItem.setQuantity(1);
    inventoryItem.setStatus(ItemStatus.AVAILABLE);
    when(inventoryItemService.createItem(Mockito.<InventoryItem>any())).thenReturn(inventoryItem);

    InventoryItem inventoryItem2 = new InventoryItem();
    inventoryItem2.setCategory("Category");
    inventoryItem2.setId(1L);
    inventoryItem2.setName("Name");
    inventoryItem2.setQuantity(1);
    inventoryItem2.setStatus(ItemStatus.AVAILABLE);
    String content = (new ObjectMapper()).writeValueAsString(inventoryItem2);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/inventory/items")
        .contentType(MediaType.APPLICATION_JSON)
        .content(content);

    // Act
    ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(inventoryController)
        .build()
        .perform(requestBuilder);

    // Assert
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content()
            .string("{\"id\":1,\"name\":\"Name\",\"category\":\"Category\",\"quantity\":1,\"status\":\"AVAILABLE\"}"));
  }

  /**
   * Method under test: {@link InventoryController#createItem(InventoryItem)}
   */
  @Test
  void testCreateItem2() throws Exception {
    // Arrange
    InventoryItem inventoryItem = new InventoryItem();
    inventoryItem.setCategory("Category");
    inventoryItem.setId(1L);
    inventoryItem.setName("Name");
    inventoryItem.setQuantity(1);
    inventoryItem.setStatus(ItemStatus.AVAILABLE);
    when(inventoryItemService.createItem(Mockito.<InventoryItem>any())).thenReturn(inventoryItem);

    InventoryItem inventoryItem2 = new InventoryItem();
    inventoryItem2.setCategory("Category");
    inventoryItem2.setId(1L);
    inventoryItem2.setName(null);
    inventoryItem2.setQuantity(1);
    inventoryItem2.setStatus(ItemStatus.AVAILABLE);
    String content = (new ObjectMapper()).writeValueAsString(inventoryItem2);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/inventory/items")
        .contentType(MediaType.APPLICATION_JSON)
        .content(content);

    // Act
    ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(inventoryController)
        .build()
        .perform(requestBuilder);

    // Assert
    actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
        .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
        .andExpect(MockMvcResultMatchers.content().string("Item name cannot be empty"));
  }

  /**
   * Method under test: {@link InventoryController#createItem(InventoryItem)}
   */
  @Test
  void testCreateItem3() throws Exception {
    // Arrange
    InventoryItem inventoryItem = new InventoryItem();
    inventoryItem.setCategory("Category");
    inventoryItem.setId(1L);
    inventoryItem.setName("Name");
    inventoryItem.setQuantity(1);
    inventoryItem.setStatus(ItemStatus.AVAILABLE);
    when(inventoryItemService.createItem(Mockito.<InventoryItem>any())).thenReturn(inventoryItem);

    InventoryItem inventoryItem2 = new InventoryItem();
    inventoryItem2.setCategory("Category");
    inventoryItem2.setId(1L);
    inventoryItem2.setName("");
    inventoryItem2.setQuantity(1);
    inventoryItem2.setStatus(ItemStatus.AVAILABLE);
    String content = (new ObjectMapper()).writeValueAsString(inventoryItem2);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/inventory/items")
        .contentType(MediaType.APPLICATION_JSON)
        .content(content);

    // Act
    ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(inventoryController)
        .build()
        .perform(requestBuilder);

    // Assert
    actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
        .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
        .andExpect(MockMvcResultMatchers.content().string("Item name cannot be empty"));
  }

  /**
   * Method under test: {@link InventoryController#getAllItems()}
   */
  @Test
  void testGetAllItems() throws Exception {
    // Arrange
    when(inventoryItemService.getAllItems()).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/inventory/items");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(inventoryController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("[]"));
  }
}
