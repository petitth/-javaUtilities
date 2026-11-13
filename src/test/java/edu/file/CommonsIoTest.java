package edu.file;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.*;

public class CommonsIoTest {

    private static final Logger LOG = LoggerFactory.getLogger(CommonsIoTest.class);

    @TempDir
    Path tempDir;

    private String tempFolder;

    @BeforeEach
    public void setup() throws IOException {
        // create temp folder
        tempFolder = tempDir.toString() + File.separator + "commons-io";
        FileUtils.forceMkdir(new File(tempFolder));
    }

    @Test
    public void basicTest() {
        assertNotNull(FileUtils.getTempDirectoryPath());
        assertNotNull(FileUtils.getUserDirectoryPath());
        LOG.info("temp directory = {}", FileUtils.getTempDirectoryPath());
        LOG.info("user directory = {}", FileUtils.getUserDirectoryPath());
    }

    @Test
    public void copyMoveTest() throws IOException {
        // init
        String springPath = tempFolder + File.separator + "spring";
        String summerPath = tempFolder + File.separator + "summer";
        String autumnPath = tempFolder + File.separator + "autumn";
        String winterPath = tempFolder + File.separator + "winter";
        FileUtils.forceMkdir(new File(springPath));
        FileUtils.forceMkdir(new File(springPath + File.separator + "break"));
        FileUtils.forceMkdir(new File(summerPath));
        FileUtils.forceMkdir(new File(autumnPath));
        FileUtils.forceMkdir(new File(winterPath));
        FileUtils.forceMkdir(new File(winterPath + File.separator + "vacation"));
        FileUtils.writeStringToFile(new File(winterPath + File.separator + "vacation" + File.separator + "sun.txt"), "it is sunny", UTF_8);
        FileUtils.writeStringToFile(new File(springPath + File.separator + "break" + File.separator + "activities.txt"), "rest", UTF_8);
        FileUtils.writeStringToFile(new File(springPath + File.separator + "break" + File.separator + "weather.txt"), "sun", UTF_8);

        //** testing copy + move methods of commons-io **
        FileUtils.copyDirectory(new File(winterPath + File.separator + "vacation"), new File(summerPath + File.separator + "vacation"));
        FileUtils.copyFile(new File(winterPath + File.separator + "vacation" + File.separator + "sun.txt"), new File(springPath + File.separator + "test.txt")); // copy + rename
        FileUtils.moveFile(new File(springPath + File.separator + "break" + File.separator + "weather.txt"), new File(summerPath + File.separator + "weather.txt"));
        FileUtils.moveDirectory(new File(springPath + File.separator + "break"), new File(autumnPath + File.separator + "break"));

        long byteCount = FileUtils.sizeOfDirectory(new File(tempFolder));
        LOG.info("byte count of {} = {}", tempFolder, FileUtils.sizeOfDirectory(new File(tempFolder)));
        LOG.info("display size of {} = {}", tempFolder, byteCount);

        // assert
        assertTrue(new File(summerPath + File.separator + "vacation").exists());
        assertTrue(new File(summerPath + File.separator + "vacation" + File.separator + "sun.txt").exists());
        assertTrue(new File(springPath + File.separator + "test.txt").exists());
        assertTrue(new File(autumnPath + File.separator + "break" + File.separator + "activities.txt").exists());
        assertFalse(new File(springPath + File.separator + "break" + File.separator + "activities.txt").exists());
        assertTrue(new File(summerPath + File.separator + "weather.txt").exists());
        assertNotNull(FileUtils.sizeOfDirectory(new File(tempFolder)));

        //** delete a non empty directory with commons-io **
        FileUtils.deleteDirectory(new File(tempFolder));
    }

    @Test
    public void readFileTest() throws IOException {
        FileUtils.writeStringToFile(new File(tempFolder + File.separator + "hello"),"hello world", UTF_8);
        FileUtils.writeStringToFile(new File(tempFolder + File.separator + "test"),"1 2 3\n4 5 6\n7 8 9", UTF_8);

        assertEquals("hello world", FileUtils.readFileToString(new File(tempFolder + File.separator + "hello"), UTF_8));
        List<String> fileLines =  FileUtils.readLines(new File(tempFolder + File.separator + "test"), UTF_8);
        assertTrue(fileLines.size() == 3);
        assertEquals("1 2 3", fileLines.get(0));

        FileUtils.deleteDirectory(new File(tempFolder));
    }

    @Test
    public void inputStreamToString() throws IOException {
        FileUtils.writeStringToFile(new File(tempFolder + File.separator + "foo"),"bar", UTF_8);
        File file = new File(tempFolder + File.separator + "foo");
        InputStream inputStream = FileUtils.openInputStream(file);

        String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        assertEquals("bar", result);

        FileUtils.deleteDirectory(new File(tempFolder));
    }

    @Test
    public void listAllFiles() throws IOException {
        FileUtils.writeStringToFile(new File(tempFolder + File.separator + "dummy.txt"),"dummy", UTF_8);
        FileUtils.writeStringToFile(new File(tempFolder + File.separator + "dummy.json"),"{\"dummy\":true}", UTF_8);
        FileUtils.writeStringToFile(new File(tempFolder + File.separator + "folder1" + File.separator + "foo.json"),"{\"foo\":\"bar\"}", UTF_8);
        FileUtils.writeStringToFile(new File(tempFolder + File.separator + "folder2" + File.separator + "bar.json"),"{\"bar\":\"foo\"}", UTF_8);

        List<File> allFiles = FileUtils.listFiles(
                new File(tempFolder),
                null,
                true
        ).stream().sorted().collect(Collectors.toList());

        assertEquals(4, allFiles.size());
        assertEquals(new File(tempFolder + File.separator + "dummy.json"), allFiles.get(0));
        assertEquals(new File(tempFolder + File.separator + "dummy.txt"), allFiles.get(1));
        assertEquals(new File(tempFolder + File.separator + "folder1" + File.separator + "foo.json"), allFiles.get(2));
        assertEquals(new File(tempFolder + File.separator + "folder2" + File.separator + "bar.json"), allFiles.get(3));
    }

