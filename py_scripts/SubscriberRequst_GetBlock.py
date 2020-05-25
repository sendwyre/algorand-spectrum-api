import json
import pprint
from websocket import create_connection

path="streaming"
url = "ws://localhost:8080/"+path
data = {}
messageBody = {
    "apikey":"test-api-key",
    "event":"subscribe",
    "type":"get_blocks",
    "data":data
}
ws = create_connection(url)
ws.send(json.dumps(messageBody))

while True:
    pprint.pprint(json.loads(ws.recv()))
ws.close()