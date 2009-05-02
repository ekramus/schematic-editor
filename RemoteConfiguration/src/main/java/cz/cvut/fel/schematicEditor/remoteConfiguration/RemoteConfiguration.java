package cz.cvut.fel.schematicEditor.remoteConfiguration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

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
        for (char c = 'a'; c <= 'z'; c++) {
            System.out.println("\n" + c + ":");
            try {
                URL u = new URL(url + c + ".html");
                BufferedReader br = new BufferedReader(new InputStreamReader(u.openStream()));
                String buf = br.readLine();
                while (buf != null) {
                    System.out.println(buf);
                    buf = br.readLine();
                }
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
