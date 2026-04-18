from fastapi import APIRouter, UploadFile, File, HTTPException
from models import JsonParseRequest
from services import ai, pdf

router = APIRouter(prefix="/parse")


@router.post("/json")
def parse_json(request: JsonParseRequest):
    if request.prompt:
        result = ai.chat_with_data(request.prompt, request.data)
        return {"input": request.data, "result": result}
    return {"input": request.data, "result": None}


@router.post("/pdf")
async def parse_pdf(file: UploadFile = File(...)):
    if not file.filename or not file.filename.endswith(".pdf"):
        raise HTTPException(status_code=400, detail="File must be a PDF")
    contents = await file.read()
    pages, text = pdf.extract_text(contents)
    return {"filename": file.filename, "pages": pages, "text": text}