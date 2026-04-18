#v1

import base64

#import information from AI file
from googleapiclient.discovery import build
from email.mime.text import MIMEText

import ollama

#get the information from the other files

message =ollama.chat(model='gemma',messages=[
    {'role':'user','content':'Generate an email saying that you are available to work the shitfs at (time) then asking to be put on the '
    'schedule for those shifts'},
])



def send_gmail_api(service,sender,to,subject,message_text):
    message=MIMEText(message_text)
    message['to']= to
    message['from']=sender
    message['sender']=subject
    
    raw_message=base64.urlsafe_b64decode(message.as_bytes()).decode()
    body={'raw':raw_message}

    return service.users().message().send(userID='me',body=body).execute()