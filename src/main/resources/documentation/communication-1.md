# Communication functional requirements
- Subscribe and unsubscribe from other users
- Getting a list of user subscribers and user publishers
- Get my publishers notes, 'from' to 'limit'
<br/><br/>

## Subscription method (auth)
**incoming data**<br/>
`AccessToken: d4a76068f5104f26975499d22bcd11cc1665995491673`<br/>
```json
{
  "pubUserId": 1
}
```
**validation**<br/>
pubUserId > 0

**logic**<br/>
service check that pubUserId does not equal user id from request, if is equal service return error SUBSCRIPTION_LOGIC_ERROR,<br/>
service search user by pubUserId from table 'user', if finding user does not exist return error PUBLISHER_USER_NOT_FOUND,<br/>
If finding the user is present service checks that the subscription is not present, If a subscription is present then return ALREADY_SUBSCRIBED,<br/>
If not service add a subscription.


**out coming data if access**<br/>
status 200

**out coming data if not access**<br/>
status 400
```json
{
  "error": {
    "code": "SUBSCRIPTION_LOGIC_ERROR",
    "userMessage": "you can not subscribe by your self"
  }
}
```
```json
{
  "error": {
    "code": "PUBLISHER_NOT_FOUND",
    "userMessage": "publisher not found"
  }
}
```

```json
{
  "error": {
    "code": "ALREADY_SUBSCRIBED",
    "userMessage": "you have already subscribed to this user"
  }
}
```

## unsubscription method (auth)
**incoming data**<br/>
`AccessToken: d4a76068f5104f26975499d22bcd11cc1665995491673`<br/>
```json
{
  "pubUserId": 1
}
```
**validation**<br/>
pubUserId > 0

**logic**<br/>
service trying to delete subscription by incoming data

**out coming data if access**<br/>
status 200

## getMySubscribers method (auth)
**incoming data**<br/>
`AccessToken: d4a76068f5104f26975499d22bcd11cc1665995491673`<br/>

**logic**<br/>
service returns user subscribers

**out coming data if access**<br/>
status 200

```json
{
  "data": {
    "subscribers": [
      {
        "userId": 2,
        "nickname": "desmont"
      },
      {
        "userId": 1,
        "nickname": "mamaKevina"
      }
    ]
  }
}
```

## getMyPublishers method (auth)
**incoming data**<br/>
`AccessToken: d4a76068f5104f26975499d22bcd11cc1665995491673`<br/>

**logic**<br/>
service returns users publishers

**out coming data if access**<br/>
status 200

```json
{
  "data": {
    "subscribers": [
      {
        "userId": 3,
        "nickname": "maman"
      },
      {
        "userId": 2,
        "nickname": "desmont"
      },
      {
        "userId": 1,
        "nickname": "mamaKevina"
      }
    ]
  }
}
```
## getMyPublishersNotes (auth)

**incoming data**<br/>
`AccessToken: d4a76068f5104f26975499d22bcd11cc1665995491673`<br/>
/getMyPublishersPhrases/{from}/{limit}

**validation**<br/>
from >= 0, limit > 0

**logic**<br/>
service returns limit notes of user publishers from first. Sorted from new to old<br/>

**out coming data if access**<br/>
status 200
```json
{
  "data": {
    "notes": [
      {
        "noteId": 20,
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
        "noteId": 18,
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
        "noteId": 13,
        "userid": 3,
        "nickname": "amitamaru",
        "text": "Have a nice day",
        "tags": [
          {
            "tagId": 20,
            "text": "Sunny Day"
          }
        ],
        "timeInsert": "2023-07-05 20:16:04"
      }
    ]
  }
}
```
