package com.marzhiievskyi.home_notes.controller.testUtils.testData;

public class CommonTestData {
    public static final String REST_URL = "/home-notes";

    public static final String COMMON_GET_NOTES_RESPONSE_JSON = """
            {
                "data": {
                    "notes": [
                        {
                            "nickname": "amitamaru",
                            "text": "second note test",
                            "likesCount": 0
                        },
                        {
                            "nickname": "amitamaru",
                            "text": "note test",
                            "likesCount": 0
                        }
                    ]
                }
            }
            """;
}
