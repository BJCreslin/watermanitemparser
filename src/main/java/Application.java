import service.WatermanItemParserService;

public class Application {
    public static void main(String[] args) {
        WatermanItemParserService wips = new WatermanItemParserService();
        wips.getWatermanItemByCode(2124L);
    }
}
