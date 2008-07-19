package cz.cvut.fel.schematicEditor.manipulation;

import java.util.LinkedList;

import cz.cvut.fel.schematicEditor.manipulation.exception.ManipulationExecutionException;
import cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation;
import cz.cvut.fel.schematicEditor.manipulation.manipulation.ManipulationFactory;

/**
 * This class encapsulates manipulations, which are to be executed or already were executed.
 * 
 * @author Urban Kravjansky
 */
public class ManipulationQueue {
    /**
     * Queue of manipulations waiting for processing.
     */
    private LinkedList<Manipulation> waitingManipulations;
    /**
     * Queue of processed manipulations.
     */
    private LinkedList<Manipulation> processedManipulations;

    /**
     * Default constructor. It is for initialization purposes.
     */
    public ManipulationQueue() {
        setWaitingManipulations(new LinkedList<Manipulation>());
        setProcessedManipulations(new LinkedList<Manipulation>());
    }

    /**
     * Executes top manipulation stored in <code>waitingManipulations</code> queue and moves it into
     * <code>processedManipulations</code> queue.
     * 
     * @return status of executed {@link Manipulation}. <code>false</code> in case of execution problems, else
     *         <code>true</code>.
     */
    public boolean execute() {
        Manipulation manipulation = getWaitingManipulations().poll();

        // try to execute manipulation
        try {
            manipulation.execute();
        } catch (NullPointerException e) {
            // manipulation was null
            return false;
        } catch (ManipulationExecutionException e) {
            // TODO: handle exception
        }

        // add manipulation to top of processed manipulations
        getProcessedManipulations().offer(manipulation);
        return true;
    }

    /**
     * Unexecutes (undoes) top manipulation stored in <code>processedManipulations</code> queue and moves it on top of
     * <code>waitingManipulations</code> queue.
     * 
     * @return status of unexecuted {@link Manipulation}. <code>false</code> in case of unexecution problems, else
     *         <code>true</code>.
     */
    public boolean unexecute() {
        Manipulation manipulation = getProcessedManipulations().poll();

        // try to unexecute manipulation
        try {
            manipulation.unexecute();
        } catch (NullPointerException e) {
            // manipulation was null
            return false;
        } catch (ManipulationExecutionException e) {
            // TODO: handle exception
        }

        // add manipulation to top of waiting manipulations
        getWaitingManipulations().offer(manipulation);
        return true;
    }

    /**
     * Reexecutes (redoes) top manipulation stored in <code>processedManipulations</code> queue. No transfers between
     * queues are done.
     * 
     * @return status of reexecuted {@link Manipulation}. <code>false</code> in case of reexecution problems, else
     *         <code>true</code>.
     */
    public boolean reexecute() {
        Manipulation manipulation = getProcessedManipulations().peek();

        // try to reexecute manipulation
        try {
            manipulation.execute();
        } catch (NullPointerException npe) {
            // manipulation was null
            return false;
        } catch (ManipulationExecutionException e) {
            // TODO: handle exception
        }

        // duplicate manipulation and add it on top of processed ones
        getProcessedManipulations().offer(ManipulationFactory.duplicate(manipulation));
        return true;
    }

    /**
     * @return the waitingManipulations
     */
    private LinkedList<Manipulation> getWaitingManipulations() {
        return this.waitingManipulations;
    }

    /**
     * @param waitingManipulations
     *            the waitingManipulations to set
     */
    private void setWaitingManipulations(LinkedList<Manipulation> waitingManipulations) {
        this.waitingManipulations = waitingManipulations;
    }

    /**
     * @return the processedManipulations
     */
    private LinkedList<Manipulation> getProcessedManipulations() {
        return this.processedManipulations;
    }

    /**
     * @param processedManipulations
     *            the processedManipulations to set
     */
    private void setProcessedManipulations(LinkedList<Manipulation> processedManipulations) {
        this.processedManipulations = processedManipulations;
    }

    /**
     * Adds {@link Manipulation} at the end of {@link ManipulationQueue}.
     * 
     * @param manipulation
     */
    public void offerManipulation(Manipulation manipulation) {
        getWaitingManipulations().offer(manipulation);
    }

    /**
     * Replaces last {@link Manipulation} with given.
     * 
     * @param manipulation
     */
    public void replaceLastManipulation(Manipulation manipulation) {
        getWaitingManipulations().poll();
        getWaitingManipulations().offer(manipulation);
    }
}
