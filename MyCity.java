//Name: Duy Vu
//Date:10/28/21
//Class: CSS436
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.TimeUnit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.json.JSONObject;
import org.json.JSONArray;
public class MyCity {
    static String latitude;
    static String longitude;
    static String merge;
    public static void main(String[] args)throws Exception{
        try{
            String temp="";
            String City;
            if(args.length==0){
            System.out.println("Please Try Again and With A City Name Entered!");
                System.exit(0);
            }else{
                for(int i=0;i<args.length;i++){
                    temp+=args[i];
                    if(i!=args.length-1){
                        temp+=" ";
                    }
                }
            }

            City=temp;
            WeatherAPI(City);
            CityAPI(merge);
        }catch(IOException e){

        }
    }
    //GetWeatherData
    //This get the selected data from the json object and display to the console
    public static void GetWeatherData(String json){
        JSONObject Store= new JSONObject(json);
        
        //Get the city name from the json content
        //Print out the statement
        String Name= (String) Store.get("name");
        System.out.println("-----------------------------------------");
        System.out.println(Name+"'s Current Weather:");
        System.out.println("-----------------------------------------");

        //Get the weather data from the json content
        //Print out the statement
        var Weather =  ((JSONObject)((JSONArray)Store.get("weather")).get(0)).get("main");
        System.out.println("Weather: "+Weather);
       
        //Get the weather data from the json content
        //Print out the statement
        var Description =  ((JSONObject)((JSONArray)Store.get("weather")).get(0)).get("description");
        System.out.println("Description: "+ Description);

        //double Temperature= ((JSONObject) Store.get("main")).get("temp").toString();
        //Get the temperature data from the json content
        //Print out the statement
        int Temperature = ((JSONObject) Store.get("main")).getInt("temp")-273 ;
        System.out.println("Temperature: "+Temperature+"°C");

        //Get the humidity data from the json content
        //Print out the statement
        String Humidity= ((JSONObject) Store.get("main")).get("humidity").toString();
        System.out.println("Humidity: "+Humidity+"%");

        //Get the wind data from the json content
        //Print out the statement
        String Wind= ((JSONObject) Store.get("wind")).get("speed").toString();
        System.out.println("Wind Speed: "+Wind+"m/s");

        //Get the visibility data from the json content
        //Print out the statement
        double Visibility= Store.getDouble("visibility")/1000;
        System.out.println("Visibility: "+ Math.round(Visibility*100.0)/100.0+"km");
        System.out.println("-----------------------------------------\n");

        //Get the longitude data from the json content
        String Templongitude= ((JSONObject) Store.get("coord")).get("lon").toString();

        //Check longitude for plus or minus and modify it
        //Get the latitude data from the json content
        longitude=CheckLon(Templongitude);
        latitude=  ((JSONObject) Store.get("coord")).get("lat").toString();

        //Combine both latitude and longitude
        merge= latitude+longitude;
    }

    //WeatherAPI
    //This function open the connection to the API with the location and API key
    //Open and get its content and call GetWeatherData for printing.
    public static void WeatherAPI(String City) throws IOException, InterruptedException{
        String APIkey=""; //enter key here
        String APIcall="https://api.openweathermap.org/data/2.5/weather?q="+City+"&appid="+APIkey;

        //Use to store the read in content
        StringBuilder build= new StringBuilder();

        //Input URL and open the API connection.
        URL url= new URL(APIcall);
        HttpURLConnection connection= (HttpURLConnection)url.openConnection();
        int statusCode= connection.getResponseCode();
        int count=0;
        boolean retry=false;

        //Check for connection or back off and retry
        while(count!=4){ 

            //No more retry after this
            if(count==3){
                System.out.println("Can't connect to the server after many tries. Terminating the program.");
                System.exit(0);
            }

            //If connection is bad, retry and back off
            if(statusCode!=200){
                if(retry==false){
                    System.out.println("Error: City not found or failed to connect to the server");
                    retry=true;
                }
                if(retry==true){
                    System.out.println("Retry: "+(count+1));
                }
                TimeUnit.SECONDS.sleep(3);
                connection=(HttpURLConnection)url.openConnection();
                statusCode=connection.getResponseCode();
            }else{
                break;
            }
            count++;
        }
        
        //Read and store the content
        String content;
        BufferedReader reader= new BufferedReader(new InputStreamReader(connection.getInputStream()));
        while((content=reader.readLine())!=null){
            build.append(content);
        }

        //Call this to print the data with the json body as input
        GetWeatherData(build.toString());
    }

    //CityAPI
    //This function open the connection to the API with the location and API key
    //Open and get its content and call GetCityData for printing.
    public static void CityAPI(String location) throws IOException, InterruptedException{

        //This open the connection to city API
        HttpClient client= HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
		.uri(URI.create("https://wft-geo-db.p.rapidapi.com/v1/geo/locations/"+location+"/nearbyCities?radius=100&limit=10"))
		.header("x-rapidapi-host", "wft-geo-db.p.rapidapi.com")
		.header("x-rapidapi-key", "") //enter key here
		.method("GET", HttpRequest.BodyPublishers.noBody())
		.build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        int count=0;
        boolean retry=false;

        //Check for connection or back off and retry
        while(count!=4){ 

            //No more retry after this
            if(count==3){
                System.out.println("Can't connect to the server after many tries. Terminating the program.");
                System.exit(0);
            }

            //If connection is bad, retry and back off
            if(response.statusCode()!=200){
                if(retry==false){
                    System.out.println("Error: City not found or failed to connect to the server");
                    retry=true;
                }
                if(retry==true){
                    System.out.println("Retry: "+(count+1));
                }
                TimeUnit.SECONDS.sleep(3);
                response=client.send(request, BodyHandlers.ofString());
            }else{
                break;
            }
            count++;
        }
    
        //Call this to print the data with the json body as input
        GetCityData(response.body());
    }

    //GetData
    //This get the selected data from the json object and display to the console
    public static void GetCityData(String json){
        JSONObject store= new JSONObject(json);
        JSONArray list= (JSONArray) store.get("data");
        
        //Title
        System.out.println("-----------------------------------------");
        System.out.println("Nearby Cities Affected By The Weather:");
        System.out.println("-----------------------------------------");

        //loop through and print out the json content until there is no more
        for(int i=0;i<list.length();i++){
            JSONObject getInfo=list.getJSONObject(i);
            String city= getInfo.getString("name");
            System.out.println((i+1)+". "+city);
        }
        //End
        System.out.println("-----------------------------------------");
        System.out.println("Thank You For Using This Program. End!");
        System.out.println("-----------------------------------------");
    }
    
    //CheckLon
    //Check the longitude for plus and minus
    //add in plus sign if logitude is positive
    public static String CheckLon(String longitude){
        String temp;
        if(longitude.charAt(0)!='-'){
            temp= '+'+longitude;
            return temp;
        }else{
            return longitude;
        }
    }
}
