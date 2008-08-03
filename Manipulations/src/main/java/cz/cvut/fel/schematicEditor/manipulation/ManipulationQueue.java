package cz.cvut.fel.schematicEditor.manipulation;

import java.util.LinkedList;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.manipulation.exception.ManipulationExecutionException;

/**
 * This class encapsulates manipulations, which are to be executed or already were executed.
 * 
 * @author Urban Kravjansky
 */
public class ManipulationQueue {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger            logger;
    /**
     * Queue of {@link Manipulation}s.
     */
    private LinkedList<Manipulation> manipulationQueue;
    /**
     * Index of active {@link Manipulation} stored in {@link ManipulationQueue}.
     */
    private int                      activeManipulationIndex;

    /**
     * Default constructor. It is for initialization purposes.
     */
    public ManipulationQueue() {
        logger = Logger.getLogger(this.getClass().getName());

        setManipulationQueue(new LinkedList<Manipulation>());
        setActiveManipulationIndex(0);
    }

    /**
     * Executes given active manipulation.
     * 
     * @param activeManipulation {@link Manipulation} to be executed.
     * 
     * @return status of executed {@link Manipulation}. <code>false</code> in case of execution problems, else
     *         <code>true</code>.
     */
    public boolean execute(Manipulation activeManipulation) {
        // try to add and execute manipulation
        try {
            // move index of active manipulation to the next one
            setActiveManipulationIndex(getActiveManipulationIndex() + 1);

            // there are manipulations, which are to be abandoned
            if (getManipulationQueue().size() > getActiveManipulationIndex()) {
                setManipulationQueue(new LinkedList<Manipulation>(getManipulationQueue()
                        .subList(0, getActiveManipulationIndex() - 1)));
            }

            // add manipulation at the end of manipulation queue
            getManipulationQueue().add(activeManipulation);

            // execute manipulation
            activeManipulation.execute();

            logger.trace("Manipulation executed: " + this);
        }
        // manipulation was null
        catch (NullPointerException e) {
            return false;
        }
        // exception during execution
        catch (ManipulationExecutionException e) {
            return false;
        }

        return true;
    }

    /**
     * Reexecutes (redoes) manipulation on active manipulation index position.
     * 
     * @return status of reexecuted {@link Manipulation}. <code>false</code> in case of reexecution problems, else
     *         <code>true</code>.
     */
    public boolean reexecute() {
        // move index of active manipulation to the next one
        setActiveManipulationIndex(getActiveManipulationIndex() + 1);

        // if there is no manipulation to execute, add last one
        if (getManipulationQueue().size() < getActiveManipulationIndex()) {
            getManipulationQueue().add(getManipulationQueue().getLast());
        }

        // retrieve manipulation
        Manipulation manipulation = getManipulationQueue().get(getActiveManipulationIndex());

        // try to reexecute manipulation
        try {
            manipulation.execute();
        }
        // manipulation was null
        catch (NullPointerException npe) {
            return false;
        }
        // exception during execution
        catch (ManipulationExecutionException e) {
            return false;
        }

        return true;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        String buf = "";

        for (int i = 0; i < getManipulationQueue().size(); i++) {
            if (i == getActiveManipulationIndex()) {
                buf += "<" + getManipulationQueue().get(i) + "> ";
            } else {
                buf += "[" + getManipulationQueue().get(i) + "] ";
            }
        }

        return buf;
    }

    /**
     * Unexecutes (undoes) manipualtion on position of active manipulation index.
     * 
     * @return status of unexecuted {@link Manipulation}. <code>false</code> in case of unexecution problems, else
     *         <code>true</code>.
     */
    public boolean unexecute() {
        Manipulation manipulation = getManipulationQueue().get(getActiveManipulationIndex());

        // try to unexecute manipulation
        try {
            manipulation.unexecute();

            // move index of active manipulation to the previous one
            setActiveManipulationIndex(getActiveManipulationIndex() - 1);
        }
        // manipulation was null
        catch (NullPointerException e) {
            return false;
        }
        // exception occurred during unexecution
        catch (ManipulationExecutionException e) {
            return false;
        }

        return true;
    }

    /**
     * Getter for <code>activeManipulationIndex</code> field.
     * 
     * @return The index of active {@link Manipulation}.
     */
    private int getActiveManipulationIndex() {
        return this.activeManipulationIndex;
    }

    /**
     * Getter for <code>manipulationQueue</code>.
     * 
     * @return the <code>manipulationQueue</code> instance.
     */
    private LinkedList<Manipulation> getManipulationQueue() {
        return this.manipulationQueue;
    }

    /**
     * Setter for <code>activeManipulationIndex</code> field.
     * 
     * @param activeManipulationIndex Index of active {@link Manipulation}.
     */
    private void setActiveManipulationIndex(int activeManipulationIndex) {
        this.activeManipulationIndex = activeManipulationIndex;
    }

    /**
     * @param manipulationQueue the <code>manipulationQueue</code> to set
     */
    private void setManipulationQueue(LinkedList<Manipulation> manipulationQueue) {
        this.manipulationQueue = manipulationQueue;
    }
}
