package fi.oulu.tol.sqat.tests;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import fi.oulu.tol.sqat.GildedRose;
import fi.oulu.tol.sqat.Item;

public class GildedRoseTest {

    @Test
    public void testTheTruth() {
        assertTrue(true);
    }

    @Test
    public void exampleTest() {
        //create an inn, add an item, and simulate one day
        GildedRose inn = new GildedRose();
        inn.setItem(new Item("+5 Dexterity Vest", 10, 20));
        inn.oneDay();

        //access a list of items, get the quality of the one set
        List<Item> items = inn.getItems();
        int quality = items.get(0).getQuality();

        //assert quality has decreased by one
        assertEquals("Failed quality for Dexterity Vest", 19, quality);
    }

    //Test Aged Brie
    @Test
    public void AgedBrieQualityTest() {
        GildedRose inn = new GildedRose();
        inn.setItem(new Item("Aged Brie", 2, 0));
        inn.oneDay();

        List<Item> items = inn.getItems();
        int quality = items.get(0).getQuality();
        assertEquals("Aged Brie", 1, quality);
    }

    @Test
    public void AgedBrieZeroSellInTest() {
        GildedRose inn = new GildedRose();
        inn.setItem(new Item("Aged Brie", 0, 6));
        inn.oneDay();

        List<Item> items = inn.getItems();
        int quality = items.get(0).getQuality();
        assertEquals("Failed quality for Aged Brie", 8, quality);
    }

    @Test
    public void AgedBrieZeroSellIn50QualityTest() {
        GildedRose inn = new GildedRose();
        inn.setItem(new Item("Aged Brie", 0, 50));
        inn.oneDay();

        List<Item> items = inn.getItems();
        int quality = items.get(0).getQuality();

        assertEquals("Failed quality for Aged Brie", 50, quality);
    }

    //Test Backstage passes to a TAFKAL80ETC concert
    @Test
    public void BackStagePassQualityTest() {
        //Test quality after 1 day
        GildedRose inn = new GildedRose();
        inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20));
        inn.oneDay();
        List<Item> items = inn.getItems();
        int quality = items.get(0).getQuality();
        assertEquals("Failed quality for Backstage passes to a TAFKAL80ETC concert", 21, quality);
        //Test quality after 6 days
        for (int i = 0; i < 5; i++) {
            inn.oneDay();
        }
        quality = items.get(0).getQuality();
        assertEquals("Failed quality for Backstage passes to a TAFKAL80ETC concert", 27, quality);
        //Test quality after 11 days
        for (int i = 0; i < 5; i++) {
            inn.oneDay();
        }
        quality = items.get(0).getQuality();
        assertEquals("Failed quality for Backstage passes to a TAFKAL80ETC concert", 38, quality);
        //Test quality after sell in days
        for (int i = 0; i < 5; i++) {
            inn.oneDay();
        }
        quality = items.get(0).getQuality();
        assertEquals("Failed quality for Backstage passes to a TAFKAL80ETC concert", 0, quality);
    }

    @Test
    public void BackstagePasses2DaysLeftButQuality50Test() {
        GildedRose inn = new GildedRose();
        inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 2, 50));
        inn.oneDay();

        List<Item> items = inn.getItems();
        int quality = items.get(0).getQuality();
        int sellIn = items.get(0).getSellIn();

        assertEquals("Failed quality for Backstage passes to a TAFKAL80ETC concert", 50, quality);
        assertEquals("Failed sellIn for Backstage passes to a TAFKAL80ETC concert", 1, sellIn);
    }

    //Test Sulfuras, Hand of Ragnaros
    @Test
    public void SulfurasQualityTest() {
        GildedRose inn = new GildedRose();
        inn.setItem(new Item("Sulfuras, Hand of Ragnaros", 0, 80));
        List<Item> items = inn.getItems();
        Random random = new Random();
        for (int i = 0; i < random.nextInt(21); i++) {
            inn.oneDay();
        }
        int quality = items.get(0).getQuality();
        int sellIn = items.get(0).getSellIn();
        assertEquals("Failed quality for Sulfuras", 80, quality);
        assertEquals("Failed sellIn for Sulfuras", 0, sellIn);
    }

    //Test regular items
    @Test
    public void TestRegularItemDegradesInQuality() {
        GildedRose inn = new GildedRose();
        inn.setItem(new Item("foo", 10, 20));
        inn.setItem(new Item("+5 Dexterity Vest", 10, 20));
        inn.setItem(new Item("Random regular item 1", 8, 15));
        inn.setItem(new Item("Random regular item 2", 5, 10));
        List<Item> items = inn.getItems();
        int[] quality = new int[items.size()];
        int[] sellInLeft = new int[items.size()];
        for (int i = 0; i < items.size(); i++) {
            quality[i] = items.get(i).getQuality();
            sellInLeft[i] = items.get(i).getSellIn();
        }

        inn.oneDay();

        for (int i = 0; i < items.size(); i++) {
            int currentQuality = items.get(i).getQuality();
            int currentSellInLeft = items.get(i).getSellIn();
            assertEquals("Failed quality for regular item", quality[i] - 1, currentQuality);
            assertEquals("Failed SellIn for regular item", sellInLeft[i] - 1, currentSellInLeft);
        }
    }

    @Test
    public void ConjuredManaCakeQualityTest() {
        GildedRose inn = new GildedRose();
        inn.setItem(new Item("Conjured Mana Cake", 1, 6));
        inn.oneDay();
        inn.oneDay();
        List<Item> items = inn.getItems();
        int quality = items.get(0).getQuality();
        assertEquals("Failed quality for Conjured Mana Cake", 3, quality);
    }

	//Quality & SellIn Zero Test
    @Test
    public void QualityZeroTest() {
        GildedRose inn = new GildedRose();
        inn.setItem(new Item("Conjured Mana Cake", 4, 0));
        inn.oneDay();

        List<Item> items = inn.getItems();
        int quality = items.get(0).getQuality();

        assertEquals("Failed quality for Conjured Mana Cake", 0, quality);
    }

    @Test
    public void QualityAndSellInZeroTest() {
        GildedRose inn = new GildedRose();
        inn.setItem(new Item("Conjured Mana Cake", 0, 0));
        inn.oneDay();

        List<Item> items = inn.getItems();
        int quality = items.get(0).getQuality();

        assertEquals("Failed quality for Conjured Mana Cake", 0, quality);
    }

	//Test main method
    @Test
    public void MainTest() {
        GildedRose inn = new GildedRose();
        GildedRose.main(null);

        List<Item> items = inn.getItems();
        int quality = items.get(2).getQuality();

        assertEquals("Failed quality for Elixir of the Mongoose", 6, quality);
    }

    @Test
    public void MainPrintTest() {
        PrintStream old = System.out;

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        GildedRose.main(null);

        System.setOut(old);

        String output = new String(outContent.toByteArray());

        assertTrue(output.contains("OMGHAI!"));
    }
}
