import time
from fastapi import FastAPI, Request
from fastapi.middleware.cors import CORSMiddleware
from fastapi.middleware.gzip import GZipMiddleware
from app.v1.router import api_router

app = FastAPI(
    title="Music Room API",
    version="1.0.0",
)

# ---------------------------------------------------------------------
# 1. MIDDLEWARE: CORS (Cross-Origin Resource Sharing)
# ---------------------------------------------------------------------
# Define aquí los orígenes permitidos (p. ej., el dominio de tu frontend)
# Evita usar ["*"] en producción si manejas credenciales (cookies/auth headers).
origins = [
    "http://localhost:3000",      # Frontend de desarrollo común (React/Next.js)
    "http://127.0.0.1:3000",
    "https://tudominio.com",       # Tu dominio de producción
]

app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],           # Permite GET, POST, PUT, DELETE, etc.
    allow_headers=["*"],           # Permite headers como Authorization, Content-Type
)

# ---------------------------------------------------------------------
# 2. MIDDLEWARE: Compresión Gzip
# ---------------------------------------------------------------------
# Comprime las respuestas JSON que superen el tamaño mínimo en bytes.
# Es crucial para optimizar el ancho de banda cuando devuelves payloads grandes.
app.add_middleware(
    GZipMiddleware, 
    minimum_size=1000  # Comprime cualquier respuesta mayor a 1 KB
)

# ---------------------------------------------------------------------
# 3. MIDDLEWARE CUSTOM: Tiempos de Respuesta y Logs básicos
# ---------------------------------------------------------------------
# Un middleware basado en funciones (ASGI) ideal para medir el rendimiento
# de cada endpoint y añadir cabeceras personalizadas de diagnóstico.
@app.middleware("http")
async def add_process_time_header(request: Request, call_next):
    start_time = time.perf_counter()
    
    # Procesa la petición y obtiene la respuesta del endpoint
    response = await call_next(request)
    
    # Calcula el tiempo exacto de procesamiento
    process_time = time.perf_counter() - start_time
    
    # Inyecta el tiempo en los headers de la respuesta (útil para debugging en el cliente)
    response.headers["X-Process-Time"] = f"{process_time:.4f}s"
    
    # Opcional: Loggear en consola la ruta y su tiempo de respuesta
    print(f"INFO:     {request.method} {request.url.path} - Completado en {process_time:.4f}s con status {response.status_code}")
    
    return response

# ---------------------------------------------------------------------
# Inclusión de Rutas Globales
# ---------------------------------------------------------------------
app.include_router(api_router, prefix="/app/v1")