package com.gildedrose;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GildedRoseTest {

    private static final String BACKSTAGE_NAME = "Backstage passes to a TAFKAL80ETC concert";
    private static final String NORMAL_ITEM_NAME = "foo";
    private static final String AGED_BRIE_NAME = "Aged Brie";
    private static final String SULFURAS_NAME = "Sulfuras, Hand of Ragnaros";

    private static final int QUALITY_CHANGE = 1;
    private static final int NORMAL_ITEM_QUALITY = 5;
    private static final int MINIMUM_QUALITY = 0;
    private static final int MAXIMUM_QUALITY = 50;

    private static final int SELL_IN_CHANGE = 1;
    private static final int EXPIRED_SELL_IN = 0;
    private static final int NORMAL_ITEM_SELL_IN = 15;
    private static final int SELL_IN_IN_5_DAYS = 5;
    private static final int SELL_IN_IN_10_DAYS = 10;
    private static final int SELL_IN_IN_11_DAYS = 11;
    private static final int SELL_IN_IN_6_DAYS = 6;
    private static final int SELL_IN_IN_1_DAYS = 1;

    @Test
    public void sell_in_decreases_every_day() {
        verifySellIn(NORMAL_ITEM_SELL_IN - SELL_IN_CHANGE, normalItem());
    }

    @Test
    public void quality_decreases_every_day() {
        verifyQuality(NORMAL_ITEM_QUALITY - QUALITY_CHANGE, normalItem());
    }

    @Test
    public void quality_decreases_twice_as_fast_one_sell_in_has_passed() {
        verifyQuality(NORMAL_ITEM_QUALITY - 2 * QUALITY_CHANGE, expiredItem());
    }

    @Test
    public void quality_is_never_negative() {
        verifyQuality(MINIMUM_QUALITY, itemWithoutQuality());
    }

    @Test
    public void aged_brie_increases_quality() {
        verifyQuality(NORMAL_ITEM_QUALITY + QUALITY_CHANGE, agedBrie());
    }

    @Test
    public void item_never_increase_quality_when_has_reached_the_maximum() {
        verifyQuality(MAXIMUM_QUALITY, agedBrieWithMaximumQuality());
    }

    @Test
    public void sulfuras_never_changes_the_quality() {
        verifyQuality(NORMAL_ITEM_QUALITY, sulfuras());
    }

    @Test
    public void sulfuras_never_changes_the_sell_in() {
        verifySellIn(NORMAL_ITEM_SELL_IN, sulfuras());
    }

    @Test
    public void backstage_increase_quality() {
        verifyQuality(NORMAL_ITEM_QUALITY + QUALITY_CHANGE, backstage());
    }

    @Test
    public void backstage_increase_quality_by_1_when_sell_in_11_days() {
        verifyQuality(NORMAL_ITEM_QUALITY + QUALITY_CHANGE, backstageInIn11Days());
    }

    @Test
    public void backstage_increase_quality_by_2_when_sell_in_is_10_or_less() {
        verifyQuality(NORMAL_ITEM_QUALITY + 2 * QUALITY_CHANGE, backstageInIn10Days());
    }

    @Test
    public void backstage_increase_quality_by_2_when_sell_in_is_6() {
        verifyQuality(NORMAL_ITEM_QUALITY + 2 * QUALITY_CHANGE, backstageIn6Days());
    }

    @Test
    public void backstage_increase_quality_by_3_when_sell_in_is_5_or_less() {
        verifyQuality(NORMAL_ITEM_QUALITY + 3 * QUALITY_CHANGE, backstageIn5Days());
    }

    @Test
    public void backstage_increase_quality_by_3_when_sell_in_is_1_or_less() {
        verifyQuality(NORMAL_ITEM_QUALITY + 3 * QUALITY_CHANGE, backstageIn1Days());
    }

    @Test
    public void backstage_drops_to_0_quality_after_the_concert() {
        verifyQuality(MINIMUM_QUALITY, backstageAfterTheConcert());
    }

    @Test
    public void aged_brie_expired_gets_the_maximum_quality_when_has_the_quality_minus_1() {
        Item item = new Item(AGED_BRIE_NAME, EXPIRED_SELL_IN, MAXIMUM_QUALITY - 1);
        verifyQuality(MAXIMUM_QUALITY, item);
    }

    private void verifySellIn(int expectedSellIn, Item item) {
        GildedRose gildedRose = gildedRose(item);
        gildedRose.updateQuality();
        assertSellIn(expectedSellIn, gildedRose);
    }

    private void verifyQuality(int expectedQuality, Item normalItem) {
        GildedRose gildedRose = gildedRose(normalItem);
        gildedRose.updateQuality();
        assertQuality(expectedQuality, gildedRose);
    }

    private GildedRose gildedRose(Item item) {
        Item[] items = new Item[]{item};
        return new GildedRose(items);
    }

    private Item normalItem() {
        return itemWithNormalSellInAndQuality(NORMAL_ITEM_NAME);
    }

    private Item expiredItem() {
        return itemWithNormalQuality(NORMAL_ITEM_NAME, EXPIRED_SELL_IN);
    }

    private Item itemWithoutQuality() {
        return new Item(NORMAL_ITEM_NAME, NORMAL_ITEM_SELL_IN, MINIMUM_QUALITY);
    }

    private Item agedBrie() {
        return itemWithNormalSellInAndQuality(AGED_BRIE_NAME);
    }

    private Item agedBrieWithMaximumQuality() {
        return new Item(AGED_BRIE_NAME, NORMAL_ITEM_SELL_IN, MAXIMUM_QUALITY);
    }

    private Item sulfuras() {
        return itemWithNormalSellInAndQuality(SULFURAS_NAME);
    }

    private Item backstage() {
        return itemWithNormalSellInAndQuality(BACKSTAGE_NAME);
    }

    private Item backstageInIn10Days() {
        return itemWithNormalQuality(BACKSTAGE_NAME, SELL_IN_IN_10_DAYS);
    }

    private Item backstageInIn11Days() {
        return itemWithNormalQuality(BACKSTAGE_NAME, SELL_IN_IN_11_DAYS);
    }

    private Item backstageIn6Days() {
        return itemWithNormalQuality(BACKSTAGE_NAME, SELL_IN_IN_6_DAYS);
    }

    private Item backstageIn5Days() {
        return itemWithNormalQuality(BACKSTAGE_NAME, SELL_IN_IN_5_DAYS);
    }

    private Item backstageIn1Days() {
        return itemWithNormalQuality(BACKSTAGE_NAME, SELL_IN_IN_1_DAYS);
    }

    private Item backstageAfterTheConcert() {
        return itemWithNormalQuality(BACKSTAGE_NAME, EXPIRED_SELL_IN);
    }

    private Item itemWithNormalSellInAndQuality(String name) {
        return itemWithNormalQuality(name, NORMAL_ITEM_SELL_IN);
    }

    private Item itemWithNormalQuality(String name, int sellIn) {
        return new Item(name, sellIn, NORMAL_ITEM_QUALITY);
    }

    private void assertSellIn(int expectedSellIn, GildedRose app) {
        assertEquals(expectedSellIn, app.items[0].sellIn);
    }

    private void assertQuality(int expectedQuality, GildedRose gildedRose) {
        assertEquals(expectedQuality, gildedRose.items[0].quality);
    }
}