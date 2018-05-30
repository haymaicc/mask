package com.majian.utils.mask.masker;

import com.majian.utils.mask.MaskerTemplate;
import com.majian.utils.mask.util.MaskException;
import com.majian.utils.mask.util.MaskHelper;

/**
 * Created by majian on 2017/12/13.
 */
public class IdCardMasker extends MaskerTemplate {

    @Override
    protected String doMask(String content) {
        if (content.length() == 15) {
            return MaskHelper.maskMiddle(content, 7, 12);
        } else if (content.length() == 18) {
            return MaskHelper.maskMiddle(content, 7, 14);
        }
        throw new MaskException("id card not in the right format, id="+content);
    }
}
