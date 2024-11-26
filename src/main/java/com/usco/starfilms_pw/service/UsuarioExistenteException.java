package com.usco.starfilms_pw.service;

/**
 * Excepción personalizada que se lanza cuando un usuario ya existe en el sistema.
 * Esta clase extiende {@link RuntimeException} y proporciona una excepción específica para manejar el caso
 * cuando se intenta crear o registrar un usuario que ya existe en el sistema, por ejemplo, cuando se intenta
 * registrar un correo electrónico que ya está asociado con otro usuario.
 */
public class UsuarioExistenteException extends RuntimeException {

    /**
     * Constructor de la excepción {@code UsuarioExistenteException}.
     * Este constructor recibe un mensaje que describe el motivo de la excepción, que puede ser utilizado
     * para proporcionar información más detallada cuando se lanza la excepción.
     * @param message el mensaje que describe el motivo de la excepción.
     */
    public UsuarioExistenteException(String message) {
        super(message);
    }
}
