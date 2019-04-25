package service;

import lombok.extern.java.Log;
import model.WatermanItem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLOutput;

@Log
public class WatermanItemParserService {
    private final static String WATERMAN_ADDRESS = "http://www.waterman-t.ru/";
    private final static String WATERMAN_FIND_PAGE = "http://www.waterman-t.ru/search/result?q=";
    private final static String WATERMAN_ITEM_PAGE = "http://www.waterman-t.ru/products/";

    public WatermanItem getWatermanItemByCode(Long code) {
        var item = new WatermanItem();
        item.setCode(code);
        try {
            String addressParsingPage = WATERMAN_FIND_PAGE + code.toString();
            log.info("Parsing adress: " + addressParsingPage);
            Document document = Jsoup.connect(addressParsingPage).
                    userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.154 Safari/537.36").
                    get();
            // <div class="search--nav">
            Elements element = document.getElementsByClass("search--nav");
            //<a class="search--item products--item" data-code="88b44d61-8c05-11e8-a8cc-001e6727034e" href="/products/88b44d61-8c05-11e8-a8cc-001e6727034e">
            var elementForAddress = element.first().getElementsByClass("search--item products--item").first();
            String itemAdress = elementForAddress.attr("data-code");
            item.setAddress(itemAdress);


            String AddressItemPage = WATERMAN_ITEM_PAGE + itemAdress;
            Document itemDocument = Jsoup.connect(AddressItemPage).
                    userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.154 Safari/537.36").
                    get();
            log.info("Item :"+AddressItemPage);
            Element elements = itemDocument.getElementsByClass("product-config--table").first();
            for (Element elementsItem : itemDocument.getElementsByClass("_product-config--item")) {
                String codeElements = elementsItem.attr("data-sku").trim().replace(" ", "");
                log.info(codeElements);

                if (codeElements.equals(code.toString())) {
                    for (Element element1 : elementsItem.getElementsByTag("td")) {


                        if (element1.attr("data-th").equals("Наименование")) {
                            item.setName(element1.attr("data-th"));
                        }
                        String price = element1.getElementsByClass("js-actualPrice").outerHtml();
                        log.severe(price);
                        item.setPrice(new BigDecimal(price).setScale(2));
                    }

                }
            }

            log.info(item.toString());


        } catch (IOException e) {
            e.printStackTrace();
        }

        return item;
    }

}
