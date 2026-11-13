package edu.misc;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SandboxUtil.class})
public class MockStaticInstantTest {
    private MockedStatic<Instant> mockInstant;
    private MockedStatic<SandboxUtil> mockSandboxUtil;

    @BeforeEach
    public void setup() {
        this.mockInstant = Mockito.mockStatic(Instant.class, Mockito.CALLS_REAL_METHODS);
        LocalDateTime localDateTime = LocalDateTime.of(2022, Month.MAY, 1, 11, 15);
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        mockInstant.when(Instant::now).thenReturn(instant);

        this.mockSandboxUtil = Mockito.mockStatic(SandboxUtil.class, Mockito.CALLS_REAL_METHODS);
        mockSandboxUtil.when(() -> SandboxUtil.getLabel(anyLong())).thenReturn("foobar");
    }

    @AfterEach
    public void tearDown() {
        mockInstant.close();
        mockSandboxUtil.close();
    }

    @Test
    public void checkInstant() {
        Instant now = Instant.now();
        assertNotNull(now);

        ZoneId zoneId = ZoneId.systemDefault(); // Use the system default time zone
        LocalDateTime localDateTime = now.atZone(zoneId).toLocalDateTime();
        assertEquals(2022, localDateTime.getYear());
        assertEquals(5, localDateTime.getMonthValue());
        assertEquals(1, localDateTime.getDayOfMonth());
    }

    @Test
    public void checkUtilityClass() {
        String result = SandboxUtil.getLabel(8);
        assertEquals("foobar", result);
    }
}
