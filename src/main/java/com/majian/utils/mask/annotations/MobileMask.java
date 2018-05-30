package com.majian.utils.mask.annotations;

import com.majian.utils.mask.masker.MobileMasker;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by majian on 2017/12/13.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Mask(MobileMasker.class)
public @interface MobileMask {

}
