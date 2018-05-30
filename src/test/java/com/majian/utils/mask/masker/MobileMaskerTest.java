package com.majian.utils.mask.masker;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by majian on 2017/12/13.
 */
public class MobileMaskerTest {
    private MobileMasker mobileMasker = new MobileMasker();
    @Test
    public void mask() throws Exception {
        String actual = mobileMasker.mask("13777778888");
        assertEquals("137****8888", actual);
    }

    @Test
    public void maskNull() throws Exception {
        String actual = mobileMasker.mask(null);
        assertEquals(null, actual);
    }
    @Test
    public void maskEmpty() throws Exception {
        String actual = mobileMasker.mask("");
        Assert.assertTrue(actual.isEmpty());
    }
    @Test
    public void maskIllegal() throws Exception {
        String actual = mobileMasker.mask("137777");
        assertEquals("******", actual);
    }

}