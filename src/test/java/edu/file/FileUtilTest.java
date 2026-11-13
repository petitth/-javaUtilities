package edu.file;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.info.BuildProperties;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;


public class FileUtilTest {

    private static final Logger LOG = LoggerFactory.getLogger(FileUtilTest.class);
    private AutoCloseable openMocks;
    private String tempFolder;

    @TempDir
    Path tempDir;

    @Mock
    BuildProperties buildProperties;

    @BeforeEach
    public void setup() throws IOException {
        openMocks = MockitoAnnotations.openMocks(this);
        // create temp folder
        tempFolder = tempDir.toString() + File.separator + "file-util";
        Files.createDirectories(Paths.get(tempFolder));
        // update properties file for this test class
        Properties prop = FileUtil.getPropertiesFromResources("settings.properties");
        prop.setProperty("param", "foo");
        updatePropertiesFile(prop, "settings.properties");
    }

    @AfterEach
    public void tearDown() throws Exception {
        // revert properties file to original values
        Properties prop = FileUtil.getPropertiesFromResources("settings.properties");
        prop.setProperty("param", "one");
        updatePropertiesFile(prop, "settings.properties");
        // confirm we are using original values
        prop = FileUtil.getPropertiesFromResources("settings.properties");
        assertEquals("one", prop.get("param"));

        openMocks.close();
    }

    /**
     * Update values of a properties file in resources folder.
     * @param properties properties
     * @param fileName file name
     * @throws FileNotFoundException file not found exception
     * @throws IOException any other io exception
     */
    private static void updatePropertiesFile(Properties properties, String fileName) throws FileNotFoundException, IOException {
        ClassLoader classLoader = FileUtilTest.class.getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        properties.store(new FileOutputStream(file), null);
    }

    @Test
    public void readFileFromResources() throws IOException {
        List<String> resourceLines = FileUtil.getResourceLines("animal.txt");
        assertEquals(5, resourceLines.size());

        String resourceContent = FileUtil.getResourceContent("animal.txt");
        assertEquals("dog\ncat\nbird\nhorse\nmouse", resourceContent);
    }

    @Test
    public void readFileFromResourcesAlternateWay() throws IOException {
        String content = FileUtil.getFileContent("./src/test/resources/animal.txt");
        assertEquals("dog\ncat\nbird\nhorse\nmouse", content);
    }

    @Test
    public void readFileFromResourcesAsByteArray() throws IOException {
        String filePath = tempFolder + File.separator + "squirrel.jpg";
        byte[] fileContent = FileUtil.getResourceByteArray("squirrel.jpg");
        FileUtil.create(filePath, fileContent);
        assertTrue(FileUtil.doesPathExist(filePath));
    }

    @Test
    public void readFileFromResourcesAsByteArrayWithException() {
        try {
            byte[] fileContent = FileUtil.getResourceByteArray("foo.jpg");
            fail("it cannot work since file does not exist");
        } catch(IOException ioEx) {
            assertTrue(true, "confirm file does not exist");
        }
    }

    @Test
    public void getResourceLinesWithException() {
        try {
            List<String> resourceLines = FileUtil.getResourceLines("foo.txt");
            fail("it cannot work since file does not exist");
        } catch(IOException ioEx) {
            assertTrue(true, "confirm file does not exist");
        }
    }

    @Test
    public void getResourceContentWithException() {
        try {
            String resourceContent = FileUtil.getResourceContent("foo.txt");
            fail("it cannot work since file does not exist");
        } catch(IOException ioEx) {
            assertTrue(true, "confirm file does not exist");
        }
    }

    @Test
    public void getPropertiesFromResourcesWithException() {
        try {
            Properties properties = FileUtil.getPropertiesFromResources("foo.properties");
            fail("it cannot work since file does not exist");
        } catch(IOException ioEx) {
            assertTrue(true, "confirm file does not exist");
        }
    }

    @Test
    public void getPropertiesWithException() {
        try {
            Properties properties = FileUtil.getProperties(tempFolder + File.separator + "foo.properties");
            fail("it cannot work since file does not exist");
        } catch(IOException ioEx) {
            assertTrue(true, "confirm file does not exist");
        }
    }

    @Test
    public void isDirectory() {
        assertTrue(FileUtil.isDirectory(tempFolder));
    }

