package edu.file;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.CREATE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApacheFileUtilsTest {

    @TempDir
    Path tempDir;

    private String tempFolder;

    /**
     * This tested package is part of spring boot libraries.
     * @throws IOException any IO exception
     */
    @Test
    public void basic() throws IOException {
        tempFolder = tempDir.toString() + File.separator + "apache-file-utils";
        FileUtils.forceMkdir(new File(tempFolder));
        Files.write(Paths.get(tempFolder + File.separator + "foo.json"),"{\"foo\":\"bar\"}".getBytes(UTF_8),CREATE);
        assertTrue(FileUtil.doesPathExist(tempFolder + File.separator + "foo.json"));
        FileUtils.cleanDirectory(new File(tempFolder));
        assertFalse(FileUtil.doesPathExist(tempFolder + File.separator + "foo.json"));
        assertTrue(FileUtil.doesPathExist(tempFolder));
        FileUtils.deleteDirectory(new File(tempFolder));
        assertFalse(FileUtil.doesPathExist(tempFolder));
    }
}
