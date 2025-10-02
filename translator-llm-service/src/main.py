from fastapi import FastAPI, HTTPException, Body
from pydantic import BaseModel
from typing import Optional, Dict, List
import requests
import json
import time
import os
import threading
from pyngrok import ngrok
import uvicorn

app = FastAPI(
    title="Ru-LLM Переводчик технических текстов",
    description="Профессиональный переводчик для инженеров данных с сохранением технических терминов",
    version="2.0.0",
    docs_url="/docs",
    redoc_url="/redoc"
)

# Конфигурация
OLLAMA_URL = "http://localhost:11434/api"
MODEL_NAME = "ru-analyst"

# 🎯 СИСТЕМНЫЙ ПРОМПТ ДЛЯ ПРОФЕССИОНАЛЬНОГО ПЕРЕВОДА
SYSTEM_PROMPT = """Ты - профессиональный переводчик-аналитик для инженера данных.
Твоя задача - переводить технические требования между русским и английским, сохраняя точность технических терминов.

ПРАВИЛА ПЕРЕВОДА:
1. Технические термины (DDL, ETL, CSV, JSON, PostgreSQL, ClickHouse, HDFS, Airflow и т.д.) НЕ переводить
2. Сохранять структуру данных и форматирование
3. Деловые требования переводить точно
4. Все дата-инженерские концепции оставлять на языке оригинала если это технический термин

НАПРАВЛЕНИЯ ПЕРЕВОДА:
- Русский → Английский: для передачи в специализированные модели
- Английский → Русский: для ответов пользователю от моделей например

Примеры перевода RU → EN:
- "создай таблицу users с полями id и name" → "create users table with id and name fields"
- "настроить ETL процесс из CSV в ClickHouse" → "setup ETL process from CSV to ClickHouse"

Примеры перевода EN → RU:
- "create a daily DAG for data pipeline" → "создай ежедневный DAG для пайплайна данных"
- "recommend database for time-series analytics" → "рекомендуй базу данных для аналитики временных рядов"
"""


# 📊 МОДЕЛИ ДАННЫХ
class TranslationRequest(BaseModel):
    text: str
    context_type: str = "general"


class TranslationResponse(BaseModel):
    original_text: str
    translated_text: str
    success: bool


class AnalysisRequest(BaseModel):
    query: str
    context: Optional[dict] = None


class AnalysisResponse(BaseModel):
    russian_response: str
    technical_content: str
    status: str
    model_used: str


class HealthResponse(BaseModel):
    status: str
    model: str
    requests_processed: int = 0
    ollama_ready: bool = False
    model_loaded: bool = False


class TestRequest(BaseModel):
    test_prompt: str = "Привет! Ответь коротко на русском кто ты."


# 📈 СТАТИСТИКА
request_counter = 0


def wait_for_model_ready(timeout=120):
    """Ждет пока модель будет готова"""
    start_time = time.time()
    while time.time() - start_time < timeout:
        try:
            response = requests.get(f"{OLLAMA_URL}/tags", timeout=5)
            if response.status_code == 200:
                models = response.json().get("models", [])
                for model in models:
                    if model.get("name", "").startswith(MODEL_NAME):
                        print(f"✅ Модель {MODEL_NAME} загружена!")
                        return True
            print("⏳ Ожидаем загрузку модели...")
            time.sleep(5)
        except Exception as e:
            print(f"⚠️ Ошибка проверки модели: {e}")
            time.sleep(5)
    print("❌ Таймаут ожидания модели")
    return False


def call_ollama_translate(text: str, direction: str) -> str:
    """Вызов модели для перевода с системным промптом"""
    global request_counter
    request_counter += 1

    if direction == "ru_en":
        user_prompt = f"Переведи следующий русский технический текст на английский:\n\n{text}"
    else:  # en_ru
        user_prompt = f"Переведи следующий английский технический текст на русский:\n\n{text}"

    try:
        response = requests.post(
            f'{OLLAMA_URL}/generate',
            json={
                'model': MODEL_NAME,
                'prompt': user_prompt,
                'system': SYSTEM_PROMPT,
                'stream': False,
                'options': {
                    'temperature': 0.1,
                    'top_p': 0.9,
                    'num_predict': 1024
                }
            },
            timeout=300
        )

        if response.status_code == 200:
            result = response.json()
            translated_text = result.get('response', '').strip()

            # Очищаем ответ от возможных пояснений
            lines = translated_text.split('\n')
            clean_lines = []
            for line in lines:
                line = line.strip()
                if line and not any(word in line.lower() for word in ['перевод:', 'translation:', 'ответ:', 'answer:']):
                    clean_lines.append(line)

            if clean_lines:
                return ' '.join(clean_lines)
            return translated_text
        else:
            return f"Ошибка API: {response.status_code}"

    except Exception as e:
        return f"Ошибка соединения: {str(e)}"


def call_ollama_sync(prompt: str) -> str:
    """Синхронный вызов Ollama для анализа"""
    global request_counter
    request_counter += 1

    try:
        response = requests.post(
            f"{OLLAMA_URL}/generate",
            json={
                "model": MODEL_NAME,
                "prompt": prompt,
                "stream": False,
                "options": {"temperature": 0.1}
            },
            timeout=60
        )
        if response.status_code == 200:
            return response.json().get("response", "Нет ответа")
        else:
            return f"Ошибка API: {response.status_code}"
    except Exception as e:
        return f"Ошибка запроса: {str(e)}"


