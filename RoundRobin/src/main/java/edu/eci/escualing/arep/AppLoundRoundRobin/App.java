package edu.eci.escualing.arep.AppLoundRoundRobin;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import static spark.Spark.*;
// TODO: Auto-generated Javadoc

/**
 * Class App!.
 *
 * @Author JuanRomero11
 */
public class App {
    
    /** The balanciador. */
    private static int balanciador = 3;

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        port(getPort());
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return render(model, "/index");
        });

        post("/", (req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String dato = req.body().substring(7);
            BufferedReader in = null;
            if (balanciador <= 1) {
                balanciador = 3;
            } else if (balanciador > 1) {
                balanciador--;
            }
            URL logService =  revisarUrl(dato,balanciador);
            URLConnection conexion = logService.openConnection();
            in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            BufferedReader bufferIn = new BufferedReader(new InputStreamReader(System.in));
            in.close();
            bufferIn.close();
            res.redirect("/vistaDatos");
            return new ModelAndView(model, "/index");
        }, new ThymeleafTemplateEngine());

        get("/vistaDatos", (req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            BufferedReader in = null;
            if (balanciador <= 1) {
                balanciador = 3;
            } else if (balanciador > 1) {
                balanciador--;
            }
            URL logService = revisarUrl(null,balanciador);
            URLConnection conexion = logService.openConnection();
            in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            BufferedReader bufferIn = new BufferedReader(new InputStreamReader(System.in));
            String line = in.readLine();
            in.close();
            bufferIn.close();
            line = line.replace("\\\"", "");
            model.put("datos", line);
            return render(model, "/datos");
        });
    }

    /**
     * Revisar url.
     *
     * @param dato the dato
     * @param balanciador the balanciador
     * @return the url
     * @throws MalformedURLException the malformed URL exception
     */
    public static URL revisarUrl(String dato, int balanciador) throws MalformedURLException {
        URL responseUrl = null;
        if (dato.equals(null)) {

            if (balanciador == 1) {
                System.out.println("entre en 1");
                responseUrl = new URL("http://localhost:35001/");
            } else if (balanciador == 2) {
                System.out.println("entre en 2");
                responseUrl = new URL("http://localhost:35002/");
            } else if (balanciador == 3) {
                System.out.println("entre en 3");
                responseUrl = new URL("http://localhost:34003/");
            }

        } else {
            if (balanciador == 1) {
                System.out.println("entre en 1 value?dato "+dato);
                responseUrl = new URL("http://localhost:35001/newDato?newDato=" + dato);
            } else if (balanciador == 2) {
                System.out.println("entre en 2 value!dato "+dato);
                responseUrl = new URL("http://localhost:35002/newDato?newDato=" + dato);
            } else if (balanciador == 3) {
                System.out.println("entre en 3 value?dato "+dato);
                responseUrl = new URL("http://localhost:35003/newDato?danewDato=" + dato);
            }
        }
        return responseUrl;

    }

    /**
     * Render.
     *
     * @param model the model
     * @param templatePath the template path
     * @return the string
     */
    public static String render(Map<String, Object> model, String templatePath) {
        return new ThymeleafTemplateEngine().render(new ModelAndView(model, templatePath));
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
