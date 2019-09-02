from locust import HttpLocust, TaskSet
from bs4 import BeautifulSoup
import random
import string
import requests
import urllib

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
    # print response.text

def delete_user(l):
    link = findLinks(l,'fas fa-user-times ml-2')
    response = l.client.get(link)

def bad_addUser(l):
    name = randomString(8)
    data = {'name': name}
    response = l.client.post("/adduser", data)
    #print response.text

def edit_user(l):
    link = findLinks(l,'fas fa-user-edit ml-2')
    name = randomString(8)
    phone = randomString(7)
    description = randomString(15)
    data = {'name': name , 'phone': phone, 'description' : description}
    response = l.client.post(link, data)

def bad_edit_user(l):
    link = findLinks(l,'fas fa-user-edit ml-2')
    name = randomString(8)
    description = randomString(15)
    data = {'name': name , 'description' : description}
    response = l.client.post(link, data)
    #print response.text

def randomString(stringLength=10):
    """Generate a random string of fixed length """
    letters = string.ascii_lowercase
    return ''.join(random.choice(letters) for i in range(stringLength))

def findLinks(l, action):
    page = urllib.urlopen(WebsiteUser.host)
    soup = BeautifulSoup(page, "html.parser")
    link = soup.find(attrs={'class': action})
    #print link
    if link is not None:
        #print link.parent.get('href')
        return link.parent.get('href')
    else:
        print "none found"

class UserBehavior(TaskSet):

    def on_start(self):
        index(self)

    tasks = {
        index: 2,
        page404: 2,
        addUser: 4,
        bad_addUser: 2,
        bad_edit_user:2,
        edit_user:2,
        delete_user: 2}

class WebsiteUser(HttpLocust):
    host = "http://127.0.0.1:8082"
    task_set = UserBehavior
    min_wait = 1000
    max_wait = 10000
