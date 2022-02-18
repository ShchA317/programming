package main;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.worker.OrdinaryWorker;
import main.worker.Worker;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.LinkedList;

/**
 * Class to parse workers from json file
 */
public class JSONFileWorkerReader implements WorkerReader{
    private final String dataFileName;

    public JSONFileWorkerReader(String dataFileName){
        this.dataFileName = dataFileName;
    }

    @Override
    public LinkedList<Worker> getWorkers() {
        String jsonString = getJSONString();
        jsonString = setNewIds(jsonString);
        Gson gson = new Gson();
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
            if(e.getMessage() != null){
                System.out.println(e.getMessage());
            }
        }
        return jsonString;
    }

    private String setNewIds(String jsonString){
        String result;
        result = jsonString.replaceAll("\"id\": \\d++", "\"id\": number");
        int count = 1;
        while(result.contains("number")){
            result = result.replaceFirst("number", Integer.toString(count));
            count++;
        }
        return result;
    }
}
