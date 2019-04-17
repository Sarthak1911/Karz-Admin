package com.example.deeppatel.car_rerntal.Renting_Process.DataEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data_Searched_Customer {
    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem2> ITEMS = new ArrayList<DummyItem2>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem2> ITEM_MAP = new HashMap<String, DummyItem2>();

    private static final int COUNT = 5;

    public static void CreateList(String customer_name) {
        // Add some sample items.
        ITEMS.clear();
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(customer_name,i));
        }
    }

    private static void addItem(DummyItem2 item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DummyItem2 createDummyItem(String customer_name,int position) {
        return new DummyItem2(String.valueOf(position),   customer_name, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("#License Number ");

        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem2 {
        public final String id;
        public final String content;
        public final String details;

        public DummyItem2(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
