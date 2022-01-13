package edu.kit.informatik.exception;

/**
 * A semantics fault will be indicate with this exception.
 *
 * @author NICHT vergessen
 * @version 1.0
 */
public class SemanticsException extends Exception {

    /**
     * Auto created UID for serializing. Actually not needed according to the task, but absence produces to annoy
     * warnings.
     */
    private static final long serialVersionUID = 2902883905608249312L;

    /**
     * Generates a new SemanticsException with the given detailed message.
     *
     * @param pMessage some detailed error message (null is not allowed)
     */
    public SemanticsException(final String pMessage) {
        super(pMessage);
    }
}