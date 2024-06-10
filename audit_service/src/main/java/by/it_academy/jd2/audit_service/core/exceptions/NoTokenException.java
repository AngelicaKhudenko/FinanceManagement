package by.it_academy.jd2.audit_service.core.exceptions;

public class NoTokenException extends RuntimeException{

    public NoTokenException() {
        super("Для выполнения запроса на данный адрес требуется передать токен авторизации");
    }
}
