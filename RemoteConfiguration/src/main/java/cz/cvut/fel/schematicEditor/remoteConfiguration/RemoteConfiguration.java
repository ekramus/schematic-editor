package cz.cvut.fel.schematicEditor.remoteConfiguration;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;

/**
 *
 * @author Urban Kravjansky
 */
public class RemoteConfiguration {
    public static void main(String[] args) {
        String url = "http://asinus.feld.cvut.cz/pracan3/data/part-spice/soucastky/";
        RemoteConfiguration rc = new RemoteConfiguration(url);
    }

    /**
     * Constructor. It creates {@link RemoteConfiguration} object and tries to read all accessible remote html files.
     *
     */
    public RemoteConfiguration(String url) {
        HashMap<Character, Properties> externalProperties = new HashMap<Character, Properties>();

        for (char c = 'a'; c <= 'z'; c++) {
            System.out.println("\n" + c + ":");
            Properties p = new Properties();
            try {
                URL u = new URL(url + c + ".ini");
                p.load(u.openStream());
                externalProperties.put(c, p);
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                System.err.println("File not found");
            }
        }

        for (Character c : externalProperties.keySet()) {
            System.out.println(c + ": " + externalProperties.get(c).size());
        }
    }
}
