# Make sure IMAP is enabled and an app-specific password is used if 2FA is active

import imaplib
import email
import os
import yaml

# --- Config ---
SAVE_DIR = "downloaded_pdfs"
os.makedirs(SAVE_DIR, exist_ok=True)

# --- Load credentials ---
with open('credentials.yml') as f:
    content = f.read()

my_credentials = yaml.load(content, Loader=yaml.FullLoader)
user = my_credentials['user']
password = my_credentials['password']

# --- Connect to Gmail ---
imap_url = 'imap.gmail.com'
my_mail = imaplib.IMAP4_SSL(imap_url)
my_mail.login(user, password)
my_mail.select('Inbox')

# --- Search for emails from a specific sender ---
key = 'From'
value = 'nathanclark20066@gmail.com'  # Replace with actual sender email
_, data = my_mail.search(None, key, value)

mail_id_list = data[0].split()
print(f"Found {len(mail_id_list)} email(s) from {value}")

pdf_count = 0

# --- Iterate through each email ---
for num in mail_id_list:
    _, msg_data = my_mail.fetch(num, '(RFC822)')

    for response_part in msg_data:
        if not isinstance(response_part, tuple):
            continue

        msg = email.message_from_bytes(response_part[1])
        subject = msg.get("Subject", "No Subject")

        # --- Walk all MIME parts ---
        for part in msg.walk():
            content_type = part.get_content_type()
            content_disposition = str(part.get("Content-Disposition", ""))

            # Detect PDFs by content type or file extension in disposition
            is_pdf = (
                content_type == "application/pdf"
                or content_type == "application/octet-stream"
                and ".pdf" in content_disposition.lower()
            )

            if part.get_content_maintype() == "multipart":
                continue  # Skip container parts

            filename = part.get_filename()

            if is_pdf and filename:
                # Sanitize filename
                safe_filename = "".join(
                    c if c.isalnum() or c in (' ', '.', '_', '-') else '_'
                    for c in filename
                )
                save_path = os.path.join(SAVE_DIR, safe_filename)

                # Avoid overwriting duplicates
                base, ext = os.path.splitext(save_path)
                counter = 1
                while os.path.exists(save_path):
                    save_path = f"{base}_{counter}{ext}"
                    counter += 1

                with open(save_path, 'wb') as f:
                    f.write(part.get_payload(decode=True))

                print(f"  ✓ Saved: {save_path}  (from email: '{subject}')")
                pdf_count += 1

print(f"\nDone. {pdf_count} PDF(s) saved to '{SAVE_DIR}/'")
my_mail.logout()