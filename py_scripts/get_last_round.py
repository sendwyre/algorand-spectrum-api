import requests
from pprint import pprint
import os
import redis
import time

upstream = os.environ.get('UPSTREAM_API',"http://algo.eostribe.io")
redis_host = os.environ.get('REDIS_HOST',"localhost")

redis_client = redis.Redis(host=redis_host, port=6379, db=0)
timeSleep = 4.3
delta = 0.0

def getLastRound():
    global delta
    response = requests.get(upstream + '/v1/status')
    if response.status_code == 200:
        r = response.json()
        lastBlock = r['lastRound']
        delta = 1 - (response.elapsed.microseconds / 1000 + r['timeSinceLastRound']) / 1000000000
        pprint(lastBlock)
        return lastBlock

def getTransactions(lastRound):
    response = requests.get(upstream+'/v1/block/'+str(lastRound))
    if response.status_code == 200:
        r = response.json()
        transactions = r['txns']
        print(lastRound)
        pprint(transactions)
        return transactions

while 1:
    redis_client.publish('lastblock', getLastRound())
    time.sleep(timeSleep+delta)
