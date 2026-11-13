package edu.file;

import org.springframework.boot.info.BuildProperties;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;


public class FileUtil {

    private static final int BUFFER_LEN = 1024;
    private static final String ERROR_FILE_NOT_EXIST = "file does not exist";

    private FileUtil() {}

    /**
     * Get a file content from resources folder.
     * This method won't work on spring projects !
     * @param fileName file name in resources
     * @return a list of lines representing file content
     * @throws IOException any io exception
     */
    public static List<String> getResourceLines(String fileName) throws IOException {
        InputStream inputStream = FileUtil.class.getResourceAsStream(File.separator + fileName);
        if (inputStream != null) {
            return IOUtil.getStringList(inputStream);
        }
        throw new IOException(ERROR_FILE_NOT_EXIST);
    }

    /**
     * Get a file from resources folder.
     * @param fileName file name in resources
     * @return file content
     * @throws IOException any io exception
     */
    public static String getResourceContent(String fileName) throws IOException {
        InputStream inputStream = FileUtil.class.getResourceAsStream(File.separator + fileName);
        if (inputStream != null) {
            return IOUtil.getString(inputStream);
        }
        throw new IOException(ERROR_FILE_NOT_EXIST);
    }

    /**
     * Get a byte array from resources folder.
     * @param fileName file name in resources
     * @return byte array
     * @throws IOException any io exception
     */
    public static byte[] getResourceByteArray(String fileName) throws IOException {
        InputStream inputStream = FileUtil.class.getResourceAsStream(File.separator + fileName);
        if (inputStream != null) {
            byte[] byteArray = new byte[inputStream.available()];
            inputStream.read(byteArray,0, byteArray.length);
            return byteArray;
        }
        throw new IOException(ERROR_FILE_NOT_EXIST);
    }

    /**
     * Get content of a property file from ressources folder.
     * @param filePath property file path
     * @return property object
     * @throws IOException any io exception
     */
    public static Properties getPropertiesFromResources(String filePath) throws IOException {
        ClassLoader classLoader = FileUtil.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(filePath);
        if (inputStream != null) {
            Properties prop = new Properties();
            prop.load(inputStream);
            return prop;
        }
        throw new IOException(ERROR_FILE_NOT_EXIST);
    }

    /**
     * Get content of a property file.
     * @param filePath property file path
     * @return property object
     * @throws IOException any io exception
     */
    public static Properties getProperties(String filePath) throws IOException {
        Properties prop = new Properties();
        InputStream stream = new FileInputStream(filePath);
        prop.load(stream);
        return prop;
    }

    /**
     * Create a folder if it doesn't exist already.
     * @param path folder path
     */
    public static void createDirectory(String path) {
        new File(path).mkdirs();
    }

    /**
     * Create a temp folder for the application.
     * @param buildProperties BuildProperties
     * @return application temp directory
     */
    public static String createApplicationTempDirectory(BuildProperties buildProperties) {
        String path = getTempDirectory() + File.separator
                + buildProperties.getArtifact() + "-" + buildProperties.getVersion();
        createDirectory(path);
        return path;
    }

    /**
     * Is the following path an existing directory ?
     * @param directoryPath path of directory to check
     * @return yes if path is an existing directory, false otherwise
     */
    public static boolean isDirectory(String directoryPath) {
        Path path = Paths.get(directoryPath);
        return path.toFile().isDirectory();
    }

    /**
     * Does a file or folder exist ?
     * @param filePath file or folder path
     * @return yes if the file or folder exist, false otherwise
     */
    public static boolean doesPathExist(String filePath) {
        Path path = Paths.get(filePath);
        return path.toFile().exists();
    }

    /**
     * Delete a file or an empty folder.
     * @param path file or directory path
     * @throws IOException any exception
     */
    public static void delete(String path) throws IOException {
        Files.deleteIfExists(Paths.get(path));
    }

    /**
     * Append content to a file, file will be created if it doesn't exist.
     * @param filePath file path
     * @param fileContent file content to append
     * @throws IOException any exception
     */
    public static void append(String filePath, String fileContent) throws IOException {
        new File(filePath).getParentFile().mkdirs();
        Files.writeString(Paths.get(filePath), fileContent, APPEND, CREATE);
    }

    /**
     * Create a file with some content.
     * @param filePath file path
     * @param fileContent file content to set
     * @throws IOException any exception
     */
    public static void create(String filePath, String fileContent) throws IOException {
        Path path = Paths.get(filePath);
        if (path.toFile().exists()) {
            Files.delete(path);
        }
        new File(filePath).getParentFile().mkdirs();
        Files.writeString(path, fileContent, CREATE);
    }

    /**
     * Create an empty file.
     * @param filePath file path
     * @throws IOException any exception
     */
    public static void create(String filePath) throws IOException {
        create(filePath, "");
    }

    /**
     * Create a file from a byte array
     * @param fileContent file content in a byte array
     * @param filePath file path
     * @throws IOException any exception
     */
    public static void create(String filePath, byte[] fileContent) throws IOException {
        Path path = Paths.get(filePath);
        new File(filePath).getParentFile().mkdirs();
        Files.write(path, fileContent);
    }

    /**
     * Get file content as a list of strings
     * @param filePath file path
     * @return file content in a list of strings
     * @throws IOException any exception
     */
    public static List<String> getFileLines(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllLines(path);
    }

    /**
     * Get file content as a string.
     * @param filePath file path
     * @return file content in a string
     * @throws IOException any exception
     */
    public static String getFileContent(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return new String(Files.readAllBytes(path));
    }

