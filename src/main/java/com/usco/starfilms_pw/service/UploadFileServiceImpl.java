package com.usco.starfilms_pw.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Implementación del servicio para gestionar la carga, recuperación y eliminación de archivos.
 * Esta clase implementa la interfaz {@link IUploadFileService} y proporciona la lógica de negocio para manejar
 * operaciones relacionadas con archivos cargados, como cargar, guardar, eliminar y obtener recursos de archivos en
 * el sistema de archivos del servidor.
 */
@Service
public class UploadFileServiceImpl implements IUploadFileService {

    private final static String UPLOADS_FOLDER = "uploads";

    /**
     * Carga un archivo desde el sistema de archivos.
     * Este método obtiene el archivo solicitado desde la carpeta de "uploads" y lo convierte en un {@link Resource}.
     * Si el archivo no existe o no es legible, se lanza una excepción {@link RuntimeException}.
     * @param filename el nombre del archivo que se desea cargar.
     * @return el archivo como un {@link Resource}.
     * @throws MalformedURLException si hay un error al formar la URL del archivo.
     * @throws RuntimeException si el archivo no existe o no es legible.
     */
    @Override
    public Resource load(String filename) throws MalformedURLException {
        Path path = getPath(filename);
        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            throw new RuntimeException("Error in path: " + path.toString());
        }
        return resource;
    }

    /**
     * Copia un archivo al sistema de archivos.
     * Este método guarda el archivo proporcionado en el directorio de "uploads" con un nombre único generado
     * a partir de un UUID. Si la operación es exitosa, devuelve el nombre único del archivo.
     * @param file el archivo {@link MultipartFile} que se desea guardar.
     * @return el nombre único asignado al archivo guardado.
     * @throws IOException si ocurre un error al copiar el archivo.
     */
    @Override
    public String copy(MultipartFile file) throws IOException {
        String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path rootPath = getPath(uniqueFilename);
        Files.copy(file.getInputStream(), rootPath);
        return uniqueFilename;
    }

    /**
     * Elimina un archivo del sistema de archivos.
     * <p>
     * Este método elimina el archivo especificado en el directorio de "uploads". Si el archivo no existe o no se
     * puede eliminar, devuelve {@code false}. Si el archivo se elimina exitosamente, devuelve {@code true}.
     *
     * @param filename el nombre del archivo que se desea eliminar.
     * @return {@code true} si el archivo se elimina con éxito, de lo contrario, {@code false}.
     */
    @Override
    public boolean delete(String filename) {
        Path rootPath = getPath(filename);
        File file = rootPath.toFile();
        if(file.exists() && file.canRead()) {
            if(file.delete()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene la ruta absoluta de un archivo en la carpeta de "uploads".
     * Este método resuelve la ruta del archivo en la carpeta "uploads" y devuelve la ruta absoluta.
     * @param filename el nombre del archivo cuya ruta se desea obtener.
     * @return la ruta absoluta del archivo.
     */
    public Path getPath(String filename) {
        return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
    }

}
