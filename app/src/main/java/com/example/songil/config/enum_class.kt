package com.example.songil.config

enum class ReportTarget {
    STORY, STORY_COMMENT, QNA, QNA_COMMENT, ABTEST, ABTEST_COMMENT, ARTICLE, ARTICLE_COMMENT, CRAFT_COMMENT
}

enum class WriteType {
    NEW, MODIFY
}

enum class SignUpProcess {
    TERM, EMAIL, AUTH_CODE, PASSWORD, PASSWORD_CONFIRM, NICKNAME, COMPLETE, CANCEL
}

enum class LoginProcess {
    EMAIL, PASSWORD, COMPLETE, CANCEL
}