from pydantic import BaseModel
from typing import Any


class JsonParseRequest(BaseModel):
    data: dict[str, Any]
    prompt: str | None = None


class PromptRequest(BaseModel):
    prompt: str
    model: str = "gemma4:e2b"