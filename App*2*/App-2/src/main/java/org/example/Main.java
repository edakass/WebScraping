package org.example;

import com.opencsv.CSVWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    /*
    ya da bu kategorileri diziye atılıp o şekilde yapılacak

    YADA TÜM BU KATEGORİLERİN OLDUĞU KISIM
     */
    public static void main(String[] args) throws IOException { //

        /*
        JSOUP kütüphanesinin connect metodu ile bir web sayfası indirilir.
        Daha sonra, get metodu ile web sayfası parse edilir ve Document türünde bir nesne olarak saklanır.
        Bu nesnenin title metodu ile web sayfasının başlığı alınır
        */
        Document doc = Jsoup.connect("https://www.ebay.com/").get();
        System.out.println("***************************************************************");
        System.out.println("Title -> " + doc.title());
        System.out.println("***************************************************************");
        /*
         burada navbardaki css query ye erişmeye çalışıyoruz
         div in id si #maincontent
         hl-cat-nav__js-tab
         > a , burada demek istenilen a yı diğerlerinden sonra yölendirir
         şu linkte bir örnek var : https://jsoup.org/cookbook/extracting-data/selector-syntax
         bir nevi diğer alt öğeleri bulur
         */


        Elements navBarCategory = doc.select("#mainContent .hl-cat-nav__js-tab > a"); // Tüm bağlantıları seçer yani bu css query e dair

        PrintWriter out = new PrintWriter(new FileWriter("/Users/eda/Desktop/outputtext.txt"));

        List<String> categoryLinkList = new ArrayList<>();
        List<String> categoryNameList = new ArrayList<>();

        for (Element element : navBarCategory) { // Element ile kast edilen belli bir öğe, tüm elementleri teker teker yazdırıyo
            String categoryName = element.text(); // category name'i getiriyor
            String link = element.absUrl("href"); // linki getiriyor

            if (categoryName.equals("Deals")) { //burada if i kullandım çünkü navbarda ki kategorilerde deals i ve sonrasındakini yazdırmasın
                break;
            }
             System.out.println("---- Category : " + categoryName + " | Link :" + link + " ----");// Yukarıdaki işlemlerde kategori name leri ve linkleri yazdırılmış oldu

            categoryNameList.add(categoryName);
            out.println("");
            out.print("--- CATEGORY : " + categoryName + " | Link :" + link);
            out.println("");
            //out.println("");
            //out.println("");

            System.out.println(); //şimdi ise bu alt kategorilernde alt kategorilerini yazdırmam lazım

            Document categoryPage = Jsoup.connect(link).get(); //burada her bir categoryPage e yukardaki her bir kategorinin linkine bağlanmak için kullanılıyor

            Elements links = categoryPage.select("a"); //category page deki cssquerysi a olan tüm linkler

            // O ZAMAN İÇERİSİNDE SÜREKLİ İÇ İÇE FOR LAR OLACAK O ZAMAN
            for (Element item : links) { //bunları for ile getirme , yani linki bulunan hepsini getirme
                String subLinkText = item.text();
                String subLinkUrl = item.absUrl("href");


                // if(subLinkUrl.equals("") || subLinkUrl.equals("https://www.ebay.com/mys/home?source=GBH")){
                     //continue; // break demek istiyorum ama burada diğerlerini yoksaycak}
                categoryLinkList.remove("https://www.ebay.com/adchoice");

                //subLinkUrl.equalsIgnoreCase("https://www.ebay.com/adchoice");
                  System.out.println("   Sublink Text " + subLinkText);


                  System.out.println("   Sublink url " + subLinkUrl);

                //out.println("");
                // out.print(subLinkUrl);
                //out.print(subLinkText);

                categoryLinkList.add(link);
            }
        }

        System.out.println(" - - - - - - - - - -");
        System.out.println("Category Name:");
        for (String categoryName : categoryNameList) {
            System.out.println("   " + categoryName);
        }
        System.out.println(" - - - - - - - - - -");

        // System.out.println("Category Links:");
        for (String categoryLink : categoryLinkList) {
           // if(categoryLink.equals("https://www.ebay.com/adchoice"))
          //  categoryLink.equalsIgnoreCase("https://www.ebay.com/adchoice");
            System.out.println(categoryLink);
        }


        out.close();
    }
}
