package pe.isil.Saturno_1431.exception;

public class FileNotFoundException extends RuntimeException {

    //creamos lso constructores


    public FileNotFoundException(String message) {
        super(message);
    }

    public FileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
