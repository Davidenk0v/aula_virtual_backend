package com.grupo2.aulavirtual.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * Esta contiene una serie de utilidades para el control y manejo de archivos.
 */
public class FileUtil {

    String root;

    public FileUtil() {
        root = new File("").getAbsolutePath();
    }

    public FileUtil(String root) {
        this.root = root;
    }

    /**
     * Guarda el archivo en la ruta asignada.
     * 
     * @param file MultipartFile que contiene la informacion del archivo.
     * @param path String con la ruta donde se va a almacenar el archivo (Debe
     *             terminar en / o \)
     * @return String con la ruta absoluta del archivo, si ocurre un error, envia un
     *         null.
     */
    public String saveFile(MultipartFile file, String path) {
        File newPath = new File(root + path);
        if (!newPath.exists()) {
            newPath.mkdirs();
        }
        String extension = getExtensionByName(file.getOriginalFilename());
        String newName = stringGenerator(25) + "." + extension;
        File newFile = new File(root + path + newName);
        try {
            file.transferTo(newFile);
            return newFile.getAbsolutePath();
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Sobreescribe el archivo en la ruta asignada.
     * 
     * @param file MultipartFile que contiene la informacion del archivo.
     * @param path String con la ruta donde se va a almacenar el archivo (Debe
     *             terminar en / o \)
     * @return String con la ruta absoluta del archivo, si ocurre un error, envia un
     *         null.
     */
    public String updateFile(MultipartFile file, String path) {
        File newPath = new File(root + path);
        File folder = new File(newPath.getParent());
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File newFile = new File(path);
        try {
            file.transferTo(newFile);
            return newFile.getAbsolutePath();
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Guarda la lista de archivos en la ruta asignada.
     * 
     * @param file List<MultipartFile> que contiene la informacion de lo archivos.
     * @param path String con la ruta donde se va a almacenar los archivos (Debe
     *             terminar en / o \ dependiendo del SO)
     * @return List<String> con la ruta absoluta de los archivos, si ocurre un
     *         error, envia un la lista vacia.
     */
    public List<String> saveListFile(List<MultipartFile> files, String path) {
        List<String> pathList = new ArrayList<>();
        File newPath = new File(root + path);
        if (!newPath.exists()) {
            newPath.mkdirs();
        }
        for (MultipartFile file : files) {
            String extension = getExtensionByName(file.getOriginalFilename());
            String newName = stringGenerator(25) + "." + extension;
            File newFile = new File(root + path + newName);
            try {
                file.transferTo(newFile);
                pathList.add(newFile.getAbsolutePath());
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
                return pathList;
            }
        }
        return pathList;
    }

    /**
     * Recoge el archivo almacenado y lo convierte a un byte[]
     * 
     * @param fileName String con la ruta del archivo.
     * @return byte[] con los datos del archivo, se envia byte[0], si el archivo no
     *         existe u ocurre un error.
     */
    public byte[] sendFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            try {
                return Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
                return new byte[0];
            }
        } else {
            return new byte[0];
        }
    }

    /**
     * Recoge una lista de archivos almacenados y los convierte a byte[]
     * 
     * @param fileName El nombre de la imagen.
     * @return List<byte[]> con los datos de los archivos, se devuelve la lista
     *         vacia, si el archivo no existe u ocurre un error.
     */
    public List<byte[]> sendArrayFile(List<String> paths) {
        List<byte[]> files = new ArrayList<>();
        for (String path : paths) {
            File file = new File(path);
            if (file.exists()) {
                try {
                    files.add(Files.readAllBytes(file.toPath()));
                } catch (IOException e) {
                    e.printStackTrace();
                    return files;
                }
            }
        }
        return files;
    }

    /**
     * Metodo para obtener la extencion de un archivo.
     * 
     * @param path String con la ruta del archivo.
     * @return String con la extencion del archivo.
     */
    public String getExtensionByPath(String path) {
        File file = new File(path);
        String ext = null;
        String s = file.getName();
        int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    /**
     * Metodo para obtener la extencion de un archivo.
     * 
     * @param path String con la ruta del archivo.
     * @return String con la extencion del archivo.
     */
    public String getExtensionByName(String file) {
        String ext = null;
        int i = file.lastIndexOf('.');
        if (i > 0 && i < file.length() - 1) {
            ext = file.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    /**
     * Metodo para obtener el mediatype de un archivo.
     * 
     * @param extension String con la extencion del archivo.
     * @return String con el mediatype del archivo.
     */
    public String getMediaType(String extension) {
        String mediaType = null;
        String imgType = "application/";
        String appType = "image/";
        String textType = "text/";
        switch (extension) {
            case "csv":
                mediaType = textType + extension;
                break;
            case "txt":
                mediaType = textType + extension;
                break;
            case "png":
                mediaType = appType + extension;
                break;
            case "jpeg":
                mediaType = appType + extension;
                break;
            case "jpg":
                mediaType = appType + extension;
                break;
            case "pdf":
                mediaType = imgType + extension;
                break;
            case "json":
                mediaType = imgType + extension;
                break;
            case "xml":
                mediaType = imgType + extension;
                break;
            default:
                mediaType = "*/*";
                break;
        }
        return mediaType;
    }

    /**
     * Metodo para generar Strings aleatorios.
     * 
     * @param size int con el tamaño del String generado.
     * @return String aleatorio.
     */
    public static String stringGenerator(int size) {
        String menu = "menuABCDFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        char[] alfabeto = menu.toCharArray();
        SecureRandom objetoRandom;
        try {
            objetoRandom = SecureRandom.getInstance("SHA1PRNG", "SUN");
            char[] caracteres = new char[size];
            for (int i = 0; i < size; i++) {
                caracteres[i] = alfabeto[objetoRandom.nextInt(alfabeto.length)];
            }
            return String.valueOf(caracteres);
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
            return "";
        }
    }

}
