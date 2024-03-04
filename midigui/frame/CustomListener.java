package frame;

/**
 * the following interface is meant to make all button communications go
 * through a controller
 * the following interface will perform an event to a listener for the toolbar
 * String iin the emmited() is only for testing
 */
public interface CustomListener {

    public void emmited(String s); //for now it accepts a string to display button messages, should be changed to other objects
}
