package com.group.artifact.vo;

import java.util.HashMap;
import java.util.Map;

public class BookNameAndAction {
    private static Map<String, Action> map = new HashMap<>();
    public static final BookNameAndAction EMPTY = new BookNameAndAction("", "");
    private String user;
    private String bookName;

    public BookNameAndAction(String user, String bookName) {
        this.user = user;
        this.bookName = bookName;
    }

    static void save(String user, Action action) {
        map.put(user, action);
    }

    public Action load() {
        return map.get(this.user);
    }

    public String getBookName() {
        return bookName;
    }

    public void resetAction() {
        this.map.remove(this.user);
    }
}
