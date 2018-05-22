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
        if (!isAgedBrie(item) && !isBackstage(item) && !isSulfuras(item)) {
            if (item.quality > MIN_QUALITY) {
                item.quality = decreaseQualityByOne(item);
            }
        }
        if (isAgedBrie(item))
        {
            if (item.quality < MAX_QUALITY) {
                item.quality = increaseQualityByOne(item);
            }
        }

        if (isBackstage(item))
        {
            if (item.quality < MAX_QUALITY) {
                item.quality = increaseQualityByOne(item);

                if (isBackstage(item)) {
                    if (item.sellIn < 11) {
                        if (item.quality < MAX_QUALITY) {
                            item.quality = increaseQualityByOne(item);
                        }
                    }

                    if (item.sellIn < 6) {
                        if (item.quality < MAX_QUALITY) {
                            item.quality = increaseQualityByOne(item);
                        }
                    }
                }
            }
        }

        if (!isSulfuras(item)) {
            item.sellIn = decreaseStockByOne(item);
        }

        if (item.sellIn < 0) {
            if (!isAgedBrie(item)) {
                if (!isBackstage(item)) {
                    if (item.quality > MIN_QUALITY) {
                        if (!isSulfuras(item)) {
                            item.quality = decreaseQualityByOne(item);
                        }
                    }
                } else {
                    item.quality = 0;
                }
            } else {
                if (item.quality < MAX_QUALITY) {
                    item.quality = increaseQualityByOne(item);
                }
            }
        }
    }

    private int decreaseStockByOne(Item item) {
        return item.sellIn - 1;
    }

    private int increaseQualityByOne(Item item) {
        return item.quality + 1;
    }

    private int decreaseQualityByOne(Item item) {
        return item.quality - 1;
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
