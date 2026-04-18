import pypdf
import io


def extract_text(file_bytes: bytes) -> tuple[int, str]:
    reader = pypdf.PdfReader(io.BytesIO(file_bytes))
    text = "\n".join(page.extract_text() or "" for page in reader.pages)
    return len(reader.pages), text