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
        if (isRegular(item)) {
            decreaseQualityByOne(item);
        }
        if (isAgedBrie(item)) {
            increaseQualityByOne(item);
        }

        if (isBackstage(item)) {
            increaseQualityByOne(item);
            if (item.sellIn < 11) {
                increaseQualityByOne(item);
            }

            if (item.sellIn < 6) {
                increaseQualityByOne(item);
            }
        }

        if (!isSulfuras(item)) {
            item.sellIn = decreaseExpirationDaysByOne(item);
        }

        if (item.sellIn < 0) {
            if (!isAgedBrie(item)) {
                if (!isBackstage(item)) {
                    if (!isSulfuras(item)) {
                        decreaseQualityByOne(item);
                    }
                } else {
                    item.quality = 0;
                }
            } else {
                increaseQualityByOne(item);
            }
        }
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

    private boolean isRegular(Item item) {
        return !isAgedBrie(item) && !isBackstage(item) && !isSulfuras(item);
    }

    private int decreaseExpirationDaysByOne(Item item) {
        return item.sellIn - 1;
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
