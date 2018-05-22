package com.gildedrose;

class GildedRose {
    public static final int MIN_QUALITY = 0;
    public static final int MAX_QUALITY = 50;
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            updateQualityItem(items[i]);
        }
    }

    private void updateQualityItem(Item item) {
        if (isSulfuras(item)) {
            return;
        }

        if (isAgedBrie(item)) {
            increaseQualityByOne(item);
            decreaseExpirationDaysByOne(item);
            if (hasExpired(item)) {
                increaseQualityByOne(item);
            }
        } else if (isBackstage(item)) {
            if (item.sellIn > 10) {
                increaseQualitySeveralTimes(item, 1);
            }
            if (item.sellIn > 5 && item.sellIn <= 10 ) {
                increaseQualitySeveralTimes(item, 2);
            }
            if (item.sellIn <= 5) {
                increaseQualitySeveralTimes(item, 3);
            }

            decreaseExpirationDaysByOne(item);
            if (hasExpired(item)) {
                lostAllQuality(item);
            }
        } else {
            decreaseQualityByOne(item);
            decreaseExpirationDaysByOne(item);
            if (hasExpired(item)) {
                decreaseQualityByOne(item);
            }
        }
    }

    private void increaseQualitySeveralTimes(Item item, int times) {
        for (int i = 0; i < times ; i++) {
            increaseQualityByOne(item);
        }
    }

    private void decreaseExpirationDaysByOne(Item item) {
        item.sellIn = item.sellIn - 1;
    }

    private boolean hasExpired(Item item) {
        return item.sellIn < 0;
    }

    private void lostAllQuality(Item item) {
        item.quality = 0;
    }

    private void increaseQualityByOne(Item item) {
        if (item.quality < MAX_QUALITY) {
            item.quality = item.quality + 1;
        }
    }

    private void decreaseQualityByOne(Item item) {
        if (item.quality > MIN_QUALITY) {
            item.quality = item.quality - 1;
        }
    }

    private boolean isSulfuras(Item item) {
        return item.name.equals("Sulfuras, Hand of Ragnaros");
    }

    private boolean isBackstage(Item item) {
        return item.name.equals("Backstage passes to a TAFKAL80ETC concert");
    }

    private boolean isAgedBrie(Item item) {
        return item.name.equals("Aged Brie");
    }
}
