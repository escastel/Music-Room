from fastapi import APIRouter, Depends

router = APIRouter(tags=["Users"])

@router.get("/{user_id}")
async def get_users(user_id : int):
    return [{"id": user_id, "name": "Nestor"}]
