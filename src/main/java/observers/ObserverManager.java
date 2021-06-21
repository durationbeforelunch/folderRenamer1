package observers;
public class ObserverManager {

    private final Observed observed;

    public ObserverManager(Observed observed) {
        this.observed = observed;
    }

    public void addObserver(Observer observer) {
        observed.addObserver(observer);
    }
}
