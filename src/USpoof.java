import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Ian Anderson
 * 7/24/19
 */

public class USpoof {
    private static OkHttpClient client;
    private static Random rand;
    private static String user;
    private static String pass;
    private static String nid;
    private static String platform;
    private static String uuid;
    private static String loginKey = "";
    private static List<String> eventIDs = new ArrayList<>();
    private static List<String> eventDescriptions = new ArrayList<>();
    private static List<String> pointValues = new ArrayList<>();
    private static List<LocalDateTime> startTimes = new ArrayList<>();
    private static List<LocalDateTime> endTimes = new ArrayList<>();
    private static List<Double> generatedLatitidues = new ArrayList<>();
    private static List<Double> generatedLongitudes = new ArrayList<>();
    /**
     * USpoof: A SuperFanU Spoofer
     * @throws IOException
     */
    public static void main(String[] args) throws IOException
    {
        System.out.println("Welcome to USpoof 1.0\nBy Ian Anderson, 2019");
        Scanner scan = new Scanner(System.in);
        rand = new Random();
        System.out.println("Enter your username:");
        user = scan.nextLine();
        System.out.println("Enter your password:");
        pass = scan.nextLine();
        // nid changes school
        System.out.println("Select your school:\n" +
                "1.  UMass Lowell\n" +
                "2.  Drake University\n" +
                "3.  College of St. Rose\n" +
                "4.  Harvard University\n" +
                "5.  Boston University\n" +
                "6.  University of Maine\n" +
                "7.  Southeast Missouri State University\n" +
                "8.  California State University\n" +
                "9.  University of Toledo\n" +
                "10. Lee University\n" +
                "11. Fairfield University\n" +
                "12. Wichita State University\n" +
                "13. University of Kentucky\n" +
                "14. University of North Carolina at Charlotte\n" +
                "15. University of Pennsylvania\n" +
                "16. University of Hawaii at Manoa\n" +
                "17. Minot State University\n" +
                "18. Grand Valley State University\n" +
                "19. Keene State College\n" +
                "20. University of North Carolina at Pembroke\n" +
                "0.  Manual NID Entry");
        switch (scan.nextInt())
        {
            case 1: nid = "694";
            break;
            case 2: nid = "627";
            break;
            case 3: nid = "447";
            break;
            case 4: nid = "50";
            break;
            case 5: nid = "51";
            break;
            case 6: nid = "10";
            break;
            case 7: nid = "8";
            break;
            case 8: nid = "103";
            break;
            case 9: nid = "112";
            break;
            case 10: nid = "114";
            break;
            case 11: nid = "20";
            break;
            case 12: nid = "21";
            break;
            case 13: nid = "119";
            break;
            case 14: nid = "124";
            break;
            case 15: nid = "28";
            break;
            case 16: nid = "547";
            break;
            case 17: nid = "128";
            break;
            case 18: nid = "412";
            break;
            case 19: nid = "175";
            break;
            case 20: nid = "185";
            break;
            default: System.out.println("Enter custom number:");
            scan.nextLine();
            nid = scan.nextLine();
            break;
        }
        // select platform
        final String[] possiblePlatforms = {"iOS", "Android"};
        platform = possiblePlatforms[rand.nextInt(possiblePlatforms.length)];
        // generate uuid
        uuid = generateUUID();
        client = new OkHttpClient();
        logIn();
        getFeed();
        eventPrinter();
        try
        {
            while (true)
            {
                while (!eventIDs.isEmpty())
                {
                    long millis = 1;
                    while (millis > 0)
                    {
                        millis = Duration.between(LocalDateTime.now(), startTimes.get(0)).toMillis();
                        String timeUntil = String.format("%d days, %d hours, %d mins",
                                TimeUnit.MILLISECONDS.toDays(millis),
                                TimeUnit.MILLISECONDS.toHours(millis) -
                                        TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millis)),
                                TimeUnit.MILLISECONDS.toMinutes(millis) -
                                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)));
                        System.out.println(timeUntil + " until " + eventDescriptions.get(0));
                        Thread.sleep(60000);
                    }
                    checkIn(eventIDs.get(0));
                    eventIDs.remove(0);
                    eventDescriptions.remove(0);
                    pointValues.remove(0);
                    startTimes.remove(0);
                    endTimes.remove(0);
                    generatedLatitidues.remove(0);
                    generatedLongitudes.remove(0);
                }
                getFeed();
            }

        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    private static String generateUUID()
    {

        String validChars = "0123456789abcdefghijklmnopqrstuvxyz";
        StringBuilder uuidBuilder = new StringBuilder(16);

        for (int i = 0; i < 16; i++)
        {
            uuidBuilder.append(validChars.charAt(rand.nextInt(validChars.length())));
        }
        return uuidBuilder.toString();
    }
    private static void logIn() throws IOException
    {
        System.out.println("Logging in with username " + user + " at school ID " + nid + "...");
        RequestBody loginToU = new FormBody.Builder()
                .add("user", user)
                .add("pass", pass)
                .add("nid", nid)
                .add("platform", platform)
                .add("uuid", uuid)
                .build();
        Request login = new Request.Builder()
                .url("https://api.superfanu.com/7.0.1/login")
                .addHeader("nid", nid)
                .addHeader("platform", platform)
                .addHeader("uuid", uuid)
                .addHeader("login_key", loginKey)
                .post(loginToU)
                .build();
        Response response = client.newCall(login).execute();
        if(response.isSuccessful())
        {
            System.out.println("Login Successful!");
        }
        JSONObject loginResponse = new JSONObject(response.body().string());
        JSONArray data = loginResponse.getJSONArray("data");
        for(int i = 0; i < data.length(); i++)
        {
            loginKey = data.getJSONObject(i).getString("login_key");
        }
    }
    private static void getFeed() throws IOException
    {
        System.out.println("Getting feed...");
        Request feed = new Request.Builder()
                .url("https://api.superfanu.com/7.0.1/feed")
                .addHeader("nid", nid)
                .addHeader("platform", platform)
                .addHeader("uuid", uuid)
                .addHeader("login_key", loginKey)
                .get()
                .build();
        Response feedResponse = client.newCall(feed).execute();
        if(feedResponse.isSuccessful())
        {
            System.out.println("Feed successfully fetched!\n");
        }
        JSONObject jsonFeed = new JSONObject(feedResponse.body().string());
        JSONArray data2 = jsonFeed.getJSONArray("data").getJSONArray(0);
        List<JSONObject> events = new ArrayList<>();
        for(int i = 0; i < data2.length(); i++)
        {
            JSONObject current = data2.getJSONObject(i);
            if(current.getString("type").equals("event"))
            {
                events.add(current);
            }
        }
        if(events.isEmpty())
        {
            System.out.println("No events currently. Check again later!");
            System.exit(0);
        }
        for(JSONObject e: events)
        {
            eventIDs.add(e.getString("eid"));
            eventDescriptions.add(e.getString("description"));
            pointValues.add(e.getString("pointvalue"));
            startTimes.add(LocalDateTime.parse(e.getString("starttime"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).plusHours(1));
            endTimes.add(LocalDateTime.parse(e.getString("endtime"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).plusHours(1));;
        }
    }
    private static void eventPrinter() throws IOException
    {
        for(int i = 0; i < eventIDs.size(); i++)
        {
            System.out.println("EVENT ID = " + eventIDs.get(i));
            System.out.println("EVENT DESCRIPTION = " + eventDescriptions.get(i));
            System.out.println("POINT VALUE = " + pointValues.get(i));
            System.out.println("START TIME = " + startTimes.get(i));
            System.out.println("END TIME = " + endTimes.get(i));
            Request currentEvent = new Request.Builder()
                    .url("https://api.superfanu.com/7.0.1/event/" + eventIDs.get(i) + "/details")
                    .addHeader("nid", nid)
                    .addHeader("platform", platform)
                    .addHeader("uuid", uuid)
                    .addHeader("login_key", loginKey)
                    .get()
                    .build();
            Response eventResponse = client.newCall(currentEvent).execute();
            JSONObject eventJSON = new JSONObject(eventResponse.body().string());
            JSONObject data3 = eventJSON.getJSONArray("data").getJSONObject(0).getJSONObject("event").getJSONArray("venues").getJSONObject(0);
            String lattude = data3.getString("latitude");
            String lontude = data3.getString("longitude");
            System.out.println("LATITUDE = " + lattude);
            System.out.println("LONGITUDE = " + lontude);
            double randCloseLattude = ((Math.round(Double.parseDouble(lattude) * 1000) * 100) + rand.nextInt(100)) / 100000.0;
            double randCloseLontude = ((Math.round(Double.parseDouble(lontude) * 1000) * 100) + rand.nextInt(100)) / 100000.0;
            System.out.println("RANDOM CLOSE LATITUDE = " + randCloseLattude);
            System.out.println("RANDOM CLOSE LONGITUDE = " + randCloseLontude);
            generatedLatitidues.add(randCloseLattude);
            generatedLongitudes.add(randCloseLontude);
            System.out.println("EXACT LOCATION PREVIEW = " + "https://www.google.com/maps/search/?api=1&query=" + lattude + "," + lontude);
            System.out.println("RANDOM CLOSE LOCATION PREVIEW = " + "https://www.google.com/maps/search/?api=1&query=" + randCloseLattude + "," + randCloseLontude);
            LocalDateTime startIn = startTimes.get(i);
            long millis = Duration.between(LocalDateTime.now(), startIn).toMillis();
            String timeUntil = String.format("%d days, %d hours, %d mins",
                    TimeUnit.MILLISECONDS.toDays(millis),
                    TimeUnit.MILLISECONDS.toHours(millis) -
                            TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millis)),
                    TimeUnit.MILLISECONDS.toMinutes(millis) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)));
            System.out.println("Event starts in " + timeUntil + ".\n");
        }
    }
    private static void checkIn(String eventID) throws IOException
    {
        RequestBody checkInToU = new FormBody.Builder()
                .add("eid", eventID)
                .add("latitude", generatedLatitidues.get(0).toString())
                .add("longitude", generatedLongitudes.get(0).toString())
                .add("altitude", Integer.toString(rand.nextInt(100)))
                .add("horizontal_accuracy", Integer.toString(rand.nextInt(20)))
                .add("vertical_accuracy", Integer.toString(rand.nextInt(20)))
                .build();
        Request theCheck = new Request.Builder()
                .url("https://api.superfanu.com/7.0.1/event/check-in")
                .addHeader("nid", nid)
                .addHeader("platform", platform)
                .addHeader("uuid", uuid)
                .addHeader("login_key", loginKey)
                .post(checkInToU)
                .build();
        Response response = client.newCall(theCheck).execute();
        System.out.println(response.body().string());
    }
}
