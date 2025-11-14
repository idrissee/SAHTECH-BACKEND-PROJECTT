# Sahtech Product Scanning Troubleshooting Guide

## Issue: Product Scanning Recommendations Not Working

If you're experiencing issues with the product scanning feature not providing recommendations, follow these steps:

### 1. Verify FastAPI Service Status

The recommendation feature requires the FastAPI AI service to be running. To check:

```bash
cd SahtechAI
python check_fastapi.py
```

You should see: `✅ FastAPI service is running at http://192.168.1.69:8000`

If not, start the FastAPI service:

```bash
cd SahtechAI/fastapi_service
uvicorn main:app --reload --host 0.0.0.0 --port 8000
```

### 2. Check GROQ API Key

The FastAPI service requires a valid GROQ API key to function:

1. Create a file `SahtechAI/fastapi_service/.env` with:
   ```
   GROQ_API_KEY=your-groq-api-key
   API_KEY=sahtech-fastapi-secure-key-2025
   ```
2. Get a valid key from [https://console.groq.com/](https://console.groq.com/)

### 3. Check Spring Boot Configuration

The Spring Boot server needs to be correctly configured:

1. Ensure `ai.service.url` in `application.properties` points to the correct URL
2. Default is `http://192.168.1.69:8000` - change if your FastAPI is on a different machine/port

### 4. Check Connection Timeout

If the AI service is slow to respond:

1. The timeout is configured to 10 seconds
2. Verify there are no network issues between Spring Boot and FastAPI

### 5. Examining Logs

If still having issues:

1. Check Spring Boot logs for errors related to `RecommendationService` or `RestTemplate`
2. Check FastAPI logs for errors related to the `/predict` endpoint
3. Look for connection timeouts or other exceptions

### Recent Fixes Implemented:

1. Added proper error handling in RecommendationServiceImpl
2. Configured connection timeout for RestTemplate
3. Added fallback mechanism in the RecommendationController
4. Improved logging across all recommendation-related components

### If All Else Fails

Try the manual recommendation save endpoint:

```bash
curl -X POST http://localhost:8080/API/Sahtech/recommendation/save \
  -H "Content-Type: application/json" \
  -d '{
    "userId": "YOUR_USER_ID",
    "productId": "YOUR_PRODUCT_ID",
    "recommendation": "This is a manual recommendation text",
    "recommendationType": "caution"
  }'
```

This will create a recommendation without using the AI service. 