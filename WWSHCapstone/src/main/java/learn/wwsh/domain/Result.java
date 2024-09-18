package learn.wwsh.domain;

import java.util.Objects;

public class Result<T> {
    private T payload;
    private boolean success = true;
    private String message;

    public Result() {
    }

    public Result(T payload) {
        this.payload = payload;
    }

    public Result(String message) {
        this.success = false;
        this.message = message;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result<?> result = (Result<?>) o;
        return success == result.success && Objects.equals(payload, result.payload) && Objects.equals(message, result.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(payload, success, message);
    }
}
