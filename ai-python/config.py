import os

OLLAMA_HOST = os.getenv("OLLAMA_HOST", "http://172.27.23.8:11434")
OLLAMA_MODEL = os.getenv("OLLAMA_MODEL", "gemma4:e2b")