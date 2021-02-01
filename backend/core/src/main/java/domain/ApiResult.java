package domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

/**
 * API response form.
 * It always contains [statusCode, message, data, timestamp]
 *
 * @param <T>
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ApiResult<T> {

    // Status Code
    @JsonView(JsonViewSupport.Base.class)
    private StatusCode statusCode = StatusCode.SERVER_ERROR;

    // Message
    @JsonView(JsonViewSupport.Base.class)
    private CharSequence message;

    @JsonView(JsonViewSupport.Base.class)
    private T data;

    @JsonView(JsonViewSupport.Base.class)
    private long timestamp = 0;

    private ApiResult() {
        super();
    }

    public ApiResult<T> statusCode(StatusCode statusCode) {
        setStatusCode(statusCode);
        return this;
    }

    public ApiResult<T> data(T data) {
        setData(data);
        return this;
    }

    public ApiResult<T> message(CharSequence message) {
        setMessage(message);
        return this;
    }

    public static <T> ApiResult<T> with(T data) {
        ApiResult<T> response = new ApiResult<>();
        response.setData(data);
        response.timestamp = System.currentTimeMillis();
        return response;
    }

    public static <T> ApiResult<T> ok(T data) {
        return with(data).statusCode(StatusCode.SUCCESS);
    }

    public static <T> ApiResult<T> ok(T data, CharSequence message) {
        return with(data).statusCode(StatusCode.SUCCESS).message(message);
    }

    public static ApiResult<String> error(StatusCode statusCode, CharSequence message) {
        return with("").statusCode(statusCode).message(message);
    }

    public enum StatusCode {
        SUCCESS(200),
        SERVER_ERROR(500);

        private int value;

        StatusCode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        @JsonCreator
        public static StatusCode forJsonValue(int statusCode) {
            for (StatusCode code : values()) {
                if (statusCode == code.getValue()) {
                    return code;
                }
            }

            return SERVER_ERROR;
        }

        @JsonValue
        public int toJsonValue() {
            return value;
        }
    }
}
