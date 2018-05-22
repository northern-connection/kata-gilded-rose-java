package com.gildedrose;

public class AgedBrie extends PerishableItem {

    AgedBrie(Item item) {
        super(item);
    }

    public void age() {
        increaseQualityByOne(item);
        decreaseExpirationDaysByOne(item);
        if (hasExpired(item)) {
            increaseQualityByOne(item);
        }
    }
}
