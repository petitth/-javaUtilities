package edu.thread;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ThreadTest {

    @Test
    public void threadA() throws InterruptedException {
        DummyThread dummyThread = new DummyThread("foo");
        assertEquals("foo", dummyThread.getName());
        dummyThread.start();
        assertTrue(dummyThread.isAlive());
        dummyThread.join();
        assertFalse(dummyThread.isAlive());
    }

    @Test
    public void threadB() throws InterruptedException {
        Thread thread = new Thread("bar") {
            public void run(){
                System.out.println("'" + getName() + "' is running");
            }
        };
        assertEquals("bar", thread.getName());

        thread.start();
        assertTrue(thread.isAlive());
        thread.join();
        assertFalse(thread.isAlive());
    }
}

class DummyThread extends Thread {
    private static final Logger LOG = LoggerFactory.getLogger(DummyThread.class);

    public DummyThread(String name) {
        setName(name);
    }

    public void run() {
        LOG.debug("Thread '{}' is running", this.getName());
    }
}