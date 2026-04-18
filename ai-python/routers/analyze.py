from fastapi import APIRouter
from pydantic import BaseModel
from typing import Any
from services import ai

router = APIRouter(prefix="/analyze")


class CalendarRequest(BaseModel):
    events: dict[str, Any]
    prompt: str | None = None


@router.post("/calendar")
def analyze_calendar(request: CalendarRequest):
    prompt = request.prompt or (
        "Analyze these Google Calendar events for this week. "
        "Identify busy periods, free time slots, and suggest the best times for work shifts. "
        "Return a JSON object with fields: busy_periods, free_slots, recommended_shift_times, summary."
    )
    result = ai.chat_json(f"{prompt}\n\nCalendar data: {request.events}")
    return {"analysis": result}