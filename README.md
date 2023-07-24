
# Functional requirements
## Registration method
**incoming data**
```json
{
  "authorization": {
    "nickname": "YourNickname",
    "password": "YourPassword"
  }
}
```
**validation**<br/>
nickname >= 4 symbols, <= 15 symbols, allowed characters a-zA-Z0-9а-яА-Я. _-<br/>
password >= 8 symbols, <= 100 symbols, allowed characters a-zA-Z0-9а-яА-Я.,:; _?!+=/'\"*(){}[]-

**logic**<br/>
service search in table user by incoming nickname,<br/>
If it is present then returns NICKNAME_BUSY,<br/>
else generate accessToken, encrypts incoming password, and write in table user (nickname, password, accessToken)<br/>
returns to client access

**outcoming data if access**<br/>
status 200
```json
{
  "data": {
    "accessToken": "d4a76068f5104f26975499d22bcd11cc1665995491673"
  }
}
```
**outcoming data if not access**<br/>
status 400
```json
{
  "error": {
    "code": "REQUEST_VALIDATION_ERROR",
    "userMessage": "incorrect nickname. incorrect password"
  }
}
```
```json
{
  "error": {
    "code": "NICKNAME_BUSY",
    "userMessage": "this nickname is busy please enter another"
  }
}
```
## Login method
**incoming data**
```json
{
  "authorization": {
    "nickname": "YourNickname",
    "password": "YourPassword"
  }
}
```
**validation**<br/>
nickname >= 4 symbols, <= 15 symbols, allowed characters a-zA-Z0-9а-яА-Я. _-<br/>
password >= 8 symbols, <= 100 symbols, allowed characters a-zA-Z0-9а-яА-Я.,:; _?!+=/'\"*(){}[]-

**logic**<br/>
service encrypting incoming passwords like in the registration method and search in table user by nickname and password<br/>
If it is present then returns accessToken<br/>
Else returns exception USER_NOT_FOUND

**outcoming data if access**<br/>
status 200
```json
{
  "data": {
    "accessToken": "d4a76068f5104f26975499d22bcd11cc1665995491673"
  }
}
```
**outcoming data if not access**<br/>
status 400
```json
{
  "error": {
    "code": "REQUEST_VALIDATION_ERROR",
    "techMessage": "incorrect nickname. incorrect password"
  }
}
```
```json
{
  "error": {
    "code": "USER_NOT_FOUND",
    "userMessage": "user not found"
  }
}
```
## Public note (auth)
**incoming data**<br/>
`AccessToken: d4a76068f5104f26975499d22bcd11cc1665995491673`
```json
{
  "text": "I'm codding my new app",
  "tags": [
    "Java",
    "Spring",
    "JDBC"
  ]
}
```
**validation**<br/>
text >= 1 symbols, <= 140 symbols, allowed characters a-zA-Z0-9а-яА-Я.,:; _?!+=/'\"*(){}[]-<br/>
tags from 0 to 5 tags,  >= 3 symbols, <= 25 symbols,  allowed characters a-zA-Z0-9а-яА-Я.,:; _?!+=/'\"*(){}[]-

**logic**<br/>
service checking that accessToken is present in the table user<br/>
if not present then returns AUTHORIZATION_ERROR,<br/>
else getting user ID by accessToken,<br/>
Write a note in the table note,<br/>
Check by every tag in table 'tag', if not present - then write in table 'tag' and write tags ID and note ID in table 'note_tag'

**outcoming data if access**<br/>
status 200

**outcoming data if not access**<br/>
status 400
```json
{
  "error": {
    "code": "AUTHORIZATION_ERROR",
    "userMessage": "error of authorization"
  }
}
```
## Get my notes method (auth)
**incoming data**<br/>
`AccessToken: d4a76068f5104f26975499d22bcd11cc1665995491673`<br/>

**logic**<br/>
service getting notes by user ID,<br/>
getting tags by note ID,<br/>
arranges notes by time from new to old and sends them to the user

**outcoming data if access**<br/>
status 200
```json
{
  "data": {
    "notes": [
      {
        "noteId": 7,
        "text": "I'm codding my new app",
        "timeInsert": "2023-16-23 12:07:23",
        "tags": [
          {
            "tagId": 13,
            "text": "Java"
          },
          {
            "tagId": 7,
            "text": "Spring"
          },
          {
            "tagId": 9,
            "text": "JDBC"
          },
        ]
      },
      {
        "noteId": 32,
        "text": "I'm still codding",
        "timeInsert": "2023-06-21 11:42:13",
        "tags": [
          {
            "tagId": 13,
            "text": "Java"
          },
          {
            "tagId": 16,
            "text": "codding late night"
          }
        ]
      }
    ]
  }
}
```
**outcoming data if not access**<br/>
status 400
```json
{
  "error": {
    "code": "AUTHORIZATION_ERROR",
    "userMessage": "error of authorization"
  }
}
```
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
at the begining shoul be tags who begins from input part of tag in descending order of popularity<br/>
after that tags that do not begin with the partTag<br/>

**outcoming data if access**<br/>
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

