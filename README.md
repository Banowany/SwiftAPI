# SwiftAPI

## Overview
SwiftAPI is a Spring Boot application for managing SWIFT data. The application provides functionalities such as:
* retrieving details for a specific SWIFT code, 
* fetching SWIFT codes associated with a particular country, 
* adding new SWIFT codes, 
* and deleting existing ones. 

## Prerequisites
- Docker Compose

## Setup Instructions
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd SwiftAPI
   ```
2. Ensure the required `swift.csv` file is present in the root directory of the project with the following structure:

    ```
    COUNTRY ISO2 CODE,SWIFT CODE,CODE TYPE,NAME,ADDRESS,TOWN NAME,COUNTRY NAME,TIME ZONE
    ```

    Example:
    ```
    MC,BAERMCMCXXX,BIC11,BANK JULIUS BAER (MONACO) S.A.M.,"12 BOULEVARD DES MOULINS  MONACO, MONACO, 98000",MONACO,MONACO,Europe/Monaco
    ```

## Running Instructions
1. Start the application using Docker Compose
```bash
docker compose up -d
```
2. The application REST API will be avaible at http://localhost:8080
3. To stop the application
```bash
docker compose down
```

## Testing Instructions
1. Run the test inside the app container
```bash
docker compose exec app gradle test
```

## Performance Optimizations

To meet low-latency requirements, the following optimizations were implemented:

1. **Single Database Query per Request**: Each API request is designed to execute only one query to the database, minimizing overhead and improving response times.

2. **Indexing**: Indexes were created on specific columns frequently used in search operations to speed up query execution.

3. **Denormalized Table Design**: The database schema was partially denormalized to avoid costly table joins, reducing query complexity and execution time.

4. **SWIFT Code Optimization**: An additional column was added to store the first 8 characters of the SWIFT code. This column is indexed to enable fast lookups for headquarters and their branches.

These measures collectively ensure efficient data retrieval and improved application performance.