package cz.cvut.fel.schematicEditor.application;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import org.apache.log4j.Logger;

/**
 * This class represents FileFilter of POSTSCRIPT and SVG file formats
 * 
 * @author Zdenek Straka
 * @author Urban Kravjansky
 */
public class ExportFileFilter extends FileFilter {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger      logger;
    public final static String POSTDESC   = "Encaptulated Postscript (*.eps)";
    public final static String POSTSCRIPT = "eps";
    public final static String SVG        = "svg";
    public final static String SVGDESC    = "SVG files (*.svg)";
    /**
     * SEF file format description.
     */
    public static final String SEFDESC    = "Schematic Editor Format (*.sef)";
    /**
     * SEF file format extension.
     */
    public static final String SEF        = "sef";

    public static String getExtension(File f) {

        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }

        return ext;
    }

    private String description;

    private String extension;

    public ExportFileFilter(String extension, String description) {
        logger = Logger.getLogger(this.getClass().getName());

        this.extension = extension;
        this.description = description;
    }

    @Override
    public boolean accept(File arg0) {
        String ext = ExportFileFilter.getExtension(arg0);

        logger.trace("file: " + arg0 + ", ext: " + ext);

        try {
            if (arg0.isDirectory() || ext.equals(extension)) {
                return true;
            }
        }
        // file does not have extension
        catch (NullPointerException npe) {
            return false;
        }
        return false;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
