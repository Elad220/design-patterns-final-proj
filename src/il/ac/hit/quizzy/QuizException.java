/**
 * The QuizException class is a custom exception class for handling quiz-related exceptions,
 * including I/O failures and unsupported cloning operations.
 */

package il.ac.hit.quizzy;

import java.io.IOException;

/** Custom exception class for quiz-related exceptions */
public class QuizException extends Exception {

    private IOException ioException;
    private CloneNotSupportedException cloneNotSupportedException;

    /** Constructors */
    public QuizException(String message) {
        super(message);
    }

    /** Throw when i/o operation fails */
    public QuizException(String message, IOException ioException) {
        super(message);
        setIoException(ioException);
    }

    /** Throw when clone is not supported by the object */
    public QuizException(String message, CloneNotSupportedException cloneNotSupportedException) {
        super(message);
        setCloneNotSupportedException(cloneNotSupportedException);
    }

    /** Setters */

    public void setIoException(IOException ioException) {
        if (ioException == null) {
            throw new IllegalArgumentException("IOException cannot be null.");
        }
        this.ioException = ioException;
    }

    public void setCloneNotSupportedException(CloneNotSupportedException cloneNotSupportedException) {
        if (cloneNotSupportedException == null) {
            throw new IllegalArgumentException("CloneNotSupportedException cannot be null.");
        }
        this.cloneNotSupportedException = cloneNotSupportedException;
    }


    /** Getters */

    public IOException getIOException() {
        return ioException;
    }

    public CloneNotSupportedException getCloneNotSupportedException() {
        return cloneNotSupportedException;
    }
}