    @Test
    public void listAllFilesWithRegex() throws IOException {
        FileUtils.writeStringToFile(new File(tempFolder + File.separator + "dummy.txt"),"dummy", UTF_8);
        FileUtils.writeStringToFile(new File(tempFolder + File.separator + "dummy.json"),"{\"dummy\":true}", UTF_8);
        FileUtils.writeStringToFile(new File(tempFolder + File.separator + "folder1" + File.separator + "foo.json"),"{\"foo\":\"bar\"}", UTF_8);
        FileUtils.writeStringToFile(new File(tempFolder + File.separator + "folder2" + File.separator + "bar.json"),"{\"bar\":\"foo\"}", UTF_8);

        List<File> allFiles = FileUtils.listFiles(
                new File(tempFolder),
                new RegexFileFilter("^(.*?)"),
                DirectoryFileFilter.DIRECTORY
        ).stream().sorted().collect(Collectors.toList());

        assertEquals(4, allFiles.size());
        assertEquals(new File(tempFolder + File.separator + "dummy.json"), allFiles.get(0));
        assertEquals(new File(tempFolder + File.separator + "dummy.txt"), allFiles.get(1));
        assertEquals(new File(tempFolder + File.separator + "folder1" + File.separator + "foo.json"), allFiles.get(2));
        assertEquals(new File(tempFolder + File.separator + "folder2" + File.separator + "bar.json"), allFiles.get(3));
    }

    @Test
    public void listFilesWithFilter() throws IOException {
        FileUtils.writeStringToFile(new File(tempFolder + File.separator + "dummy.txt"),"dummy", UTF_8);
        FileUtils.writeStringToFile(new File(tempFolder + File.separator + "dummy.json"),"{\"dummy\":true}", UTF_8);
        FileUtils.writeStringToFile(new File(tempFolder + File.separator + "folder1" + File.separator + "foo.json"),"{\"foo\":\"bar\"}", UTF_8);
        FileUtils.writeStringToFile(new File(tempFolder + File.separator + "folder2" + File.separator + "bar.json"),"{\"bar\":\"foo\"}", UTF_8);

        List<File> fileList = FileUtils.listFiles(
                new File(tempFolder),
                new String[]{"json"},
                true
        ).stream().sorted().collect(Collectors.toList());

        assertEquals(3, fileList.size());
        assertEquals(new File(tempFolder + File.separator + "dummy.json"), fileList.get(0));
        assertEquals(new File(tempFolder + File.separator + "folder1" + File.separator + "foo.json"), fileList.get(1));
        assertEquals(new File(tempFolder + File.separator + "folder2" + File.separator + "bar.json"), fileList.get(2));

        FileUtils.deleteDirectory(new File(tempFolder));
    }

    @Test
    public void listFilesWithRegexFilter() throws IOException {
        FileUtils.writeStringToFile(new File(tempFolder + File.separator + "dummy.txt"),"dummy", UTF_8);
        FileUtils.writeStringToFile(new File(tempFolder + File.separator + "dummy.json"),"{\"dummy\":true}", UTF_8);
        FileUtils.writeStringToFile(new File(tempFolder + File.separator + "folder1" + File.separator + "foo.json"),"{\"foo\":\"bar\"}", UTF_8);
        FileUtils.writeStringToFile(new File(tempFolder + File.separator + "folder2" + File.separator + "bar.json"),"{\"bar\":\"foo\"}", UTF_8);

        List<File> fileList = FileUtils.listFiles(
                new File(tempFolder),
                new RegexFileFilter(".*\\.json$"),
                DirectoryFileFilter.DIRECTORY
        ).stream().sorted().collect(Collectors.toList());

        assertEquals(3, fileList.size());
        assertEquals(new File(tempFolder + File.separator + "dummy.json"), fileList.get(0));
        assertEquals(new File(tempFolder + File.separator + "folder1" + File.separator + "foo.json"), fileList.get(1));
        assertEquals(new File(tempFolder + File.separator + "folder2" + File.separator + "bar.json"), fileList.get(2));

        FileUtils.deleteDirectory(new File(tempFolder));
    }

    @Test
    public void readResourceWithIOUtils() throws IOException {
        byte bytes[] = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("foo"));
        assertNotNull(bytes);
        assertEquals("bar", IOUtils.toString(bytes,"UTF-8"));
    }

    @Test
    public void readFileWithIOUtils() throws IOException {
        FileUtils.writeStringToFile(new File(tempFolder + File.separator + "foo.json"),"{\"foo\":\"bar\"}", UTF_8);

        byte bytes[] = IOUtils.toByteArray(new File(tempFolder + File.separator + "foo.json").toURI());
        assertNotNull(bytes);
        assertEquals("{\"foo\":\"bar\"}", IOUtils.toString(bytes,"UTF-8"));
        FileUtils.deleteDirectory(new File(tempFolder));
    }

}
