package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Main {
    /*
    YAPILACAKLAR
    - ya tüm kategoriler için for döngüsü yazılacak
    - https://www.ebay.com/ bu ana sayfadan all kategories kısmından selektörü gir
    - her kategoriden de alt kategoriyi yazdıracak
    - ve en sonda bir dosyaya yazılcak
    - ya da e bay ekranından sort baya tıklasın sonra o ekrandakılerın yenı bır sayfadan acıp
    oradakı a hreflerı lısteleyebılırım

    Acaba tüm kategorılerın basındakı class aynıysa onlaı da getırebılırım
     */

    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://www.ebay.com/").get();
        System.out.println("Title " + doc.title());

        System.out.println("NAVBARDAKI Category");

        Elements navBar = doc.select("#mainContent .hl-cat-nav__js-tab > a");

        System.out.println();
        for (Element item : navBar) {
            String categoryName = item.text();
            String link = item.attr("href");
              /*href ile ilgili 3 tip var hangisi daha iyi bilmiyorum
            link.attr("href") − provides the value of href present in anchor tag. It may be relative or absolute.
            link.attr("abs:href") − provides the absolute url after resolving against the document's base URI.
            link.absUrl("href")  (site links : https://www.tutorialspoint.com/jsoup/jsoup_use_url.htm) */

            if(categoryName.equals("Deals")){
                break;
            }
            System.out.println("Category: " + categoryName);
            System.out.println("Link: " + link);
            System.out.println();
        }

    }
}
