package edu.enumeration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ActionEnumTest {

    @Test
    public void basic() {
        ActionEnum actionEnum = ActionEnum.find("jump");
        assertNotNull(actionEnum);
        assertEquals(ActionEnum.JUMP, actionEnum);
        assertEquals("jump", actionEnum.getValue());
    }

    @Test
    public void notFound() {
        ActionEnum actionEnum = ActionEnum.find("fly");
        assertNotNull(actionEnum);
        assertEquals(ActionEnum.UNDEFINED, actionEnum);
        assertEquals("", actionEnum.getValue());

        actionEnum = ActionEnum.find(null);
        assertNotNull(actionEnum);
        assertEquals(ActionEnum.UNDEFINED, actionEnum);
    }

    @Test
    public void getArray() {
        String[] actions = ActionEnum.getArray();
        assertTrue(actions.length > 0);
        assertEquals(4, actions.length);
        assertEquals("", actions[0]);
        assertEquals("run", actions[1]);
        assertEquals("jump", actions[2]);
        assertEquals("walk", actions[3]);
    }
}