    @Test
    public void createFileWichExistsAlready() throws Exception {
        FileUtil.create(tempFolder + File.separator + "foo", "foo");
        FileUtil.create(tempFolder + File.separator + "foo", "bar");
        assertEquals("bar", FileUtil.getFileContent(tempFolder + File.separator + "foo"));
    }

    @Test
    public void createInsertDeleteFileFolder() {
        try {
            assertTrue( FileUtil.doesPathExist(tempFolder) );

            String helloTxtPath = tempFolder + File.separator + "abc" + File.separator + "hello.txt";
            FileUtil.append(helloTxtPath, "hello");
            assertTrue( FileUtil.doesPathExist(helloTxtPath) );
            FileUtil.append(helloTxtPath, "\n");
            FileUtil.append(helloTxtPath, "world");
            assertEquals("hello\nworld", FileUtil.getFileContent(helloTxtPath));

            String testPath = tempFolder + File.separator + "test";
            FileUtil.append(testPath, "test\n1 2 3\n4 5 6");
            assertTrue( FileUtil.doesPathExist(testPath) );
            List<String> fileLines = FileUtil.getFileLines(testPath);
            assertEquals("test", fileLines.get(0));
            assertEquals(3, fileLines.size());
            FileUtil.delete(testPath);
            assertFalse( FileUtil.doesPathExist(testPath) );

            String somethingPath = tempFolder + File.separator + "something";
            FileUtil.create(somethingPath, "anything");
            assertEquals("anything", FileUtil.getFileContent(somethingPath));
            FileUtil.create(somethingPath);
            assertEquals("", FileUtil.getFileContent(somethingPath));

        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void createTreeStructure() {
        try {
            String springPath = tempFolder + File.separator + "spring";
            String summerPath = tempFolder + File.separator + "summer";
            String autumnPath = tempFolder + File.separator + "autumn";
            String winterPath = tempFolder + File.separator + "winter";
            FileUtil.createDirectory(springPath);
            FileUtil.createDirectory(summerPath + File.separator + "vacation");
            FileUtil.createDirectory(autumnPath);
            FileUtil.createDirectory(winterPath);

            FileUtil.create(springPath + File.separator + "april.txt","april month");
            FileUtil.create(springPath + File.separator + "may");
            FileUtil.create(summerPath + File.separator + "vacation" + File.separator + "july.txt", "july month");
            FileUtil.create(summerPath + File.separator + "vacation" + File.separator + "august");
            FileUtil.create(summerPath + File.separator + "september");
            FileUtil.create(autumnPath + File.separator + "november.txt", "november month");
            FileUtil.create(winterPath + File.separator + "december");

            List<String> items = FileUtil.listDirectory(tempFolder);
            assertEquals(13, items.size());
            assertEquals(tempFolder, items.get(0));
            assertEquals(autumnPath, items.get(1));
            assertEquals(winterPath + File.separator + "december", items.get(12));

            List<String> files = FileUtil.listFiles(tempFolder);
            assertEquals(7, files.size());
            assertEquals(autumnPath + File.separator + "november.txt", files.get(0));
            assertEquals(springPath + File.separator + "april.txt", files.get(1));
            assertEquals(springPath + File.separator + "may", files.get(2));
            assertEquals(summerPath + File.separator + "september", files.get(3));
            assertEquals(summerPath + File.separator + "vacation" + File.separator + "august", files.get(4));
            assertEquals(summerPath + File.separator + "vacation" + File.separator + "july.txt", files.get(5));
            assertEquals(winterPath + File.separator + "december", files.get(6));

            files = FileUtil.listFiles(tempFolder, "txt");
            assertEquals(3, files.size());
            assertEquals(autumnPath + File.separator + "november.txt", files.get(0));
            assertEquals(springPath + File.separator + "april.txt", files.get(1));
            assertEquals(summerPath + File.separator + "vacation" + File.separator + "july.txt", files.get(2));
            FileUtil.clearDirectory(tempFolder);

        } catch(Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void readPropertiesAsResource() {
        try {
            Properties prop = FileUtil.getPropertiesFromResources("settings.properties");
            assertNotNull(prop.get("param"));
            assertNotNull(prop.get("day"));
            assertEquals("foo", prop.get("param"));
            assertEquals("sunday", prop.get("day"));

        } catch(Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void readPropertiesInPackage() {
        try {
            Properties prop = FileUtil.getProperties("." + File.separator +  "samples" + File.separator + "misc.properties");
            assertNotNull(prop.get("color"));
            assertNotNull(prop.get("number"));
            assertEquals("red", prop.get("color"));
            assertEquals("10", prop.get("number"));

        } catch(Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void getTempDirectory() throws Exception {
        String tempDir = FileUtil.getTempDirectory();
        LOG.info("temp directory = {}", tempDir);
        assertNotNull(tempDir);
    }

    @Test
    public void createFileFromByte() throws Exception {
        byte[] fileContent = "hello world".getBytes();
        FileUtil.create(tempFolder + File.separator + "abc" + File.separator + "test", fileContent);
        assertTrue(FileUtil.doesPathExist(tempFolder + File.separator + "abc" + File.separator + "test"));
        assertEquals("hello world", FileUtil.getFileContent(tempFolder + File.separator + "abc" + File.separator + "test"));
    }

    @Test
    public void getByteContentFromFile() throws Exception {
        FileUtil.create(tempFolder + File.separator + "hello", "hello world");
        assertTrue(FileUtil.doesPathExist(tempFolder + File.separator + "hello"));

        byte[] bytes = FileUtil.getByteContent(tempFolder + File.separator + "hello");
        assertEquals("hello world", new String(bytes));
    }

    @Test
    public void zipping() throws Exception {
        String inputPath = tempFolder + File.separator + "input";
        String zipPath = tempFolder + File.separator + "zip";
        String unzipPath = tempFolder + File.separator + "unzip";
        FileUtil.createDirectory(inputPath);
        FileUtil.createDirectory(zipPath);
        FileUtil.createDirectory(unzipPath);
        FileUtil.create(inputPath + File.separator + "abc" + File.separator + "file1.txt","hello world");
        FileUtil.create(inputPath + File.separator + "def" + File.separator + "file2","hello again");
        FileUtil.create(inputPath + File.separator +  "ghi" + File.separator + "jkl" + File.separator + "empty");
        FileUtil.create(inputPath + File.separator + "weather", "it is raining");

        FileUtil.zipFiles(inputPath, zipPath + File.separator + "result.zip");
        assertTrue(FileUtil.doesPathExist(zipPath + File.separator + "result.zip"));

        FileUtil.unzipFiles(zipPath + File.separator + "result.zip", unzipPath + File.separator + "all");
        assertTrue(FileUtil.doesPathExist(unzipPath + File.separator + "all" + File.separator + "abc" + File.separator + "file1.txt"));
        assertTrue(FileUtil.doesPathExist(inputPath + File.separator + "def" + File.separator + "file2"));
        assertTrue(FileUtil.doesPathExist(inputPath + File.separator +  "ghi" + File.separator + "jkl" + File.separator + "empty"));
        assertTrue(FileUtil.doesPathExist(inputPath + File.separator + "weather"));
        assertEquals("hello world", FileUtil.getFileContent(unzipPath + File.separator + "all" + File.separator + "abc" + File.separator + "file1.txt"));
        assertEquals("hello again", FileUtil.getFileContent(inputPath + File.separator + "def" + File.separator + "file2"));
    }

    @Test
    public void failZipping() {
        try {
            FileUtil.zipFiles(tempFolder + File.separator + "foo", tempFolder);
            fail("it can't work");
        } catch(IOException ioEx) {
            assertTrue(ioEx.getMessage().contains("an error occurred while zipping files"));
        }
    }

    @Test
    public void failUnzipping() {
        try {
            FileUtil.unzipFiles(tempFolder + File.separator + "foo", tempFolder);
            fail("it can't work");
        } catch(IOException ioEx) {
            assertTrue(ioEx.getMessage().contains("an error occurred while unzipping files"));
        }
    }

    @Test
    public void getExtension() {
        assertEquals("jpg", FileUtil.getExtension("pix.jpg"));
        assertEquals("jpg", FileUtil.getExtension("/tmp/pix.jpg"));
        assertEquals("png", FileUtil.getExtension("pix.png"));
        assertEquals("avi", FileUtil.getExtension("video.avi"));
        assertEquals("mkv", FileUtil.getExtension("/tmp/video.mkv"));
        assertEquals("zip", FileUtil.getExtension("archive.foo.zip"));
        assertNull(FileUtil.getExtension("foobar"));
    }

    @Test
    public void cloneFiles() throws IOException {
        String inputPath = tempFolder + File.separator + "cloning";
        FileUtil.createDirectory(inputPath);
        FileUtil.create(inputPath + File.separator + "hello.txt","hello world");
        FileUtil.create(inputPath + File.separator + "again","hello again");

        FileUtil.cloneFiles(inputPath, 2);
        assertTrue(FileUtil.doesPathExist(inputPath + File.separator + "hello.txt"));
        assertTrue(FileUtil.doesPathExist(inputPath + File.separator + "hello-clone1.txt"));
        assertTrue(FileUtil.doesPathExist(inputPath + File.separator + "hello-clone2.txt"));
        assertTrue(FileUtil.doesPathExist(inputPath + File.separator + "again"));
        assertTrue(FileUtil.doesPathExist(inputPath + File.separator + "again-clone1"));
        assertTrue(FileUtil.doesPathExist(inputPath + File.separator + "again-clone2"));

        assertEquals("hello world", FileUtil.getFileContent(inputPath + File.separator + "hello.txt"));
        assertEquals("hello world", FileUtil.getFileContent(inputPath + File.separator + "hello-clone1.txt"));
        assertEquals("hello world", FileUtil.getFileContent(inputPath + File.separator + "hello-clone2.txt"));
        assertEquals("hello again", FileUtil.getFileContent(inputPath + File.separator + "again"));
        assertEquals("hello again", FileUtil.getFileContent(inputPath + File.separator + "again-clone1"));
        assertEquals("hello again", FileUtil.getFileContent(inputPath + File.separator + "again-clone2"));
    }

    @Test
    public void createReadWithByteContent() throws Exception {
        FileUtil.create(tempDir.toString() + File.separator + "hello", "hello world".getBytes(StandardCharsets.UTF_8));
        assertTrue(FileUtil.doesPathExist(tempDir.toString() + File.separator + "hello"));

        byte[] bytes = FileUtil.getByteContent(tempDir.toString() + File.separator + "hello");
        assertEquals("hello world", new String(bytes));
    }

    @Test
    public void createReadWithStringContent() throws Exception {
        String filePath = tempDir.toString() + File.separator + "helloagain";
        FileUtil.create(filePath, "hello world");
        assertTrue(FileUtil.doesPathExist(filePath));

        String content = FileUtil.getFileContent(filePath);
        assertEquals("hello world", content);
    }

    @Test
    public void createDeleteFolder() throws IOException {
        String newFolderPath = tempDir.toString() + File.separator + "myfolder";
        FileUtil.createDirectory(newFolderPath);
        assertTrue(FileUtil.doesPathExist(newFolderPath));
        FileUtil.delete(newFolderPath);
        assertFalse(FileUtil.doesPathExist(newFolderPath));
    }

    @Test
    public void createApplicationTempDirectory() throws IOException {
        doReturn("1.0.0").when(buildProperties).getVersion();
        doReturn("file-util").when(buildProperties).getArtifact();
        String tempFolder = FileUtil.createApplicationTempDirectory(buildProperties);
        String expected = FileUtil.getTempDirectory() + File.separator
                + buildProperties.getArtifact() + "-" + buildProperties.getVersion();
        assertTrue(FileUtil.doesPathExist(tempFolder));
        assertEquals(expected, tempFolder);
        FileUtil.delete(tempFolder);
        assertFalse(FileUtil.doesPathExist(tempFolder));
    }

    @Test
    public void clearFolder() {
        try {
            String dirPath = tempDir.toString() + File.separator + "sandbox";
            FileUtil.createDirectory(dirPath);

            String file1Path = dirPath + File.separator + "hello";
            FileUtil.create(file1Path, "hello world");
            assertTrue(FileUtil.doesPathExist(file1Path));

            String file2Path = dirPath + File.separator + "world";
            FileUtil.create(file2Path, "hello again");
            assertTrue(FileUtil.doesPathExist(file2Path));

            FileUtil.clearDirectory(dirPath);
            assertFalse(FileUtil.doesPathExist(file1Path));
            assertFalse(FileUtil.doesPathExist(file2Path));
            assertFalse(FileUtil.doesPathExist(dirPath));

        } catch(Exception ex) {
            fail(ex.getMessage());
        }
    }

}