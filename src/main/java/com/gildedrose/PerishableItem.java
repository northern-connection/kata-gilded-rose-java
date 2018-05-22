package com.gildedrose;

public abstract class PerishableItem {
    private static final int MAX_QUALITY = 50;
    Item item;

    PerishableItem(Item item) {
        this.item = item;
    }

    void decreaseExpirationDaysByOne(Item item) {
        item.sellIn = item.sellIn - 1;
    }

    boolean hasExpired(Item item) {
        return item.sellIn < 0;
    }

    void increaseQualityByOne(Item item) {
        if (item.quality < MAX_QUALITY) {
            item.quality = item.quality + 1;
        }
    }

    public abstract void age();
}
