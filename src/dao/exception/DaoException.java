package dao.exception;//package dao.exception;

public class DaoException extends Exception {

    public DaoException(Throwable cause) {
        super(cause);
    }

    public DaoException(String message) {
        super(message);
    }

}