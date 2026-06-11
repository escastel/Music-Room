from fastapi import APIRouter
from app.v1.endpoints import users, auth

api_router = APIRouter()

# Incluimos los sub-routers asignándoles su prefijo correspondiente
api_router.include_router(auth.router, prefix="/auth")
api_router.include_router(users.router, prefix="/users")