# 📍 ЭНДПОИНТЫ ПЕРЕВОДА
@app.post("/ru-to-en", response_model=TranslationResponse)
async def russian_to_english(request: TranslationRequest):
    """Перевод с русского на английский"""
    if not wait_for_model_ready():
        raise HTTPException(status_code=503, detail="Модель еще загружается")

    translated_text = call_ollama_translate(request.text, "ru_en")
    success = not translated_text.startswith("Ошибка")

    return TranslationResponse(
        original_text=request.text,
        translated_text=translated_text,
        success=success
    )


@app.post("/en-to-ru", response_model=TranslationResponse)
async def english_to_russian(request: TranslationRequest):
    """Перевод с английского на русский"""
    if not wait_for_model_ready():
        raise HTTPException(status_code=503, detail="Модель еще загружается")

    translated_text = call_ollama_translate(request.text, "en_ru")
    success = not translated_text.startswith("Ошибка")

    return TranslationResponse(
        original_text=request.text,
        translated_text=translated_text,
        success=success
    )


# 📍 ЭНДПОИНТЫ АНАЛИЗА (оригинальные)
@app.post("/analyze", response_model=AnalysisResponse)
async def analyze_request(request: AnalysisRequest):
    """Основной метод анализа"""
    try:
        if not wait_for_model_ready():
            return AnalysisResponse(
                russian_response="Модель еще загружается, попробуйте через 30 секунд",
                technical_content="",
                status="model_loading",
                model_used=MODEL_NAME
            )

        prompt = f"Пользователь спрашивает: {request.query}. Ответь на русском ясно и кратко."
        model_response = call_ollama_sync(prompt)

        return AnalysisResponse(
            russian_response=model_response,
            technical_content="Техническая часть будет сгенерирована специализированными моделями",
            status="success",
            model_used=MODEL_NAME
        )
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Ошибка модели: {str(e)}")


@app.post("/test-model")
async def test_model(request: TestRequest):
    """Тестовый вызов модели"""
    try:
        if not wait_for_model_ready():
            return {
                "success": False,
                "error": "Модель еще не загружена. Подождите 30-60 секунд.",
                "model_status": "loading"
            }

        model_response = call_ollama_sync(request.test_prompt)

        return {
            "success": True,
            "model_response": model_response,
            "model": MODEL_NAME,
            "prompt": request.test_prompt
        }
    except Exception as e:
        return {"success": False, "error": str(e)}


# 📍 СЛУЖЕБНЫЕ ЭНДПОИНТЫ
@app.get("/health", response_model=HealthResponse)
async def health_check():
    """Проверка здоровья сервиса"""
    try:
        response = requests.get(f"{OLLAMA_URL}/tags", timeout=10)
        ollama_ready = response.status_code == 200

        models_loaded = False
        if ollama_ready:
            models = response.json().get("models", [])
            models_loaded = any(model.get("name", "").startswith(MODEL_NAME) for model in models)

        return HealthResponse(
            status="healthy" if (ollama_ready and models_loaded) else "degraded",
            model=MODEL_NAME,
            requests_processed=request_counter,
            ollama_ready=ollama_ready,
            model_loaded=models_loaded
        )
    except Exception as e:
        return HealthResponse(
            status="unhealthy",
            model=MODEL_NAME,
            requests_processed=request_counter,
            ollama_ready=False,
            model_loaded=False
        )


@app.get("/model-info")
async def model_info():
    """Информация о модели"""
    try:
        response = requests.get(f"{OLLAMA_URL}/tags")
        models = response.json().get("models", [])

        model_info = None
        for model in models:
            if model.get("name", "").startswith(MODEL_NAME):
                model_info = model
                break

        return {
            "model_name": MODEL_NAME,
            "model_details": model_info,
            "total_models": len(models),
            "ollama_status": "running",
            "requests_processed": request_counter
        }
    except Exception as e:
        return {"error": str(e)}


@app.get("/")
async def root():
    return {
        "message": "Ru-LLM Переводчик технических текстов работает!",
        "description": "Профессиональный переводчик для инженеров данных с сохранением технических терминов",
        "endpoints": {
            "translation_ru_en": "POST /ru-to-en",
            "translation_en_ru": "POST /en-to-ru",
            "analysis": "POST /analyze",
            "test_model": "POST /test-model",
            "health": "GET /health",
            "model_info": "GET /model-info",
            "docs": "GET /docs"
        },
        "example_usage": {
            "ru_to_en": {
                "text": "создай таблицу users с полями id и name",
                "context_type": "sql"
            },
            "en_to_ru": {
                "text": "create a daily DAG for ETL pipeline",
                "context_type": "dag"
            }
        }
    }


if __name__ == "__main__":
    # Запуск ngrok (раскомментируйте если нужно)
    # public_url = setup_ngrok()

    uvicorn.run(app, host="0.0.0.0", port=8080)