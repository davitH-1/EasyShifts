#program to create prompts for the email to send


import ollama

#get the information from the other files

message =ollama.chat(
    model='gemma4:e4b',
    messages=[
    {
        'role':'user',
        'content':'Generate an email saying that you are available to work the shifts at (time) then asking to be put '
                  'on the '
    'schedule for those shifts'},
])

print(message['message']['content'])