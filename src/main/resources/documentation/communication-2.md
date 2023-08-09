# Communication functional requirements
- Like and dislike notes
- Comment notes. Delete my comments and comments from my notes
- Blocking and unblocking users
<br/><br/>

## likeNote method (auth)
**incoming data**<br/>
`AccessToken: d4a76068f5104f26975499d22bcd11cc1665995491673`<br/>
/likeNote/{noteId}

**validation**<br/>
noteId > 0

**logic**<br/>
service add note ID and user ID (from user request) in table like_note

**out coming data if access**<br/>
status 200

## disLikeNote ьуерщв (auth)
**incoming data**<br/>
`AccessToken: d4a76068f5104f26975499d22bcd11cc1665995491673`<br/>
/disLikeNote/{noteId}

**validation**<br/>
noteId > 0

**logic**<br/>
service delete note ID and userID (from user request) in table like_note

**out coming data if access**<br/>
status 200

## commentNote (auth)
**incoming data**<br/>
`AccessToken: d4a76068f5104f26975499d22bcd11cc1665995491673`<br/>
```json
{
  "noteId": 1,
  "text": "My comment"
}
```
**validation**<br/>
text >= 1 symbols, text <= 140 symbols, allowed characters a-zA-Z0-9а-яА-Я.,:; _?!+=/'\"*(){}[]-<br/>

**logic**<br/>
service add comment ID, user ID (from user request) in table comment

**out coming data if access**<br/>
status 200

## deleteComment (auth)
**incoming data**<br/>
`AccessToken: d4a76068f5104f26975499d22bcd11cc1665995491673`<br/>
/deleteComment/{commentId}"

**validation**<br/>
commentId > 0

**логика метода**<br/>
service delete comment from 'comment' table provided that the comment belongs to the user or refers to the user's note

**out coming data if access**<br/>
status 200

## blockUser method (auth)

**incoming data**<br/>
`AccessToken: d4a76068f5104f26975499d22bcd11cc1665995491673`<br/>
/blockUser/{blockUserId}

**logic**<br/>
service adds to the block table the ID of the user who is blocking and the ID of the user who is being blocked,<br/>
while a blocked user cannot:
- subscribe to you
- like your notes,
- comment on your notes,
- see your notes in different feeds<br/>

if the blocked user was subscribed to the user who blocks him, the subscription is deleted

**out coming data if access**<br/>
status 200

## getBlockedUsers (auth)

**incoming data**<br/>
`AccessToken: d4a76068f5104f26975499d22bcd11cc1665995491673`<br/>

**logic**<br/>
service returns a list of ID and nickname of users that the user has blocked

**out coming data if access**<br/>
status 200
```json
{
  "data": {
    "blockedUsers": [
      {
        "userId": 1,
        "nickname": "desmont"
      },
      {
        "userId": 13,
        "nickname": "amitamaru"
      }
    ]
  }
}
```

## unblockUser (auth) 

**incoming data**<br/>
`AccessToken: d4a76068f5104f26975499d22bcd11cc1665995491673`<br/>
/blockUser/{blockUserId}

**validation**<br/>
blockUserId > 0

**logic**<br/>
service removes from the block table a row with the id of the calling user and the user with the incoming blockUserId


