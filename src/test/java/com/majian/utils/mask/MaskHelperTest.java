package com.majian.utils.mask;

import static org.junit.Assert.assertEquals;

import com.majian.utils.mask.util.MaskHelper;
import org.junit.Test;

/**
 * Created by majian on 2017/12/13.
 */
public class MaskHelperTest {

    @Test
    public void maskMiddle() throws Exception {
        String actual = MaskHelper.maskMiddle("13734459687", 4, 7);
        assertEquals("137****9687", actual);
    }

    @Test
    public void maskLeft() throws Exception {
        String actual = MaskHelper.maskLeft("倪淼磊", 2);
        assertEquals("**磊",actual);
    }

    @Test
    public void maskRight() throws Exception {
        String actual = MaskHelper.maskRight("倪淼磊", 2);
        assertEquals("倪**", actual);
    }

    @Test
    public void maskPad() throws Exception {
        String actual = MaskHelper.maskPad("13734459687", 3, 4);
        assertEquals("***3445****", actual);

    }

    @Test
    public void maskAll() throws Exception {
        String actual = MaskHelper.maskAll("1373333");
        assertEquals("*******", actual);

    }
}