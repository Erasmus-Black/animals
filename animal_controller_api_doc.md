
# AnimalController API Documentation

### Base URL: `/animals`

## Endpoints:

### 1. GET /animals/cat
- **Description**: Fetches a number of cat images from the Cat API (`https://api.thecatapi.com/v1/images/search`) and stores them in the database.
- **Parameters**:
  - `num` (Optional): The number of cat images to fetch. Default value is 1.
- **Example Request**: 
  ```http
  GET /animals/cat?num=3
  ```
- **Response**:
  - **200 OK**: A string confirming the number of cat images grabbed and stored in the database.
  - Example: `"Grabbed 3 images of cats, stored in the database"`

### 2. GET /animals/dog
- **Description**: Fetches a dog image from the Random Dog API (`https://random.dog/woof.json`) and stores it in the database.
- **Parameters**:
  - `num` (Optional): The number of dog images to fetch. Default value is 1.
- **Example Request**: 
  ```http
  GET /animals/dog?num=2
  ```
- **Response**:
  - **200 OK**: A string confirming the number of dog images grabbed and stored in the database.
  - Example: `"Grabbed 2 images of dogs and stored in the database"`

### 3. GET /animals/lastimage
- **Description**: Fetches the last image stored in the database.
- **Response**:
  - **200 OK**: The image file in the form of a `ResponseEntity<Resource>`.

### 4. GET /animals
- **Description**: A basic endpoint that returns a greeting message.
- **Response**:
  - **200 OK**: `"Hello World! Welcome to animal pictures! Enjoy!"`

## Dependencies
This controller uses `AnimalLogic` to fetch and store animal images from external APIs.

## Error Handling
- If there is an issue with fetching images (e.g., network error), the method will throw an exception and the request may fail with an appropriate error message.
