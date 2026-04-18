import os
import pickle
import base64
import email
import yaml

from googleapiclient.discovery import build
from google_auth_oauthlib.flow import InstalledAppFlow
from google.auth.transport.requests import Request  # Fixed typo: requrest -> requests

#from: get the sender email

# --- Config ---
SAVE_DIR = "shift_pdfs"
SCOPES = ['https://www.googleapis.com/auth/gmail.readonly']
TOKEN_PATH = 'token.pickle'
CREDENTIALS_PATH = 'credentials.json'  # OAuth2 client secret from Google Cloud Console

os.makedirs(SAVE_DIR, exist_ok=True)

# --- Load sender filter ---
with open('credentials.yml') as f:
    content = f.read()

my_credentials = yaml.load(content, Loader=yaml.FullLoader)
SENDER_EMAIL = my_credentials.get('sender', 'nathanclark20066@gmail.com')


def authenticate():
    """Authenticate via OAuth2 and return a Gmail API service instance."""
    creds = None

    if os.path.exists(TOKEN_PATH):
        with open(TOKEN_PATH, 'rb') as token:
            creds = pickle.load(token)

    # Refresh or re-authenticate if credentials are missing or expired
    if not creds or not creds.valid:
        if creds and creds.expired and creds.refresh_token:
            creds.refresh(Request())
        else:
            flow = InstalledAppFlow.from_client_secrets_file(CREDENTIALS_PATH, SCOPES)
            creds = flow.run_local_server(port=0)

        with open(TOKEN_PATH, 'wb') as token:
            pickle.dump(creds, token)

    return build('gmail', 'v1', credentials=creds)


def search_messages(service, sender_email):
    """Return a list of message IDs from a specific sender."""
    query = f'from:{sender_email}'
    results = service.users().messages().list(userId='me', q=query).execute()
    messages = results.get('messages', [])

    # Handle pagination
    while 'nextPageToken' in results:
        results = service.users().messages().list(
            userId='me', q=query, pageToken=results['nextPageToken']
        ).execute()
        messages.extend(results.get('messages', []))

    return messages


def get_pdf_attachments(service, message_id):
    """Yield (filename, data) tuples for every PDF attachment in a message."""
    msg = service.users().messages().get(
        userId='me', id=message_id, format='full'
    ).execute()

    subject = next(
        (h['value'] for h in msg['payload']['headers'] if h['name'] == 'Subject'),
        'No Subject'
    )

    parts = msg['payload'].get('parts', [])

    # Flatten nested multipart structures
    def iter_parts(parts):
        for part in parts:
            if part.get('parts'):
                yield from iter_parts(part['parts'])
            else:
                yield part

    for part in iter_parts(parts):
        mime_type = part.get('mimeType', '')
        filename = part.get('filename', '')
        disposition = part.get('headers', [])
        disposition_value = next(
            (h['value'] for h in disposition if h['name'] == 'Content-Disposition'), ''
        )

        is_pdf = (
            mime_type == 'application/pdf'
            or (mime_type == 'application/octet-stream' and '.pdf' in filename.lower())
            or '.pdf' in disposition_value.lower()
        )

        if not is_pdf or not filename:
            continue

        attachment_id = part['body'].get('attachmentId')
        if not attachment_id:
            # Small attachments may be inlined in 'data'
            data = part['body'].get('data', '')
        else:
            attachment = service.users().messages().attachments().get(
                userId='me', messageId=message_id, id=attachment_id
            ).execute()
            data = attachment['data']

        # Gmail API uses URL-safe base64
        pdf_bytes = base64.urlsafe_b64decode(data + '==')
        yield filename, pdf_bytes, subject


def save_pdf(filename, pdf_bytes):
    """Save PDF bytes to SAVE_DIR, avoiding filename collisions."""
    safe_filename = "".join(
        c if c.isalnum() or c in (' ', '.', '_', '-') else '_'
        for c in filename
    )
    save_path = os.path.join(SAVE_DIR, safe_filename)

    base, ext = os.path.splitext(save_path)
    counter = 1
    while os.path.exists(save_path):
        save_path = f"{base}_{counter}{ext}"
        counter += 1

    with open(save_path, 'wb') as f:
        f.write(pdf_bytes)

    return save_path


# --- Main ---
service = authenticate()

messages = search_messages(service, SENDER_EMAIL)
print(f"Found {len(messages)} email(s) from {SENDER_EMAIL}")

pdf_count = 0

for msg_meta in messages:
    for filename, pdf_bytes, subject in get_pdf_attachments(service, msg_meta['id']):
        save_path = save_pdf(filename, pdf_bytes)
        print(f"  ✓ Saved: {save_path}  (from email: '{subject}')")
        pdf_count += 1

print(f"\nDone. {pdf_count} PDF(s) saved to '{SAVE_DIR}/'")