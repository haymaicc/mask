package com.majian.utils.mask.masker;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by majian on 2017/12/13.
 */
public class IdCardMaskerTest {
    private IdCardMasker idCardMasker = new IdCardMasker();

    @Test
    public void maskNull() throws Exception {
        String actual = idCardMasker.mask(null);
        assertEquals(null, actual);
    }
    @Test
    public void maskEmpty() throws Exception {
        String actual = idCardMasker.mask("");
        Assert.assertTrue(actual.isEmpty());
    }
    @Test
    public void mask18bit() throws Exception {
        String actual = idCardMasker.mask("33077719891213667X");
        assertEquals("330777********667X", actual);
    }

    @Test
    public void mask15bit() throws Exception {
        String actual = idCardMasker.mask("330777891213667");
        assertEquals("330777******667", actual);
    }

    @Test
    public void mask16bit() throws Exception {
        String actual = idCardMasker.mask("3307778912136679");
        assertEquals("****************", actual);
    }

}