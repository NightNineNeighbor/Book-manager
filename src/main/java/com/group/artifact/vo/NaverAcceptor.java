package com.group.artifact.vo;

import java.util.*;

public class NaverAcceptor {
    private List<CrawledBook> items = new ArrayList<>();
    private int total;

    public List<CrawledBook> getItems() {
        return items;
    }

    public CrawledBook getFirstItem(){
        if (items.size() == 0 ) {
            return CrawledBook.EMPTY;
        }
        return items.get(0);
    }

    public void setItems(List<CrawledBook> items) {
        this.items = items;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "NaverAcceptor{" +
                "items=" + items +
                '}';
    }
}
