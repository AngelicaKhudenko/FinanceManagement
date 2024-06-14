package by.it_academy.jd2.audit_service.core.exceptions;

public class FieldsIncorrectException extends IllegalArgumentException{

    public String getField() {
        return field;
    }

    private final String field;

    public FieldsIncorrectException(String field, String message) {

        super(message);
        this.field = field;
    }

}