    /**
     * Get a file content into byte array format
     * @param filePath file path
     * @return a byte array
     * @throws IOException any exception
     */
    public static byte[] getByteContent(String filePath) throws IOException {
        return Files.readAllBytes(Paths.get(filePath));
    }

    /**
     * Clear a directory.
     * @param directoryPath directory path
     * @throws IOException any exception
     */
    public static void clearDirectory(String directoryPath) throws IOException {
        Path path = Paths.get(directoryPath);
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * List a directory.
     * @param directoryPath path of directory
     * @return a sorted list of files and subdirectories
     * @throws IOException any exception
     */
    public static List<String> listDirectory(String directoryPath) throws IOException {
        List<String> result = new ArrayList<>();
        Path path = Paths.get(directoryPath);
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                result.add(file.toString());
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                result.add(dir.toString());
                return FileVisitResult.CONTINUE;
            }
        });

        Collections.sort(result);
        return result;
    }

    /**
     * Get the list of files from a directory
     * @param directoryPath path of directory to list content
     * @return a list of file paths
     * @throws IOException any exception
     */
    public static List<String> listFiles(String directoryPath) throws IOException {
        return listFiles(directoryPath, null);
    }

    public static List<String> listFiles(String directoryPath, String fileExt) throws IOException {
        List<String> fileList = new ArrayList<>();
        try (Stream<Path> walk = Files.walk(Paths.get(directoryPath))) {
            walk.filter(path -> path.toFile().isFile())
                .filter((fileExt != null) ? path -> path.toString().endsWith(".".concat(fileExt)) : path -> true)
                .sorted()
                .map(Path::toString)
                .forEach(fileList::add);
        }
        return fileList;
    }

    /**
     * Get temp directory from current file system
     * @return temp directory
     */
    public static String getTempDirectory() {
        return System.getProperty("java.io.tmpdir");
    }

    /**
     * Create a zip archive
     * @param directoryPath directory where all files will be taken as input for the zip archive
     * @param outputPath ouput path to create zip archive
     * @throws IOException any exception
     */
    public static void zipFiles(String directoryPath, String outputPath) throws IOException {
        try {
            byte[] buffer = new byte[BUFFER_LEN];
            try (FileOutputStream fos = new FileOutputStream(outputPath)) {
                try (ZipOutputStream zos = new ZipOutputStream(fos)) {
                    List<String> filesPath = listFiles(directoryPath);
                    for (String filePath : filesPath) {
                        File file = new File(filePath);
                        String relativeFilePath = filePath.replaceFirst(directoryPath, "");
                        relativeFilePath = (relativeFilePath.startsWith(File.separator)) ? relativeFilePath.substring(1) : relativeFilePath;
                        ZipEntry ze = new ZipEntry(relativeFilePath);
                        zos.putNextEntry(ze);
                        try(FileInputStream fis = new FileInputStream(file)) {
                            int len;
                            while ((len = fis.read(buffer)) > 0) {
                                zos.write(buffer, 0, len);
                            }
                        }
                    }
                }
            }

        } catch(IOException ioEx) {
            throw new IOException("an error occurred while zipping files", ioEx);
        }
    }

    /**
     * Unzip file from an archive.
     * @param archivePath zip file path
     * @param outputPath output path
     * @throws IOException any io exception
     */
    public static void unzipFiles(String archivePath, String outputPath) throws IOException {
        try {
            String outputPathCanonical = new File(outputPath).getCanonicalPath();
            byte[] buffer = new byte[BUFFER_LEN];
            // create output directory if not exists
            File folder = new File(outputPath);
            if (!folder.exists()) {
                folder.mkdir();
            }

            // get archive content
            try (ZipInputStream zis = new ZipInputStream(new FileInputStream(archivePath))) {
                ZipEntry ze = zis.getNextEntry();
                while (ze != null) {
                    File newFile = new File(outputPath + File.separator + ze.getName());
                    if (!newFile.getCanonicalPath().startsWith(outputPathCanonical)) {
                        throw new IOException("entry is trying to leave the target directory: " + ze.getName());
                    }

                    // create all non exists folders
                    new File(newFile.getParent()).mkdirs();
                    try (FileOutputStream fos = new FileOutputStream(newFile)) {
                        // unzip current file
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }

                        ze = zis.getNextEntry();
                    }
                }
            }

        } catch(IOException ioEx) {
            throw new IOException("an error occurred while unzipping files", ioEx);
        }
    }

    /**
     * Get the list of files from a directory, then clone each one of them.
     * @param directoryPath path of directory to browse
     * @param numberOfClones how many clones to create per file
     * @throws IOException any io exception
     */
    public static void cloneFiles(String directoryPath, int numberOfClones) throws IOException {
        List<String> fileList = listFiles(directoryPath);
        for (String filePath : fileList) {
            File fileSource = new File(filePath);
            String extension = getExtension(filePath);
            String fileWithoutExtension = (extension != null) ? filePath.substring(0, filePath.length() - extension.length() - 1) : filePath;
            for (int i=1; i<=numberOfClones; i++) {
                StringBuilder sb = new StringBuilder();
                sb.append(String.format("%s-clone%d", fileWithoutExtension, i));
                if (extension != null) {
                    sb.append(".");
                    sb.append(extension);
                }
                File fileCopy = new File(sb.toString());
                Files.copy(fileSource.toPath(), fileCopy.toPath());
            }
        }
    }

    /**
     * Get a file extension.
     * @param filePath file path
     * @return file extension or null if none
     */
    public static String getExtension(String filePath) {
        return (filePath.contains(".") ? filePath.substring(filePath.lastIndexOf('.') + 1) : null);
    }

}