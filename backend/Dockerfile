FROM python:3.11-slim

ENV PIP_DISABLE_PIP_VERSION_CHECK=1
ENV PIP_ROOT_USER_ACTION=ignore
ENV PYTHONUNBUFFERED=1

WORKDIR /app

# Copiar e instalar dependencias de forma limpia
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# El resto de tu configuración...
COPY . .
CMD ["uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "8000"]