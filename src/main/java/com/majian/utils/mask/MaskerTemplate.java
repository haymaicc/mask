package com.majian.utils.mask;

import com.google.common.base.Strings;
import com.majian.utils.mask.util.MaskException;
import com.majian.utils.mask.util.MaskHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by majian on 2017/12/13.
 */
public abstract class MaskerTemplate implements Masker {
    private static final Logger log = LoggerFactory.getLogger(MaskerTemplate.class);
    @Override
    public String mask(String content) {
        if (Strings.isNullOrEmpty(content)) {
            return content;
        }
        try {
            return doMask(content);
        } catch (MaskException e) {
            log.error("",e);
            return MaskHelper.maskAll(content);
        }
    }

    protected abstract String doMask(String content);
}
