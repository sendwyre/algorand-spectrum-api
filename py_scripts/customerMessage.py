import json
import pprint

from websocket import create_connection

path="streaming"
url = "ws://127.0.0.1:8080/"+path

messageBody = {}
actionsList = ["transfer","buyram"]
data = {}
data = {"account":"eostribe",
        "actions":actionsList
        }

messageBody ={
    "apikey":"test-api-key",
              "event":"subscribe",
               "type":"get_actions",
              "data": data
              }

# print(json.dumps(messageBody))
#
ws = create_connection(url)
ws.ping()
messageJson = json.dumps(messageBody)
ws.send(messageJson)

# while True:
   # print(ws.recv())

# actionsList = ["transfer","test1"]
# messageBody = {"account":"eostribeprod","actions":actionsList }
# messageJson = json.dumps(messageBody)
# ws.send(messageJson)
#
# actionsList = ["transfer1","test1"]
# messageBody ={"account":"testuser","actions":actionsList }
# messageJson = json.dumps(messageBody)
# ws.send(messageJson)


ws.close()
