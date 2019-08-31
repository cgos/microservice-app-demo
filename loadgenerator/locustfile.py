import random
import string
import requests
from locust import HttpLocust, TaskSet

def index(l):
    l.client.get("/")

def page404(l):
    l.client.get("/wrong-url")

def addUser(l):
    name = randomString(8)
    phone = randomString(7)
    description = randomString(15)
    data = {'name': name , 'phone': phone, 'description' : description}
    response = l.client.post("/adduser", data)
    print response.text

def bad_addUser(l):
    name = randomString(8)
    data = {'name': name}
    response = l.client.post("/adduser", data)
    print response.text

def randomString(stringLength=10):
    """Generate a random string of fixed length """
    letters = string.ascii_lowercase
    return ''.join(random.choice(letters) for i in range(stringLength))

class UserBehavior(TaskSet):

    def on_start(self):
        index(self)

    tasks = {index: 2,
        page404: 2,
        addUser: 4,
        bad_addUser: 2}

class WebsiteUser(HttpLocust):
    host = "http://127.0.0.1:8082"
    task_set = UserBehavior
    min_wait = 1000
    max_wait = 10000
