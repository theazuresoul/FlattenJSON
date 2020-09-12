import org.json.*;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class FlattenJSON {
    public static JSONObject flattenJSON(JSONObject input) {

        JSONObject output = new JSONObject();

        Iterator<String> keys = input.keys();

        while (keys.hasNext()) {
            String key = keys.next();
            if (input.get(key) instanceof JSONObject) {
                JSONObject subObject = flattenJSON(input.getJSONObject(key));
                Iterator<String> subKeys = subObject.keys();
                while (subKeys.hasNext()) {
                    String subKey = subKeys.next();
                    output.put(key + "." + subKey, subObject.get(subKey));
                }

            } else {
                output.put(key, input.get(key));
            }
        }
        return output;
    }

    public static void main(String[] args) {

        String fileName = args[0];
        String jsonStr;
        try {
            jsonStr = new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (Exception e) {
            System.out.println("Failed to read file: " + e.getMessage());
            return;
        }

        JSONObject output;
        try {
            output = new JSONObject(jsonStr);
        } catch (JSONException e) {
            System.out.println("Not a valid JSON file: " + e.getMessage());
            return;
        }

        output = flattenJSON(new JSONObject(jsonStr));
        System.out.println(output);
    }
}
