package by.it_academy.jd2.classifier_service.core.exceptions;

public class NoTokenException extends RuntimeException{

    public NoTokenException() {
        super("Для выполнения запроса на данный адрес требуется передать токен авторизации");
    }
}