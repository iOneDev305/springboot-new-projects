# API Development Guide

This guide provides step-by-step instructions for creating new APIs in our Spring Boot project.

## Project Structure
```
app/src/main/java/com/news/app/
├── domain/
│   ├── modal/         # Entity classes
│   └── repository/    # Repository interfaces
├── infrastructure/    # Security, configurations
├── web/
│   ├── controller/    # API controllers
│   └── dto/          # Data Transfer Objects
└── service/          # Business logic
```

## Step 1: Create Entity Class
1. Create a new Java class in `domain/modal/` package
2. Add necessary annotations:
   ```java
   @Entity
   @Table(name = "your_table_name")
   public class YourEntity {
       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Long id;
       
       // Add other fields with appropriate annotations
   }
   ```

## Step 2: Create Repository Interface
1. Create a new interface in `domain/repository/` package
2. Extend JpaRepository:
   ```java
   @Repository
   public interface YourRepository extends JpaRepository<YourEntity, Long> {
       // Add custom query methods if needed
   }
   ```

## Step 3: Create DTOs (Data Transfer Objects)
1. Create request/response DTOs in `web/dto/` package
2. Create separate packages for different features (e.g., `web/dto/user/`, `web/dto/news/`)
3. Example:
   ```java
   public class YourRequestDTO {
       private String field1;
       private String field2;
       // Add getters, setters, and constructors
   }
   ```

## Step 4: Create Service Layer
1. Create a service interface in `service/` package
2. Create service implementation:
   ```java
   @Service
   public class YourServiceImpl implements YourService {
       @Autowired
       private YourRepository repository;
       
       // Implement business logic
   }
   ```

## Step 5: Create Controller
1. Create a new controller in `web/controller/` package
2. Add necessary annotations and implement endpoints:
   ```java
   @RestController
   @RequestMapping("/api/your-endpoint")
   public class YourController {
       @Autowired
       private YourService service;
       
       // Implement API endpoints
   }
   ```

## Step 6: Add Security (if needed)
1. Add security annotations to controller methods
2. Configure security rules in `SecurityConfig` class

## Step 7: Add Validation
1. Add validation annotations to DTOs
2. Handle validation errors in controller

## Step 8: Add Documentation
1. Add Swagger/OpenAPI annotations
2. Document API endpoints, parameters, and responses

## Best Practices
1. **Naming Conventions**
   - Use clear, descriptive names
   - Follow Java naming conventions
   - Use consistent naming across layers

2. **Error Handling**
   - Use global exception handling
   - Return consistent error responses
   - Log errors appropriately

3. **Security**
   - Always validate input
   - Use appropriate security annotations
   - Never expose sensitive data

4. **Testing**
   - Write unit tests for services
   - Write integration tests for controllers
   - Test edge cases and error scenarios

## Example API Implementation

Here's a complete example of creating a new API:

1. **Entity**
```java
@Entity
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private String content;
    // Add other fields
}
```

2. **Repository**
```java
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByTitleContaining(String title);
}
```

3. **DTO**
```java
public class ArticleRequestDTO {
    private String title;
    private String content;
    // Add getters and setters
}
```

4. **Service**
```java
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleRepository repository;
    
    public Article createArticle(ArticleRequestDTO dto) {
        Article article = new Article();
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        return repository.save(article);
    }
}
```

5. **Controller**
```java
@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    @Autowired
    private ArticleService service;
    
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createArticle(
            @RequestParam("title") String title,
            @RequestParam("content") String content) {
        ArticleRequestDTO dto = new ArticleRequestDTO();
        dto.setTitle(title);
        dto.setContent(content);
        
        Article article = service.createArticle(dto);
        return ResponseEntity.ok(ApiResponse.success(article));
    }
}
```

## Testing Your API
1. Use Postman or similar tool to test endpoints
2. Test both success and error scenarios
3. Verify response formats and status codes
4. Test security requirements

## Common Issues and Solutions
1. **CORS Issues**
   - Configure CORS in SecurityConfig
   - Add appropriate headers

2. **Authentication Issues**
   - Verify token format
   - Check token expiration
   - Validate user permissions

3. **Validation Issues**
   - Check input constraints
   - Verify required fields
   - Handle validation errors properly

## Need Help?
- Check existing implementations in the codebase
- Review Spring Boot documentation
- Consult with team members
- Check error logs for debugging 