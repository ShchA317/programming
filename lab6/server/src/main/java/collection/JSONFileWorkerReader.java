package collection;

import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import worker.OrdinaryWorker;
import worker.Worker;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.LinkedList;

/**
 * Class to parse workers from json file
 */
public class JSONFileWorkerReader implements WorkerReader {
    private String dataFileName;

    public JSONFileWorkerReader(String dataFileName){
        this.dataFileName = dataFileName;
    }

    @Override
    public LinkedList<Worker> getWorkers() {
        String jsonString;
        try {
            jsonString = getJSONString();
        } catch (NullPointerException npe){
            jsonString = "";
        }
        jsonString = setNewIds(jsonString);
        final Gson gson = Converters.registerAll(new GsonBuilder()).create();
        LinkedList<Worker> result;
        Type type = new TypeToken<LinkedList<OrdinaryWorker>>(){}.getType();
        result = gson.fromJson(jsonString, type);
        return result;
    }

    private String getJSONString(){
        String jsonString = null;
        try{
            FileReader fileReader = new FileReader(dataFileName);
            StringBuilder sb = new StringBuilder();
            int code = -1;
            while ((code = fileReader.read()) != -1) {
                sb.append(Character.toChars(code));
            }
            fileReader.close();
            jsonString = new String(sb);
        }catch (Exception e){
            System.err.println("We have some problems with file.");
            e.printStackTrace();
            if(e.getMessage() != null){
                System.err.println(e.getMessage());
            }
        }
        if(jsonString != null) {
            return jsonString;
        } else{
            throw new NullPointerException();
        }
    }

    private String setNewIds(String jsonString){
        String result;
        result = jsonString.replaceAll("\"id\": \\d+", "\"id\": number");
        int count = 1;
        while(result.contains("number")){
            result = result.replaceFirst("number", Integer.toString(count));
            OrdinaryWorker.ids.add((long) count);
            count++;
        }
        return result;
    }
}
