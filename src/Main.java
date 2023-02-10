import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Scanner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {

    private static String getCountryDetails(String input) {
        try {
            URL url = new URL("https://restcountries.com/v2/name/" + input + "?fullText=true");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                return "No se encontraron detalles para el país especificado";
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            return response.toString();
        } catch (Exception e) {
            return "Ha ocurrido un error al obtener los detalles del país";
        }
    }

    public static void main(String[] args) {


        //MOSTRAR TODOS LOS PAISES USANDO API REST
        try {
            URL url = new URL("https://restcountries.com/v2/all");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            System.out.println("ESTE ES EL LISTADO DE TODOS LOS PAISES");
            while ((line = br.readLine()) != null) {
                response.append(line);
            }

            JSONArray jsonArray = new JSONArray(response.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                System.out.println(jsonObject.getString("name"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("INTRODUCE EL NOMBRE DEL PAIS (EN INGLÉS): ");
        String input = scanner.nextLine();
        scanner.close();

        String pais = getCountryDetails(input);
        System.out.println(pais);



    }
}
