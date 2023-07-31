# Communication functional requirements
- Subscribe and unsubscribe from other users
- Getting list of user subscribers and user publishers 
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
service check that pubUserId not equal user id from request, if is equal service return error SUBSCRIPTION_LOGIC_ERROR,<br/>
service search user by pubUserId from table 'user', if finding user not exist return error PUBLISHER_USER_NOT_FOUND,<br/>
if finding user is present service check that subscription is not present, if subscription is present then return ALREADY_SUBSCRIBED,<br/>
if not service add subscription.


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
