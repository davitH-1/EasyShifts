import httpx
import json
from typing import Any
from config import OLLAMA_HOST, OLLAMA_MODEL


def _generate(prompt: str, model: str, fmt: str | None = None) -> dict:
    payload = {"model": model, "prompt": prompt, "stream": False}
    if fmt:
        payload["format"] = fmt
    response = httpx.post(
        f"{OLLAMA_HOST}/api/generate",
        json=payload,
        timeout=120.0,
    )
    response.raise_for_status()
    return response.json()


def chat(prompt: str, model: str = OLLAMA_MODEL) -> str:
    return _generate(prompt, model)["response"]


def chat_json(prompt: str, model: str = OLLAMA_MODEL) -> dict:
    raw = _generate(prompt, model, fmt="json")["response"]
    return json.loads(raw)


def chat_with_data(prompt: str, data: dict[str, Any], model: str = OLLAMA_MODEL) -> str:
    return chat(f"{prompt}\n\nData: {data}", model)