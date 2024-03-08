package application.Universities;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;


public class Mango {
    public static void main(String[] args) {
        // MongoDB Connection URI
        String connectionString = "mongodb+srv://<username>:<password>@<cluster>/<database>";
        connectionString = "mongodb+srv://njeb1:Njabulo0.@apply.mongocluster.cosmos.azure.com/?tls=true&authMechanism=SCRAM-SHA-256&retrywrites=false&maxIdleTimeMS=120000";

        // Create MongoDB Client
        MongoClient mongoClient = MongoClients.create(connectionString);

        // Get Database
        MongoDatabase database = mongoClient.getDatabase("<database>");

        // Print Database Name
        System.out.println("Connected to database: " + database.getName());
        
        // Close the connection when done
        mongoClient.close();
    }
}
