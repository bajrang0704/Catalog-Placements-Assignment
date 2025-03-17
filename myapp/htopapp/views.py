import os
import datetime
import subprocess
from django.http import HttpResponse

def htop_view(request):
    name = "Banala Prabanjan Reddy"
    username = os.getenv("USER") or os.getenv("USERNAME")
    ist_time = datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S IST")
    top_output = subprocess.getoutput("top -b -n 1 | head -10")

    html = f"""
    <h1>System Info</h1>
    <p><strong>Name:</strong> {name}</p>
    <p><strong>Username:</strong> {username}</p>
    <p><strong>Server Time (IST):</strong> {ist_time}</p>
    <pre>{top_output}</pre>
    """
    return HttpResponse(html)
