package com.majian.utils.mask.masker;

import com.majian.utils.mask.MaskerTemplate;
import com.majian.utils.mask.util.MaskHelper;

/**
 * Created by majian on 2017/12/19.
 */
public class NameMasker extends MaskerTemplate {

    @Override
    protected String doMask(String content) {
        return MaskHelper.maskRight(content,1);
    }
}
