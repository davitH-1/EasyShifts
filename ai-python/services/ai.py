import json

import ollama
from typing import Any, List, Dict

client = ollama.Client(host="http://172.27.23.8:11434")

def chat(prompt: str, model: str = "gemma4:e2b") -> str:
    response = ollama.chat(
        model=model,
        messages=[{"role": "user", "content": prompt}],
    )
    return response["message"]["content"]


def chat_with_data(prompt: str, data: dict[str, Any], model: str = "gemma4:e2b") -> str:
    content = f"{prompt}\n\nData: {data}"
    return chat(content, model)

# Email generator
