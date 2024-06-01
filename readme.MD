## Overview

The Image and Inventory Management API is a Spring Boot application that allows users to upload images and manage inventory items through HTTP requests.

## Features

- **Image Management:**
  - Upload images
  - Retrieve images by ID

- **Inventory Management:**
  - Create inventory items
  - Update inventory item status
  - Retrieve all inventory items
  - Remove inventory items
  - Get inventory item stock

## Requirements

To run this project, ensure you have the following installed:

- Java Development Kit (JDK) 17 or higher
- Maven
- MySQL Database

## Setup

1. **Clone the repository:**

   ```bash
   git clone https://github.com/UtsavDabhi16/Tender247_Programming_Task.git
   ```

2. **Navigate to the project directory:**

   ```bash
   cd Tender247_Programming_Task
   ```

3. **Configure the database:**
    - Create a MySQL database named `tender`.
    - Update the `application.properties` file in `src/main/resources` with your database connection details.

4. **Build the project:**

   ```bash
   mvn clean package
   ```

5. **Run the project:**

   ```bash
   java -jar target/<Jar_File_Path>.jar
   ```

## API Documentation

### Image Management

#### Upload Image

Upload an image file.

**Request:**
```bash
curl -X POST -F "file=@/path/to/image.jpg" http://localhost:8080/inventory/items/upload
```

**Response:**
The uploaded image is returned in the response body.

#### Get Image by ID

Retrieve an image by its ID.

**Request:**
```bash
curl http://localhost:8080/inventory/items/{id} --output image.jpg
```

Replace `{id}` with the ID of the image.

### Inventory Management

#### Create Inventory Item

Create a new inventory item.

**Request:**
```bash
curl -X POST -H "Content-Type: application/json" -d '{"name": "Item 1", "category": "Category", "quantity": 10, "status": "AVAILABLE"}' http://localhost:8080/inventory/items
```

**Response:**
The created inventory item details are returned in the response body.

#### Update Inventory Item Status

Update the status of an inventory item by its ID.

**Request:**
```bash
curl -X PUT http://localhost:8080/inventory/items/{id}/status?status=AVAILABLE
```

Replace `{id}` with the ID of the inventory item and `AVAILABLE` with the desired status.

#### Get All Inventory Items

Retrieve all inventory items.

**Request:**
```bash
curl http://localhost:8080/inventory/items
```

**Response:**
A list of all inventory items is returned in the response body.

#### Remove Inventory Item

Remove an inventory item by its ID.

**Request:**
```bash
curl -X DELETE http://localhost:8080/inventory/items/{id}
```

Replace `{id}` with the ID of the inventory item to be removed.

#### Get Inventory Item Stock

Retrieve the stock of all inventory items.

**Request:**
```bash
curl http://localhost:8080/inventory/stock
```

**Response:**
A list of item stock information is returned in the response body.


## Technologies Used

- Spring Boot
- MySQL
- Maven

```
