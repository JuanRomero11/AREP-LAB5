package edu.eci.escualing.arep.LogServicesApp;
import edu.eci.escualing.arep.LogServicesApp.servicesImpl.*;
import com.google.gson.Gson;
import java.util.Date;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import static spark.Spark.*;


/**
 * Class App!.
 *
 * @Author JuanRomero11
 */
public class App {
   
    /** The datosservice. */
    public static ServicesDataMongo datosService =null;

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        port(getPort());
        
        get("/hello", (req,res) -> "Hello Docker!");

        Gson gson = new Gson();
        get("/newDato", (req, res) -> {
            res.type("application/json");
            System.out.println(req.queryParams("newDato"));
            // DataMongo nuevo= new DataMongo(req.queryParams("newDato"),new Date());
            System.out.print("----------------sali de DataMongo :)---------------");
            datosService=new ServicesDataMongo();
            System.out.print("creer nuevo= ");
            return datosService.addDato(req.queryParams("newDato").toString(),(new Date()).toString());
        }, gson ::toJson);

        get("/",(req, res) -> {
            res.type("application/json");
            return datosService.getAllDatos();
        }, gson ::toJson);
    }

    /**
     * Gets the port.
     *
     * @return the port
     */
    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }
}
