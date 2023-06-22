package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    /*
     * This application obtains all category information from ebay.com
     */
    public static void main(String[] args) throws IOException {

        Document doc = Jsoup.connect("https://www.ebay.com/").get();
        System.out.println("***************************************************************");
        System.out.println("Title -> " + doc.title());
        System.out.println("***************************************************************");

        Elements navBarCategory = doc.select("#mainContent .hl-cat-nav__js-tab > a");

        PrintWriter out = new PrintWriter(new FileWriter("/Users/eda/Desktop/outputtext.txt"));


        for (Element element : navBarCategory) {
            String categoryName = element.text();
            String link = element.absUrl("href");

            if (categoryName.equals("Deals")) {
                break;
            }

            System.out.println("---- Category : " + categoryName + " | URL :" + link + " ----");

            out.println("");
            out.print("--- CATEGORY : " + categoryName + " | URL :" + link);
            out.println("");

            System.out.println();

            Document categoryPage = Jsoup.connect(link).get();

            Elements links = categoryPage.select("a");

            for (Element item : links) {
                String subLinkText = item.text();
                String subLinkUrl = item.absUrl("href");

                Pattern categoryPattern = Pattern.compile("https://www.ebay.com/b/", Pattern.CASE_INSENSITIVE);
                Matcher categoryMatcher = categoryPattern.matcher(subLinkUrl);

                Pattern hashtagPattern = Pattern.compile("#[a-zA-Z0-9]+", Pattern.CASE_INSENSITIVE);
                Matcher hashtagMatcher = hashtagPattern.matcher(subLinkUrl);

                boolean flag = false;
                if (!hashtagMatcher.find()) {
                    flag = true;
                }

                if (categoryMatcher.find() && flag) {
                    System.out.println("   Category Name --> " + subLinkText);
                    System.out.println("   Category URL  --> " + subLinkUrl);
                    out.println("");
                    out.print(subLinkUrl);
                    out.print(subLinkText);
                }
            }
        }

        out.close();
    }
}