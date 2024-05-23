package com.grupo2.aulavirtual.util.files;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

/**
 * Esta contiene una serie de utilidades para el control y manejo de archivos.
 */
public class FileUtil {

    @Value("${fileutil.default.root.path}")
    private String root;
    @Value("${fileutil.default.folder.path}")
    private String defaultPath;

    public FileUtil() {
        if (root == null) {
            root = new File("").getAbsolutePath();
        }
        if (defaultPath == null) {
            defaultPath = "/Media/Default/";
        }
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
            return newFile.getName();
        } catch (IllegalStateException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Sobreescribe el archivo en la ruta asignada.
     * 
     * @param file   MultipartFile que contiene la informacion del archivo.
     * @param path   String con la ruta donde se va a almacenar el archivo (Debe
     *               terminar en / o \)
     * @param oldPth String con la ruta del anterior archivo.
     * @return String con la ruta absoluta del archivo, si ocurre un error, envia un
     *         null.
     */
    public String updateFile(MultipartFile file, String path, String oldPth) {
        try {
            File oldFile = new File(root + oldPth);
            File newPath = new File(root + path);
            if (!newPath.exists()) {
                newPath.mkdirs();
            }
            String extension = getExtensionByName(file.getOriginalFilename());
            String newName = generateHash(file.getBytes()) + "." + extension;
            File newFile = new File(root + path + newName);
            if (!Objects.equals(oldFile.getName(), newFile.getName())) {
                file.transferTo(newFile);
                deleteFile(oldPth);
            }
            return newFile.getName();
        } catch (IllegalStateException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Sobreescribe el archivo en la ruta asignada.
     * 
     * @param file   MultipartFile que contiene la informacion del archivo.
     * @param path   String con la ruta donde se va a almacenar el archivo (Debe
     *               terminar en / o \)
     * @param oldPth String con la ruta del anterior archivo.
     * @return String con la ruta absoluta del archivo, si ocurre un error, envia un
     *         null.
     */
    public String updateFile(MultipartFile file, String path, String oldPth, String defaultFile) {
        try {
            File oldFile = new File(root + oldPth);
            File newPath = new File(root + path);
            if (!newPath.exists()) {
                newPath.mkdirs();
            }
            String extension = getExtensionByName(file.getOriginalFilename());
            String newName = generateHash(file.getBytes()) + "." + extension;
            File newFile = new File(root + path + newName);
            if (!Objects.equals(oldFile.getName(), newFile.getName())) {
                file.transferTo(newFile);
                deleteFile(oldPth, defaultFile);
            }
            return newFile.getName();
        } catch (IllegalStateException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Borra el archivo indicado.
     * 
     * @param path String con la ruta al archivo.
     */
    public void deleteFile(String path) {
        try {
            Path oldFilePath = Paths.get(root + path);
            Files.delete(oldFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Borra el archivo indicado.
     * 
     * @param path String con la ruta al archivo.
     */
    public void deleteFile(String path, String defaultFile) {
        try {
            File pathFile = new File(root + path);
            File defaultImg = new File(setDefaultImage(defaultFile));
            if (!Objects.equals(defaultImg.getName(), pathFile.getName())) {
                Files.delete(pathFile.toPath());
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
    public String setDefaultImage(String name) {
        try {
            File defaultImg = new File(root + defaultPath + name);
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
     * Recoge el archivo almacenado y lo convierte a un byte[]
     * 
     * @param fileName String con la ruta del archivo.
     * @return byte[] con los datos del archivo, se envia byte[0], si el archivo no
     *         existe u ocurre un error.
     */
    public byte[] sendFile(String path) {
        try {
            File file = new File(root + path);
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
     * Recoge el archivo almacenado y lo convierte a un byte[]
     * 
     * @param fileName String con la ruta del archivo.
     * @return byte[] con los datos del archivo, se envia byte[0], si el archivo no
     *         existe u ocurre un error.
     */
    public byte[] sendFile(String path, String defaultImg) {
        try {
            File file = new File(root + path);
            File defaultImgFile = new File(root + defaultPath + defaultImg);
            if (!Objects.equals(file.getName(), defaultImgFile.getName())) {
                if (file.exists()) {
                    return Files.readAllBytes(file.toPath());
                } else {
                    return new byte[0];
                }
            } else {
                return Files.readAllBytes(defaultImgFile.toPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }


    /**
     * Metodo para obtener la extencion de un archivo.
     * 
     * @param path String con la ruta del archivo.
     * @return String con la extencion del archivo.
     */
    public String getExtensionByPath(String path) {
        File file = new File(root + path);
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
