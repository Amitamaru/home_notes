# User functional requirements

- User must be able to register and log in
- Public notes, add to his note tags
- Get his notes
- Change username and password<br/><br/>

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

**out coming data if success**<br/>
status 200
```json
{
  "data": {
    "accessToken": "d4a76068f5104f26975499d22bcd11cc1665995491673"
  }
}
```
**out coming data if not success**<br/>
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

**out coming data if success**<br/>
status 200
```json
{
  "data": {
    "accessToken": "d4a76068f5104f26975499d22bcd11cc1665995491673"
  }
}
```
**out coming data if not success**<br/>
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

**out coming data if success**<br/>
status 200

**out coming data if not success**<br/>
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

**out coming data if success**<br/>
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
**out coming data if not success**<br/>
status 400
```json
{
  "error": {
    "code": "AUTHORIZATION_ERROR",
    "userMessage": "error of authorization"
  }
}
```
## changeAuthorization method (auth)
**incoming data**<br/>
`AccessToken: d4a76068f5104f26975499d22bcd11cc1665995491673`<br/>
```json
{
  "authorization": {
    "nickname": "YourNewNickname",
    "password": "YourNewPassword"
  }
}
```
**logic**<br/>
service check that accessToken is valid,<br/>
then check that nickname is not busy, (the user can leave his old nickname and change only the password)<br/>
If a nickname is available then the service encrypting a new password and generates a new accessToken and writes it to the user table<br/>

**validation**<br/>
nickname >= 4 symbols, <= 15 symbols, allowed characters a-zA-Z0-9а-яА-Я. _-<br/>
password >= 8 symbols, <= 100 symbols, allowed characters a-zA-Z0-9а-яА-Я.,:; _?!+=/'\"*(){}[]-

**Outcoming data if success**<br/>
status 200
```json
{
  "data": {
    "accessToken": "d4a76068f5104f26975499d22bcd11cc1665995491673"
  }
}
```

**out coming data if not success**<br/>
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
```json
{
  "error": {
    "code": "USER_NOT_FOUND",
    "userMessage": "user not found"
  }
}
```



