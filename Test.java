/*import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
*/
public class Test {
    public static void main(String args[]){
        /*HttpRequest request = HttpRequest.newBuilder()
		.uri(URI.create("https://wft-geo-db.p.rapidapi.com/v1/geo/locations/47.6062-122.3321/nearbyCities?limit=5&offset=0&radius=100"))
		.header("x-rapidapi-host", "wft-geo-db.p.rapidapi.com")
		.header("x-rapidapi-key", "06af70c418msh322a646ea89eae3p1f82d6jsn1150571f854c")
		.method("GET", HttpRequest.BodyPublishers.noBody())
		.build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        */
        /*
        String test1="22.2855";
        String test2="-114.1577";
        if(test1.charAt(0)!='-'){
            test1='+'+test1;
            System.out.println(test1);
        }
        if(test2.charAt(0)!='-'){
            System.out.println(test2);
        }
        System.out.println(test2);
        */
        /*String temp="";
            String City;
            if(args.length==0){
            System.out.println("Please Try Again and With A City Name Entered!");
            }else{
                System.out.println(args.length);
                for(int i=0;i<args.length;i++){
                    temp+=args[i];
                    if(i!=args.length-1){
                        temp+=" ";
                    }
                }
            }
            City=temp;
        System.out.println(City);*/
        int count = 1;
        do{
            System.out.println("Count is: " + count);
            count++;
        } while (count < 11);
    }
     
}
