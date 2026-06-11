from fastapi import APIRouter

router = APIRouter(tags=["Authentication"])

@router.post("/login")
async def login():
    return {"message": "Login exitoso"}