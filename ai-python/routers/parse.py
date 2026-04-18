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


@router.post("/pdf/analyze")
async def analyze_pdf(file: UploadFile = File(...)):
    """Extract text from a PDF and return structured JSON analysis from Ollama."""
    if not file.filename or not file.filename.endswith(".pdf"):
        raise HTTPException(status_code=400, detail="File must be a PDF")

    contents = await file.read()
    pages, text = pdf.extract_text(contents)

    prompt = (
        "You are a data extraction assistant. Analyze the following document text and return "
        "a JSON object with these fields: "
        "\"summary\" (brief summary of the document), "
        "\"key_points\" (array of important points), "
        "\"entities\" (array of names, dates, or numbers mentioned), "
        "\"document_type\" (e.g. invoice, report, schedule, contract). "
        "Return only valid JSON, no extra text.\n\n"
        f"Document text:\n{text}"
    )

    result = ai.chat_json(prompt)
    return {"filename": file.filename, "pages": pages, "analysis": result}