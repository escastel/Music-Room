import uvicorn
from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware

# Crear instancia de la aplicación FastAPI
app = FastAPI(
    title="Music Room API",
    description="API para gestionar salas de música colaborativas",
    version="1.0.0"
)

# Configurar CORS
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)


# Rutas básicas
@app.get("/")
async def root():
    """Endpoint raíz de bienvenida"""
    return {"message": "Bienvenido a Music Room API"}


@app.get("/health")
async def health_check():
    """Endpoint de verificación de salud del servidor"""
    return {"status": "ok"}


if __name__ == "__main__":
    uvicorn.run(
        "main:app",
        host="0.0.0.0",
        port=8000,
        reload=True,
    )
