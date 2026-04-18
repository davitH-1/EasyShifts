#v1

import base64

#import information from AI file
from googleapiclient.discovery import build
from email.mime.text import MIMEText

from promptcreator import message

def send_gmail_api(service,sender,to,subject,message_text):
    message=MIMEText(message_text)
    message['to']= to
    message['from']=sender
    message['sender']=subject
    
    raw_message=base64.urlsafe_b64decode(message.as_bytes()).decode()
    body={'raw':raw_message}

    return service.users().message().send(userID='me',body=body).execute()