package service;

import static org.junit.jupiter.api.Assertions.*;

class WatermanItemParserServiceTest {

    @org.junit.jupiter.api.Test
    void getWatermanItemByCodeAloneItem() {
        var parser = new WatermanItemParserService();
        var itemTest = parser.getWatermanItemByCode(164216L);
        // Проверь цену на http://www.waterman-t.ru/products/565a3210-e95d-11e8-92c8-001e6727034e и подставь в ассерт
        assertEquals(itemTest.getPrice().longValue(), 1309L);
    }
}