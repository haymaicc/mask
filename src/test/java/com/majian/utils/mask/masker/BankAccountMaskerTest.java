package com.majian.utils.mask.masker;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by majian on 2017/12/13.
 */
public class BankAccountMaskerTest {
    private BankAccountMasker bankAccountMasker = new BankAccountMasker();

    @Test
    public void maskNull() throws Exception {
        String actual = bankAccountMasker.mask(null);
        assertEquals(null, actual);
    }
    @Test
    public void maskEmpty() throws Exception {
        String actual = bankAccountMasker.mask("");
        Assert.assertTrue(actual.isEmpty());
    }
    @Test
    public void mask() throws Exception {
        String actual = bankAccountMasker.mask("6226191234063582");
        assertEquals("622619****063582", actual);
    }

}