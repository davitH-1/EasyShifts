import ollama
from typing import Any


def chat(prompt: str, model: str = "gemma4:e2b") -> str:
    response = ollama.chat(
        model=model,
        messages=[{"role": "user", "content": prompt}],
    )
    return response["message"]["content"]


def chat_with_data(prompt: str, data: dict[str, Any], model: str = "gemma4:e2b") -> str:
    content = f"{prompt}\n\nData: {data}"
    return chat(content, model)