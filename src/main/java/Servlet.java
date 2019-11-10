import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import resource.Log;

import java.io.IOException;
import java.util.*;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class Servlet extends javax.servlet.http.HttpServlet {
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        MongoClientURI uri = new MongoClientURI(
                "mongodb+srv://jiefei:Mongodb@nvirginia-dlfpp.mongodb.net/test?retryWrites=true&w=majority");

        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("ds");

        // get new collection
        MongoCollection<Log> collection = database.getCollection("task2", Log.class);

        // set up codecregistry
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        collection = collection.withCodecRegistry(pojoCodecRegistry);
        System.out.println("setting up codec ready.");

        // get logs
        List<Log> logs = new LinkedList<>();
        for (Log log : collection.find()) {
            logs.add(log);
        }
        request.setAttribute("logs", logs);
        System.out.println("setting up log table");

        // get top analytics insights
        List<String> language_code_values = new ArrayList<>();
        for(Log log:logs) language_code_values.add(log.language_code);

        List<String> date_values = new ArrayList<>();
        for(Log log:logs) date_values.add(log.date);

        List<String> intent_values = new ArrayList<>();
        for(Log log:logs) intent_values.add(log.intent);

        request.setAttribute("top_language_code", sort(language_code_values));
        request.setAttribute("top_date", sort(date_values));
        request.setAttribute("top_intent", sort(intent_values));
        System.out.println("setting up analytics statistics");

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    private String sort (List<String> values) {
        TreeMap<Integer, String> occurrence = new TreeMap<>(Collections.reverseOrder());
        Set<String> uniqueValues = new HashSet<>(values);
        for(String value: uniqueValues){
            occurrence.put(Collections.frequency(values, value), value);
        }

        StringBuilder result = new StringBuilder();
        int cnt = 0;
        for (Map.Entry entry: occurrence.entrySet()) {
            result.append(entry.getValue()).append("(").append(entry.getKey()).append("); ");
            cnt ++;
            if (cnt==3) {
                return result.toString();
            }
        }
        return result.toString();
    }
}
