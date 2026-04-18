from typing import List, Dict

import ollama

from services.ai import client

client = ollama.Client(host="http://172.27.23.8:11434")

def generate_shift_email(
    name: str,
    shifts: List[Dict], # expects shifts with same days to already be sorted in preference via index
    model: str = "gemma4:e2b"
) -> str:
    """
    shifts format:
    [
        {
            "day": "Friday 24th",
            "locations": ["Balboa Yacht Club"],
            "priority": 1
        },
        {
        "day": "Sunday 25th",
            "locations": [
                {"name": "Balboa Yacht Club", "priority": 1},
                {"name": "Concordia West Irvine", "priority": 2}
            ]
        }
    ]
    """

    prompt = f"""
You are writing a professional availability email.

STRICT RULES:
- Do NOT add extra commentary
- Follow the format exactly
- Keep it short and natural
- Do not use JSON

FORMAT:

Hello,
I would like to request the following shifts:

{format_shifts(shifts)}

Sincerely,
{name}

STYLE RULES:
- Each day must be on its own line
- Preferred shifts must be numbered if multiple exist
- If only one shift exists, do not number it
- Use clean spacing like a real email
"""

    response = client.chat(
        model=model,
        messages=[{"role": "user", "content": prompt}],
    )

    return response["message"]["content"]


def format_shifts(shifts: List[Dict]) -> str:
    output = []

    for shift in shifts:
        day = shift["day"]
        locations = shift["locations"]

        # sort by priority (lower number = higher preference)
        sorted_locations = sorted(locations, key=lambda x: x["priority"])

        if len(sorted_locations) == 1:
            output.append(f"{day}: {sorted_locations[0]['name']}")
        else:
            block = f"{day}:"
            for i, loc in enumerate(sorted_locations, 1):
                block += f"\n{i}. {loc['name']}"
            output.append(block)

    return "\n\n".join(output)