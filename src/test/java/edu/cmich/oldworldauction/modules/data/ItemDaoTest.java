//package edu.cmich.oldworldauction.modules.data;
//
//import edu.cmich.oldworldauction.modules.models.AuctionItem;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.ValueSource;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * Tests the {@link ItemDao} class.
// *
// * @author Brock Jones
// */
//public class ItemDaoTest {
//
//    private static final ItemDao itemDao = new ItemDao();
//    @Test
//    public void nullItemNameThrowsIllegalArgumentException() {
//        AuctionItem auctionItem
//                = new AuctionItem(null, "Description", new BigDecimal("23.45"),
//                , "Blue", 2003, LocalDateTime.now().minusDays(5),
//                LocalDateTime.now().plusDays(5), "Username");
//
//        assertThrows(IllegalArgumentException.class, () -> itemDao.isAuctionItemValid(auctionItem));
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {"  ", "", "\n", "\t", "        "})
//    public void emptyItemNameThrowsIllegalArgumentException(String name) {
//        AuctionItem auctionItem
//                = new AuctionItem(name, "Description", new BigDecimal("23.45"),
//                new byte[5], "Blue", 2003, LocalDateTime.now().minusDays(5),
//                LocalDateTime.now().plusDays(5), "Username");
//
//        assertThrows(IllegalArgumentException.class, () -> itemDao.isAuctionItemValid(auctionItem));
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {"Chair", "Video Game", "Vinyl", "CD", "Floral Pattern Dish"})
//    public void validItemNameReturnsTrue(String name) {
//        AuctionItem auctionItem
//                = new AuctionItem(name, "Description", new BigDecimal("23.45"),
//                new byte[5], "Blue", 2003, LocalDateTime.now().minusDays(5),
//                LocalDateTime.now().plusDays(5), "Username");
//
//        assertTrue(itemDao.isAuctionItemValid(auctionItem));
//    }
//
//    @Test
//    public void nullItemDescriptionThrowsIllegalArgumentException() {
//        AuctionItem auctionItem
//                = new AuctionItem("Blue La-Z-Boy Chair", null, new BigDecimal("23.45"),
//                new byte[5], "Blue", 2003, LocalDateTime.now().minusDays(5),
//                LocalDateTime.now().plusDays(5), "username");
//
//        assertThrows(IllegalArgumentException.class, () -> itemDao.isAuctionItemValid(auctionItem));
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {"  ", "", "\n", "\t", "        "})
//    public void emptyItemDescriptionThrowsIllegalArgumentException(String description) {
//        AuctionItem auctionItem
//                = new AuctionItem("Blue La-Z-Boy Chair", description, new BigDecimal("23.45"),
//                new byte[5], "Blue", 2003, LocalDateTime.now().minusDays(5),
//                LocalDateTime.now().plusDays(5), "Username");
//
//        assertThrows(IllegalArgumentException.class, () -> itemDao.isAuctionItemValid(auctionItem));
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {"This is a chair.", "Video Game for the N64", "Michael Jackson Vinyl", "Taylor Swift CD", "Floral Pattern Dish.\nManufactured in China."})
//    public void validItemDescriptionReturnsTrue(String description) {
//        AuctionItem auctionItem
//                = new AuctionItem("Blue La-Z-Boy Chair", description, new BigDecimal("23.45"),
//                new byte[5], "Blue", 2003, LocalDateTime.now().minusDays(5),
//                LocalDateTime.now().plusDays(5), "username");
//
//        assertTrue(itemDao.isAuctionItemValid(auctionItem));
//    }
//
//    @Test
//    public void nullItemColorThrowsIllegalArgumentException() {
//        AuctionItem auctionItem
//                = new AuctionItem("Blue La-Z-Boy Chair", "Arm Chair. Like new.", new BigDecimal("23.45"),
//                new byte[5], null, 2003, LocalDateTime.now().minusDays(5),
//                LocalDateTime.now().plusDays(5), "user123");
//
//        assertThrows(IllegalArgumentException.class, () -> itemDao.isAuctionItemValid(auctionItem));
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {"  ", "", "\n", "\t", "        "})
//    public void emptyItemColorThrowsIllegalArgumentException(String color) {
//        AuctionItem auctionItem
//                = new AuctionItem("Blue La-Z-Boy Chair", "Description", new BigDecimal("23.45"),
//                new byte[5], color, 1993, LocalDateTime.now().minusDays(5),
//                LocalDateTime.now().plusDays(5), "user1234");
//
//        assertThrows(IllegalArgumentException.class, () -> itemDao.isAuctionItemValid(auctionItem));
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {"Green", "White", "Maroon", "Gold", "Silver"})
//    public void validItemColorReturnsTrue(String color) {
//        AuctionItem auctionItem
//                = new AuctionItem("Chair", "Description", new BigDecimal("23.45"),
//                new byte[5], color, 2003, LocalDateTime.now().minusDays(5),
//                LocalDateTime.now().plusDays(5), "user123456");
//
//        assertTrue(itemDao.isAuctionItemValid(auctionItem));
//    }
//
//    @Test
//    public void nullItemCurrentBidThrowsIllegalArgumentException(){
//        AuctionItem auctionItem
//                = new AuctionItem("Chair", "Description", null,
//                new byte[5], "Blue", 2013, LocalDateTime.now().minusDays(5),
//                LocalDateTime.now().plusDays(5), "user123");
//
//        assertThrows(IllegalArgumentException.class, () -> itemDao.isAuctionItemValid(auctionItem));
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {"-3.45", "90.345", "120.1234", "-45.34", "2.3"})
//    public void invalidItemCurrentBidThrowsIllegalArgumentException(String bid) {
//        AuctionItem auctionItem
//                = new AuctionItem("Blue La-Z-Boy Chair", "Description", new BigDecimal(bid),
//                new byte[5], "Green", 1993, LocalDateTime.now().minusDays(5),
//                LocalDateTime.now().plusDays(5), "Username");
//
//        assertThrows(IllegalArgumentException.class, () -> itemDao.isAuctionItemValid(auctionItem));
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {"0.00", "23.45", "6.23", "1200.00", "1235.67"})
//    public void validItemBidReturnsTrue(String bid) {
//        AuctionItem auctionItem
//                = new AuctionItem("Chair", "Description", new BigDecimal(bid),
//                new byte[5], "Gold", 2003, LocalDateTime.now().minusDays(5),
//                LocalDateTime.now().plusDays(5), "Username");
//
//        assertTrue(itemDao.isAuctionItemValid(auctionItem));
//    }
//
//    @ParameterizedTest
//    @ValueSource(ints = {-34, 2060, 2340, -500, -250})
//    public void invalidItemManuYearThrowsIllegalArgumentException(int year) {
//        AuctionItem auctionItem
//                = new AuctionItem("Blue La-Z-Boy Chair", "Description", new BigDecimal("3.45"),
//                new byte[5], "Green", year, LocalDateTime.now().minusDays(5),
//                LocalDateTime.now().plusDays(5), "User");
//
//        assertThrows(IllegalArgumentException.class, () -> itemDao.isAuctionItemValid(auctionItem));
//    }
//
//    @ParameterizedTest
//    @ValueSource(ints = {1999, 50, 1200, 2014, 1873})
//    public void validItemManuYearReturnsTrue(int year) {
//        AuctionItem auctionItem
//                = new AuctionItem("Chair", "Description", new BigDecimal("8.34"),
//                new byte[5], "Gold", year, LocalDateTime.now().minusDays(5),
//                LocalDateTime.now().plusDays(5), "User");
//
//        assertTrue(itemDao.isAuctionItemValid(auctionItem));
//    }
//
//    @Test
//    public void nullItemStartDateThrowsIllegalArgumentException(){
//        AuctionItem auctionItem
//                = new AuctionItem("Blue La-Z-Boy Chair", "Description", new BigDecimal("3.45"),
//                new byte[5], "Green", 1956, null,
//                LocalDateTime.now().plusDays(5), "Username");
//
//        assertThrows(IllegalArgumentException.class, () -> itemDao.isAuctionItemValid(auctionItem));
//    }
//
//    @Test
//    public void nullItemEndDateThrowsIllegalArgumentException(){
//        AuctionItem auctionItem
//                = new AuctionItem("Blue La-Z-Boy Chair", "Description", new BigDecimal("3.45"),
//                new byte[5], "Green", 1956, LocalDateTime.now().minusDays(5),
//                null, "Username");
//
//        assertThrows(IllegalArgumentException.class, () -> itemDao.isAuctionItemValid(auctionItem));
//    }
//
//}
