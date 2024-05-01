import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String titleNew = "Cover of The Hobbit: The Motion Picture Trilogy";
        String description ="The Hobbit is a series of three epic high fantasy adventure films directed by Peter Jackson. The films are subtitled An Unexpected Journey (2012), The Desolation of Smaug (2013), and The Battle of the Five Armies (2014).[5] The films are based on the 1937 novel The Hobbit by J. R. R. Tolkien, with large portions of the trilogy inspired by the appendices to The Return of the King, which expand on the story told in The Hobbit, as well as new material and characters written especially for the films. Together they act as a prequel to Jackson's The Lord of the Rings film trilogy.";
        ArrayList<String> actors = new ArrayList<>();
        actors.add("Ian McKellen");
        actors.add("Martin Freeman");
        actors.add("Richard Armitage");
        int year = 2012;
        String img = "https://upload.wikimedia.org/wikipedia/en/a/a9/The_Hobbit_trilogy_dvd_cover.jpg";
        addNewFilm(titleNew, description, actors,year, img);
        String jsonString = readJSON();
        if (jsonString != null) {
            ArrayList<String> titles = extractTitles(jsonString);
            System.out.println("Titles:");
            for (String title : titles) {
                System.out.println(title);
            }
        }
    }

    private static String readJSON() {
        try {
            FileReader reader = new FileReader("src/main/java/JSONFile/films.json");
            StringBuilder stringBuilder = new StringBuilder();
            int character;
            while ((character = reader.read()) != -1) {
                stringBuilder.append((char) character);
            }
            reader.close();
            return stringBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ArrayList<String> extractTitles(String jsonString) {
        ArrayList<String> titles = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            JSONArray jsonArray = (JSONArray) parser.parse(jsonString);
            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                String title = (String) jsonObject.get("title");
                titles.add(title);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return titles;
    }
    private static void addNewFilm(String title, String description, ArrayList actors, int year, String img) {
        try {
            // Чтение JSON из файла
            FileReader reader = new FileReader("src/main/java/JSONFile/films.json");
            JSONParser jsonParser = new JSONParser();
            JSONArray filmsArray = (JSONArray) jsonParser.parse(reader);
            reader.close();

            // Создание нового фильма
            JSONObject newFilm = new JSONObject();
            newFilm.put("title", title);
            newFilm.put("desc", description);
            newFilm.put("actors", actors);
            newFilm.put("year", year);
            newFilm.put("img", img);

            // Добавление нового фильма в массив фильмов
            filmsArray.add(newFilm);

            // Запись обновленного JSON обратно в файл
            FileWriter writer = new FileWriter("src/main/java/JSONFile/films.json");
            writer.write(filmsArray.toJSONString());
            writer.close();

            System.out.println("Новый фильм успешно добавлен в JSON!");

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
