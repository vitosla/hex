package by.lvl.hexmap.api.events;

public abstract class SuccessfulEvent<T> {

    private T data;

    public SuccessfulEvent(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
