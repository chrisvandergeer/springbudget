package nl.cge.sbb.arch.exceptions;

/**
 * Created by geerc01 on 31-3-2017.
 */
public class ApplicationException extends RuntimeException {

    public ApplicationException(Exception exception) {
        super(exception);
    }

    public static ApplicationException wrap(Exception e) {
        return new ApplicationException(e);
    }
}
