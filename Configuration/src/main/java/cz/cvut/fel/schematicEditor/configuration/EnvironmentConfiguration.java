package cz.cvut.fel.schematicEditor.configuration;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This class encapsulates environment properties.
 * 
 * @author Urban Kravjansky
 */
@XStreamAlias("guiConfiguration")
public class EnvironmentConfiguration extends Configuration {
    /**
     * File name, where should be this class serialized.
     */
    private static final String             FILE     = "config/env.xml";
    /**
     * {@link EnvironmentConfiguration} singleton instance.
     */
    private static EnvironmentConfiguration instance = null;

    /**
     * @return the FILE
     */
    public static String getFile() {
        return EnvironmentConfiguration.FILE;
    }

    /**
     * @return the instance
     */
    public static EnvironmentConfiguration getInstance() {
        if (instance == null) {
            instance = (EnvironmentConfiguration) Configuration.deserialize(EnvironmentConfiguration.class, FILE);
            if (instance == null) {
                instance = new EnvironmentConfiguration();
            }
        }
        return instance;
    }

    /**
     * Application last used export folder.
     */
    private String lastExportFolder = null;
    /**
     * Application last used import folder.
     */
    private String lastImportFolder = null;
    /**
     * Application last used load folder.
     */
    private String lastOpenFolder   = null;
    /**
     * Application last used save folder.
     */
    private String lastSaveFolder   = "parts";

    /**
     * @return the partsFolder
     */
    public String getPartsFolder() {
        return this.partsFolder;
    }

    /**
     * @param partsFolder the partsFolder to set
     */
    public void setPartsFolder(final String partsFolder) {
        this.partsFolder = partsFolder;
    }

    /**
     * Parts folder.
     */
    private String partsFolder      = null;

    /**
     * 
     */
    public EnvironmentConfiguration() {
        // nothing to do
    }

    /**
     * @return the lastExportFolder
     */
    public String getLastExportFolder() {
        return this.lastExportFolder;
    }

    /**
     * @return the lastImportFolder
     */
    public String getLastImportFolder() {
        return this.lastImportFolder;
    }

    /**
     * @return the lastLoadFolder
     */
    public String getLastOpenFolder() {
        return this.lastOpenFolder;
    }

    /**
     * @return the lastSaveFolder
     */
    public String getLastSaveFolder() {
        return this.lastSaveFolder;
    }

    /**
     * @param lastExportFolder the lastExportFolder to set
     */
    public void setLastExportFolder(String lastExportFolder) {
        this.lastExportFolder = lastExportFolder;
    }

    /**
     * @param lastImportFolder the lastImportFolder to set
     */
    public void setLastImportFolder(String lastImportFolder) {
        this.lastImportFolder = lastImportFolder;
    }

    /**
     * @param lastOpenFolder the lastLoadFolder to set
     */
    public void setLastOpenFolder(String lastOpenFolder) {
        this.lastOpenFolder = lastOpenFolder;
    }

    /**
     * @param lastSaveFolder the lastSaveFolder to set
     */
    public void setLastSaveFolder(String lastSaveFolder) {
        this.lastSaveFolder = lastSaveFolder;
    }
}
