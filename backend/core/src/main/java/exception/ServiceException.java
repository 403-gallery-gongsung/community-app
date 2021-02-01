package exception;

import lombok.Getter;

import domain.ApiResult;

/**
 * Caused by service logic
 */
@Getter
public class ServiceException extends RuntimeException {

    private Object[] messageArgs;
    private ApiResult.StatusCode statusCode;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(String message, ApiResult.StatusCode statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public ServiceException(String message, ApiResult.StatusCode statusCode, Object[] messageArgs) {
        super(message);
        this.statusCode = statusCode;
        this.messageArgs = messageArgs;
    }
}