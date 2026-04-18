from fastapi import APIRouter
from models import PromptRequest
from services import ai

router = APIRouter(prefix="/generate")


@router.post("/email")
def generate_email(request: PromptRequest):
    result = ai.chat(request.prompt, request.model)
    return {"email": result}