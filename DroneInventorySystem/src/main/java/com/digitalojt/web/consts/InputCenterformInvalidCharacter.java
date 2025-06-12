package com.digitalojt.web.consts;

/**
 * 不正文字を管理するEnumクラス
 * 
 * @author dotlife
 */
public enum InputCenterformInvalidCharacter {

    CURLY_BRACE_OPEN('{'),
    CURLY_BRACE_CLOSE('}'),
    PARENTHESIS_OPEN('('),
    PARENTHESIS_CLOSE(')'),
    EQUAL_SIGN('='),
    AMPERSAND('&'),
    SEMICOLON(';'),
    DOLLAR_SIGN('$'),
    QUESTION_MARK('?'),
    ASTERISK('*'),
    DABLE_COTE('"'),
    SHINGLE_COTE('\'');

    private final char character;

    InputCenterformInvalidCharacter(char character) {
        this.character = character;
    }

    public char getCharacter() {
        return character;
    }
}
