package com.grupo2.aulavirtual.util;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.web.multipart.MultipartFile;

/**
 * Esta contiene una serie de utilidades para el control y manejo de archivos.
 */
public class FileUtil {

    String root;
    String defaultImage;
    String fileSeparator = "\\";

    public FileUtil() {
        root = new File("").getAbsolutePath();
        defaultImage = "Media/Default/default.png";
    }

    public FileUtil(String root) {
        this.root = root;
        defaultImage = "Media/Default/default.png";
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
        try {
            File newPath = new File(root + path);
            if (!newPath.exists()) {
                newPath.mkdirs();
            }
            String extension = getExtensionByName(file.getOriginalFilename());
            String newName = generateHash(file.getBytes()) + "." + extension;
            File newFile = new File(root + path + newName);
            file.transferTo(newFile);
            return newFile.getAbsolutePath();
        } catch (IllegalStateException | IOException | NoSuchAlgorithmException e) {
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
     * @param oldPth String con la ruta del anterior archivo.
     * @return String con la ruta absoluta del archivo, si ocurre un error, envia un
     *         null.
     */
    public String updateFile(MultipartFile file, String path, String oldPth) {
        try {
            File oldFile = new File(oldPth);
            File newPath = new File(root + path);
            if (!newPath.exists()) {
                newPath.mkdirs();
            }
            String extension = getExtensionByName(file.getOriginalFilename());
            String newName = generateHash(file.getBytes()) + "." + extension;
            File newFile = new File(root + path + newName);
            if (!Objects.equals(oldFile.getName(), newFile.getName())) {
                file.transferTo(newFile);
                deleteFile(oldFile.getAbsolutePath());
            }
            return newFile.getAbsolutePath();
        } catch (IllegalStateException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Borra el archivo indicado.
     * @param path String con la ruta al archivo.
     */
    public void deleteFile(String path) {
        try {
            File pathFile = new File(path);
            File defaultImg = new File(defaultImage);
            File defaultImgPath = new File(defaultImg.getParent());
            if (!Objects.equals(defaultImgPath.getAbsolutePath(), pathFile.getAbsolutePath())) {
                Path oldFilePath = Paths.get(path);
                Files.delete(oldFilePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo para asignar la imagen por defecto.
     * 
     * @return String con la ruta absoluta del archivo, si ocurre un error, envia un
     *         null.
     */
    public String setDefaultImage() {
        try {
            File defaultImg = new File(defaultImage);
            File defaultImgPath = new File(defaultImg.getParent());
            if (!defaultImgPath.exists()) {
                defaultImgPath.mkdirs();
            }
            return defaultImg.getAbsolutePath();
        } catch (IllegalStateException e) {
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
        try {
            List<String> pathList = new ArrayList<>();
            File newPath = new File(root + path);
            if (!newPath.exists()) {
                newPath.mkdirs();
            }
            for (MultipartFile file : files) {
                String extension = getExtensionByName(file.getOriginalFilename());
                String newName = stringGenerator(25) + "." + extension;
                File newFile = new File(root + path + newName);

                file.transferTo(newFile);
                pathList.add(newFile.getAbsolutePath());
            }
            return pathList;
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Recoge el archivo almacenado y lo convierte a un byte[]
     * 
     * @param fileName String con la ruta del archivo.
     * @return byte[] con los datos del archivo, se envia byte[0], si el archivo no
     *         existe u ocurre un error.
     */
    public byte[] sendFile(String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                return Files.readAllBytes(file.toPath());
            } else {
                return new byte[0];
            }
        } catch (IOException e) {
            e.printStackTrace();
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
        try {
            List<byte[]> files = new ArrayList<>();
            for (String path : paths) {
                File file = new File(path);
                if (file.exists()) {
                    files.add(Files.readAllBytes(file.toPath()));
                }
            }
            return files;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
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
    public String stringGenerator(int size) {
        try {
            String menu = "menuABCDFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
            char[] alfabeto = menu.toCharArray();
            SecureRandom objetoRandom;
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

    /**
     * Metodo para conseguir el Hash de un archivo.
     * 
     * @param data byte[] del archivo.
     * @return String con el Hash SHA-256
     * @throws NoSuchAlgorithmException
     */
    public String generateHash(byte[] data) throws NoSuchAlgorithmException {
        byte[] hash = MessageDigest.getInstance("SHA-256").digest(data);
        return new BigInteger(1, hash).toString(16);
    }

}
