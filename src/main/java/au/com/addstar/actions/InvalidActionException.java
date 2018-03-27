package au.com.addstar.actions;

/**
 * au.com.addstar.actions
 * Created for the Addstar MC for Archery
 * Created by Narimm on 23/03/2018.
 */
public class InvalidActionException extends Exception {
    private static final long serialVersionUID = 3718647170282193158L;
    
    public InvalidActionException(String message) {
        super(message);
    }
}
