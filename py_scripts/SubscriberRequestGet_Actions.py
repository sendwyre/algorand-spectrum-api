import json
import pprint

from websocket import create_connection

path="streaming"
url = "wss://api.telos.spectrumeos.io/"+path


actionsList = []

data = {"account":"eosio",
        "actions":actionsList
        }

messageBody ={
    "apikey":"test-api-key",
              "event":"subscribe",
               "type":"get_actions",
              "data": data
              }
ws = create_connection(url)
messageJson = json.dumps(messageBody)
ws.send(messageJson)

while True:
    pprint.pprint(json.loads(ws.recv()))
ws.close()
