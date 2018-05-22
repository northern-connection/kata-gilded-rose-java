package com.gildedrose;

class GildedRose {
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
		if (!isAgedBrie(item) && !isBackstage(item)) {
			if (item.quality > 0) {
				if (!isSulfuras(item)) {
					item.quality = decreaseQualityByOne(item);
				}
			}
		} else {
			if (item.quality < 50) {
				item.quality = increaseQualityByOne(item);

				if (isBackstage(item)) {
					if (item.sellIn < 11) {
						if (item.quality < 50) {
							item.quality = increaseQualityByOne(item);
						}
					}

					if (item.sellIn < 6) {
						if (item.quality < 50) {
							item.quality = increaseQualityByOne(item);
						}
					}
				}
			}
		}

		if (!isSulfuras(item)) {
			item.sellIn = item.sellIn - 1;
		}

		if (item.sellIn < 0) {
			if (!isAgedBrie(item)) {
				if (!isBackstage(item)) {
					if (item.quality > 0) {
						if (!isSulfuras(item)) {
							item.quality = decreaseQualityByOne(item);
						}
					}
				} else {
					item.quality = item.quality - item.quality;
				}
			} else {
				if (item.quality < 50) {
					item.quality = increaseQualityByOne(item);
				}
			}
		}
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
