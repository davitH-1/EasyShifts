from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from routers import parse, generate

app = FastAPI(title="EasyShifts AI Service")

app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:8080", "http://localhost:4200"],
    allow_methods=["*"],
    allow_headers=["*"],
)

app.include_router(parse.router)
app.include_router(generate.router)


@app.get("/health")
def health():
    return {"status": "ok"}