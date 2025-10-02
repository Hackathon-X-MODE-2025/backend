FROM python:3.11-slim
WORKDIR /app
RUN apt-get update && apt-get install -y curl
COPY requirements.txt .
RUN pip install -r requirements.txt
RUN curl -fsSL https://ollama.ai/install.sh | sh

RUN mkdir -p /root/.ollama/models
COPY models/mradermacher/Saiga-llama3-L3-Stheno-8B-v3.2-GGUF /root/.ollama/models/
RUN ls /root/.ollama/models
RUN cat /root/.ollama/models/x* > /root/.ollama/models/Saiga-llama3-L3-Stheno-8B-v3.2.Q4_K_S.gguf
RUN rm -rf /root/.ollama/models/x??
RUN ls /root/.ollama/models

COPY Modelfile /root/.ollama/models/

COPY src/ /app/src/
EXPOSE 8000 11434

# ИСПРАВЛЕННАЯ КОМАНДА С ПОРТАМИ:
CMD ["sh", "-c", "OLLAMA_HOST=0.0.0.0:11434 ollama serve & sleep 45 && OLLAMA_HOST=0.0.0.0:11434 ollama create ru-analyst -f /root/.ollama/models/Modelfile && sleep 10 && cd /app/src && python -m uvicorn main:app --host 0.0.0.0 --port 8080"]