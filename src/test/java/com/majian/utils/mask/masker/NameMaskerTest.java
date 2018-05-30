package com.majian.utils.mask.masker;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by majian on 2017/12/19.
 */
public class NameMaskerTest {

    private NameMasker nameMasker = new NameMasker();
    @Test
    public void maskNull() throws Exception {
        String actual = nameMasker.mask(null);
        Assert.assertNull(actual);
    }

    @Test
    public void maskEmpty() throws Exception {
        String actual = nameMasker.mask("");
        Assert.assertTrue(actual.isEmpty());
    }

    @Test
    public void maskEnglish() throws Exception {
        String actual = nameMasker.doMask("aab");
        Assert.assertEquals("aa*", actual);

    }

    @Test
    public void maskChinese() throws Exception {
        String actual = nameMasker.doMask("大大洪");
        Assert.assertEquals("大大*", actual);

    }

    @Test
    public void maskDigital() throws Exception {
        String actual = nameMasker.doMask("111");
        Assert.assertEquals("11*", actual);

    }

    @Test
    public void maskMix() throws Exception {
        String actual = nameMasker.doMask("大111");
        Assert.assertEquals("大11*", actual);

    }
}