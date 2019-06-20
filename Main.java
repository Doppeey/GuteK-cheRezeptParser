import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException {


        Scanner reader = new Scanner(System.in);
        System.out.println("What would you like to cook today?");
        String meal = reader.nextLine();

        StringBuilder searchQuery = new StringBuilder();

        final String[] split = meal.split(" ");

        for (int i = 0; i < split.length; i++) {
            if(i == split.length -1){
                searchQuery.append(split[i]);
            } else {
                searchQuery.append(split[i]);
                searchQuery.append("+");
            }
        }

        Document searchResultsPage = Jsoup.connect("https://www.gutekueche.at/suche?s="+searchQuery.toString()).timeout(5000).get();
        String recipeId = searchResultsPage.getElementsByTag("h3").first().getElementsByTag("a").first().attr("href");


        System.out.println("\n");

        Document doc = Jsoup.connect("https://www.gutekueche.at/"+recipeId).timeout(5000).get();
        System.out.println(doc.title()+"\n");
        Elements amounts = doc.getElementsByTag("tr");

        StringBuilder recipe = new StringBuilder();

        for(Element ele : amounts){
            recipe.append(ele.getElementsByTag("td").first().attr("data-amount"));
            recipe.append(" ");
            recipe.append(ele.getElementsByTag("th").first().text());
            recipe.append(" ");
            recipe.append(ele.getElementsByTag("th").get(1).text());
            recipe.append("\n");
        }

        Element instructions = doc.getElementsByClass("sec rezept-preperation").first();
        Elements steps = instructions.getElementsByTag("li");


        recipe.append("\n");
        recipe.append("Zubereitung:\n\n");

        int counter = 1;


        for(Element ele : steps){
            recipe.append(counter);
            recipe.append(": ");
            recipe.append(ele.text());
            recipe.append("\n\n");
            counter++;
        }

        System.out.println(recipe.toString());




    }

}
