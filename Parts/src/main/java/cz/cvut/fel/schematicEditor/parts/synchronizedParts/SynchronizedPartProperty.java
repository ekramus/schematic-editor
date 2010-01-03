package cz.cvut.fel.schematicEditor.parts.synchronizedParts;

import java.util.HashMap;

import org.apache.log4j.Logger;

/**
 * @author Urban Kravjansky
 *
 */
public class SynchronizedPartProperty {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger logger;
    /**
     * The definition.
     */
    private String        definition;
    /**
     * The name cs.
     */
    private String        nameCS;
    /**
     * The name en.
     */
    private String        nameEN;
    /**
     * The type.
     */
    private String        type;
    /**
     * The help cs.
     */
    private String        helpCS;
    /**
     * The help en.
     */
    private String        helpEN;
    /**
     * The validate.
     */
    private String        validate;
    /**
     * The use.
     */
    private String        use;
    /**
     * The show.
     */
    private String        show;

    /**
     * This method instantiates new instance.
     *
     * @param sppMap {@link HashMap} used for initialization of this instance.
     *
     */
    public SynchronizedPartProperty(HashMap<String, String> sppMap) {
        logger = Logger.getLogger(getClass());

        for (String key : sppMap.keySet()) {
            if (key.matches(PartPropertyEnum.DEFINITION.getValue())) {
                setDefinition(sppMap.get(key));
            } else if (key.matches(PartPropertyEnum.HELP_CS.getValue())) {
                setHelpCS(sppMap.get(key));
            } else if (key.matches(PartPropertyEnum.HELP_EN.getValue())) {
                setHelpEN(sppMap.get(key));
            } else if (key.matches(PartPropertyEnum.TYPE.getValue())) {
                setType(sppMap.get(key));
            } else if (key.matches(PartPropertyEnum.NAME_CS.getValue())) {
                setNameCS(sppMap.get(key));
            } else if (key.matches(PartPropertyEnum.NAME_EN.getValue())) {
                setNameEN(sppMap.get(key));
            } else if (key.matches(PartPropertyEnum.VALIDATE.getValue())) {
                setValidate(sppMap.get(key));
            } else if (key.matches(PartPropertyEnum.USE.getValue())) {
                setUse(sppMap.get(key));
            } else if (key.matches(PartPropertyEnum.SHOW.getValue())) {
                setShow(sppMap.get(key));
            } else {
                logger.error("Unknow property field");
            }
        }
    }

    /**
     * @return the show
     */
    public String getShow() {
        return this.show;
    }

    /**
     * @param show the show to set
     */
    private void setShow(String show) {
        this.show = show;
    }

    /**
     * @return the definition
     */
    public String getDefinition() {
        return this.definition;
    }

    /**
     * @param definition the definition to set
     */
    private void setDefinition(String definition) {
        this.definition = definition;
    }

    /**
     * @return the nameCS
     */
    public String getNameCS() {
        return this.nameCS;
    }

    /**
     * @param nameCS the nameCS to set
     */
    private void setNameCS(String nameCS) {
        this.nameCS = nameCS;
    }

    /**
     * @return the nameEN
     */
    public String getNameEN() {
        return this.nameEN;
    }

    /**
     * @param nameEN the nameEN to set
     */
    private void setNameEN(String nameEN) {
        this.nameEN = nameEN;
    }

    /**
     * @return the type
     */
    public String getType() {
        return this.type;
    }

    /**
     * @param type the type to set
     */
    private void setType(String type) {
        this.type = type;
    }

    /**
     * @return the helpCS
     */
    public String getHelpCS() {
        return this.helpCS;
    }

    /**
     * @param helpCS the helpCS to set
     */
    private void setHelpCS(String helpCS) {
        this.helpCS = helpCS;
    }

    /**
     * @return the helpEN
     */
    public String getHelpEN() {
        return this.helpEN;
    }

    /**
     * @param helpEN the helpEN to set
     */
    private void setHelpEN(String helpEN) {
        this.helpEN = helpEN;
    }

    /**
     * @return the validate
     */
    public String getValidate() {
        return this.validate;
    }

    /**
     * @param validate the validate to set
     */
    private void setValidate(String validate) {
        this.validate = validate;
    }

    /**
     * @return the use
     */
    public String getUse() {
        return this.use;
    }

    /**
     * @param use the use to set
     */
    private void setUse(String use) {
        this.use = use;
    }

}
