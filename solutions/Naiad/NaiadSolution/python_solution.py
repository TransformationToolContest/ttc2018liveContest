import xml.etree.ElementTree as ET
aRoot = ET.parse('a.xml').getroot()

commentIds = []
likes = []
users = []


def doStuff():
    for child in aRoot:
        if child.tag == 'posts':
            if child.get('id') == '723221':
                getChildCommentIds(child)
			
def getChildCommentIds(root):
    for child in root:
        if child.tag == 'comments':
            commentIds.append(child.get('id'))
            getChildCommentIds(child)

def getLikes(user):
    users.append(user)
    for likedComment in user.get('likes','').split():
        if likedComment in commentIds:
            likes.append((user.get('id'), likedComment))

def doUserStuff():
    for child in aRoot:
        if child.tag == 'users':
            getLikes(child)

doStuff()
doUserStuff()
print(len(commentIds))
print(len(likes))