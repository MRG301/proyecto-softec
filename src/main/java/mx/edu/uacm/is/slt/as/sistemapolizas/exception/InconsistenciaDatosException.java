package mx.edu.uacm.is.slt.as.sistemapolizas.exception;

public class InconsistenciaDatosException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InconsistenciaDatosException(String mensaje) {
        super(mensaje);
    }
}