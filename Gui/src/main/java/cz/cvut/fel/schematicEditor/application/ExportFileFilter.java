/**
 * 
 */
package cz.cvut.fel.schematicEditor.application;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * This class represents FileFilter of POSTSCRIPT and SVG file formats
 * 
 * @author Zdenek Straka
 * @author Urban Kravjansky
 */
public class ExportFileFilter extends FileFilter {
    public final static String POSTDESC   = "Encaptulated Postscript(*.eps)";
    public final static String POSTSCRIPT = "eps";
    public final static String SVG        = "svg";
    public final static String SVGDESC    = "SVG files (*.svg)";

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
        this.extension = extension;
        this.description = description;
    }

    @Override
    public boolean accept(File arg0) {
        String ext = ExportFileFilter.getExtension(arg0);

        if (arg0.isDirectory() || ext.equals(extension)) {
            return true;
        }

        return false;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
