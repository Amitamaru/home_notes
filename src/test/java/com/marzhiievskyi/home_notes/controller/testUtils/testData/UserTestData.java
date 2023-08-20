package com.marzhiievskyi.home_notes.controller.testUtils.testData;

public class UserTestData {

    //Data user tests
    public static final String ACCESS_TOKEN_FIELD = "accessToken";
    public static final String USER_NICKNAME = "amitamaru";
    public static final String USER_DESMONT_NICKNAME = "desmont";
    public static final String USER_PASSWORD = "1234567890";
    public static final String USER_DESMONT_PASSWORD_PASSWORD = "987654321";
    public static final String USER_ACCESS_TOKEN = "3c6a97a051894629983c90536601bf521692129265092";
    public static final String USER_DESMONT_ACCESS_TOKEN = "3c6a97a051894629983c90536601bf521692129261990";
    public static final String USER_WRONG_ACCESS_TOKEN = "3c6a97a051894629983c90123456789888889265092";
    //Data note test
    public static final String NOTE_TEXT = "note test";
    public static final String NOTE2_TEXT = "second note test";
    public static final String TAG_TEXT = "test";

    //JSONs
    public static final String USER_AMITAMARU_JSON = """
            {
              "authorization": {
                "nickname": "amitamaru",
                "password": "1234567890"
              }
            }
            """;
    public static final String USER_AMITAMARU_WRONG_DATA_JSON = """
            {
              "authorization": {
                "nickname": "amitamaru1",
                "password": "11111111"
              }
            }
            """;

    public static final String USER_TO_CHANGE_LOGIN_PASSWORD_JSON = """
            {
              "authorization": {
                "nickname": "desmont",
                "password": "987654321"
              }
            }
            """;
    public static final String AUTHORIZATION_ERROR_JSON = """
              {
                  "error": {
                      "code": "AUTHORIZATION_ERROR",
                      "userMessage": "error of authorization"
                  }
              }
            """;
    public static final String NICKNAME_BUSY_ERROR_JSON = """
              {
                  "error": {
                      "code": "NICKNAME_BUSY",
                      "userMessage": "this nickname is busy please enter another"
                  }
              }
            """;
    public static final String USER_NOT_FOUND_ERROR_JSON = """
              {
                  "error": {
                      "code": "USER_NOT_FOUND",
                      "userMessage": "user not found"
                  }
              }
            """;
    public static final String USER_PUBLIC_NOTE_JSON = """
            {
              "text": "note test",
              "tags": [
                    "test",
                    "two",
                    "three"
              ]
            }
            """;
    public static final String USER_GET_MY_NOTES_RESPONSE_JSON = """
            {
                "data": {
                    "notes": [
                        {
                            "nickname": "amitamaru",
                            "text": "note test"
                        },
                        {
                            "nickname": "amitamaru",
                            "text": "second note test"
                        }
                    ]
                }
            }
            """;


}
