# Search functional requirements

- Search for tags or part of tag
- Search for notes by tags 
- Search for notes by word or part of word
- Search for users by nickname or part of nickname<br/><br/>

## searchTags method (auth)
**incoming data**<br/>
`AccessToken: d4a76068f5104f26975499d22bcd11cc1665995491673`<br/>
```json
{
  "partOfTag": "Jav"
}
```

**validation**<br/>
part of tag >= 3 symbols, <= 25 symbols,  allowed characters a-zA-Z0-9а-яА-Я.,:; _?!+=/'\"*(){}[]-

**logic**<br/>
service search by part of tag in table tag and returns tags and them ID<br/>
at the beginning should be tags who begin from input part of tag in descending order of popularity<br/>
after that tags that do not begin with the partTag<br/>

**out coming data if access**<br/>
status 200
```json
{
  "data": {
    "tags": [
        {
          "tagId": 13,
          "text": "Java"
        },
        {
          "tagId": 19,
          "text": "Javadoc"
        },
        {
          "tagId": 9,
          "text": "JDBC"
        }
    ]
  }
}
```
## searchNotesByTag method (auth)
**incoming data**<br/>
`AccessToken: d4a76068f5104f26975499d22bcd11cc1665995491673`<br/>
```json
{
  "tagId": 13,
  "sort": "TIME_INSERT"
}
```
**validation**<br/>
tagId > 0<br/>
sort: RANDOM, TIME_INSERT

**logic**<br/>
service search notes by tagId and returns user array of notes: <br/>
id note, user id, user nickname, note text, note tags, note time created, <br/>
random or time created (from new to old) sort

**out coming data if access**<br/>
status 200
```json
{
  "data": {
    "notes": [
      {
        "noteId": 18,
        "userid": 1,
        "nickname": "mamaKevina",
        "text": "I'm codding my pet project",
        "tags": [
          {
            "tagId": 13,
            "text": "Java"
          },
          {
            "tagId": 19,
            "text": "Javadoc"
          },
          {
            "tagId": 29,
            "text": "spring"
          }
        ],
        "timeInsert": "2023-07-13 19:17:33"
      },
      {
        "noteId": 22,
        "userid": 1,
        "nickname": "mamaKevina",
        "text": "I'm still learn",
        "tags": [
          {
            "tagId": 13,
            "text": "Java"
          }
        ],
        "timeInsert": "2023-07-06 20:06:44"
      }
    ]
  }
}
```
## searchNotesByPartWord method (auth)
**incoming data**<br/>
`AccessToken: d4a76068f5104f26975499d22bcd11cc1665995491673`<br/>
```json
{
  "partWord": "I'm ",
  "sort": "RANDOM"
}
```
**validation**<br/>
partWord >= 3 symbols, <= 25 symbols, allowed characters a-zA-Z0-9а-яА-Я.,:; _?!+=/'\"*(){}[]-<br/>
sort: RANDOM, TIME_INSERT

**logic**<br/>
service search notes by partWord and returns user array of notes: <br/>
id note, user id, user nickname, note text, note tags, note time created, <br/>
random or time created (from new to old) sort

**out coming data if access**<br/>
status 200
```json
{
  "data": {
    "notes": [
      {
        "noteId": 22,
        "userid": 1,
        "nickname": "mamaKevina",
        "text": "I'm still learn",
        "tags": [
          {
            "tagId": 13,
            "text": "Java"
          }
        ],
        "timeInsert": "2023-07-06 20:06:44"
      },
      {
        "noteId": 18,
        "userid": 1,
        "nickname": "mamaKevina",
        "text": "I'm codding my pet project",
        "tags": [
          {
            "tagId": 13,
            "text": "Java"
          },
          {
            "tagId": 19,
            "text": "Javadoc"
          },
          {
            "tagId": 29,
            "text": "spring"
          }
        ],
        "timeInsert": "2023-07-13 19:17:33"
      }
    ]
  }
}
```
## searchUsersByPartNickname method (auth)
**incoming data**<br/>
`AccessToken: d4a76068f5104f26975499d22bcd11cc1665995491673`<br/>
```json
{
  "partNickname": "desm"
}
```
**validation**<br/>
partNickname >= 3 symbols, <= 15 symbols, allowed characters a-zA-Z0-9а-яА-Я. _-<br/>

**logic**<br/>
service search nicknames by partNickname and return to user array of nickname and id<br/>
sorting - at first mus be nicknames which begins from partNickname<br/>


**out coming data if access**<br/>
status 200
```json
{
  "data": {
    "users": [
      {
        "userId": 2,
        "nickname": "desmont"
      },
      {
        "userId": 7,
        "nickname": "desmologin"
      },
      {
        "userId": 9,
        "nickname": "amidesman"
      }
    ]
  }
}
```
## searchNotesByUserId method (auth)
**incoming data**<br/>
`AccessToken: d4a76068f5104f26975499d22bcd11cc1665995491673`<br/>
```json
{
  "userId": "1"
}
```
**validation**<br/>
userId >= 1 

**logic**<br/>
service search user notes by userId and return list of notes<br/>
If the user is not found, then return USER_NOT_FOUND<br/>

**out coming data if access**<br/>
status 200
```json
{
  "data": {
    "notes": [
      {
        "noteId": 22,
        "userid": 1,
        "nickname": "mamaKevina",
        "text": "I'm still learn",
        "tags": [
          {
            "tagId": 13,
            "text": "Java"
          }
        ],
        "timeInsert": "2023-07-06 20:06:44"
      },
      {
        "noteId": 18,
        "userid": 1,
        "nickname": "mamaKevina",
        "text": "I'm codding my pet project",
        "tags": [
          {
            "tagId": 13,
            "text": "Java"
          },
          {
            "tagId": 19,
            "text": "Javadoc"
          },
          {
            "tagId": 29,
            "text": "spring"
          }
        ],
        "timeInsert": "2023-07-13 19:17:33"
      }
    ]
  }
}
```
**out coming data if not access**<br/>
status 400
```json
{
  "error": {
    "code": "USER_NOT_FOUND",
    "userMessage": "user not found"
  }
}
