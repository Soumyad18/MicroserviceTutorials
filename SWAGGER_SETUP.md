# Swagger/OpenAPI Setup for Microservices

This project now includes comprehensive Swagger/OpenAPI documentation for all microservices. Each service has been configured with detailed API documentation and can be accessed through their respective Swagger UI interfaces.

## Services with Swagger Documentation

### 1. Question Service
- **Port**: 8080 (default)
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs
- **Description**: Manages question operations including CRUD operations, category filtering, and quiz generation

### 2. Quiz Service
- **Port**: 8090
- **Swagger UI**: http://localhost:8090/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8090/api-docs
- **Description**: Handles quiz creation, retrieval, and submission with score calculation

### 3. API Gateway
- **Port**: 8765
- **Swagger UI**: http://localhost:8765/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8765/api-docs
- **Description**: Routes requests to appropriate microservices

### 4. Service Registry (Eureka)
- **Port**: 8761
- **URL**: http://localhost:8761
- **Description**: Service discovery and registration (no Swagger needed)

## Features Added

### Swagger Annotations
- **@Tag**: Groups API endpoints by functionality
- **@Operation**: Describes each API endpoint with summary and description
- **@Parameter**: Documents request parameters with descriptions
- **@ApiResponses**: Documents all possible response codes and their meanings
- **@Schema**: References model classes for request/response documentation

### Configuration
- **SpringDoc OpenAPI 3**: Modern replacement for Swagger 2
- **Custom API Information**: Service-specific titles, descriptions, and contact details
- **Server Configuration**: Development server URLs for each service
- **UI Customization**: Sorted operations and tags for better navigation

## Dependencies Added

### Maven Dependencies
```xml
<!-- For Question and Quiz Services -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.1.0</version>
</dependency>

<!-- For API Gateway (WebFlux) -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webflux-ui</artifactId>
    <version>2.1.0</version>
</dependency>
```

### Configuration Properties
```properties
# Swagger/OpenAPI Configuration
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.swagger-ui.operationsSorter=method
springdoc.swagger-ui.tagsSorter=alpha
```

## How to Test

1. **Start the services** in the following order:
   - Service Registry (Eureka)
   - Question Service
   - Quiz Service
   - API Gateway

2. **Access Swagger UI** for each service using the URLs above

3. **Test API endpoints** directly from the Swagger UI interface

4. **View API documentation** in a user-friendly, interactive format

## Benefits

- **Interactive Testing**: Test APIs directly from the browser
- **Comprehensive Documentation**: All endpoints, parameters, and responses documented
- **Developer Experience**: Easy to understand and use APIs
- **API Discovery**: Developers can explore available endpoints
- **Request/Response Examples**: Clear examples for each endpoint

## Notes

- Swagger UI is automatically generated based on the annotations
- All endpoints are properly categorized and documented
- Response schemas reference the actual model classes
- Error responses are documented with appropriate HTTP status codes
- The documentation is always in sync with the actual code
