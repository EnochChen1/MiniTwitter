/*
 * This implements Subject from the Observer Design Pattern
 */
public interface Subject {

    public void attach(Observer observer);
    public void notifyObservers();

}