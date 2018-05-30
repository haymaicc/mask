package com.majian.utils.mask.masker;

import com.majian.utils.mask.MaskerTemplate;
import com.majian.utils.mask.util.MaskHelper;

/**
 * Created by majian on 2017/12/13.
 */
public class MobileMasker extends MaskerTemplate {

    @Override
    protected String doMask(String content) {
        return MaskHelper.maskMiddle(content, 4, 7);
    }
}
