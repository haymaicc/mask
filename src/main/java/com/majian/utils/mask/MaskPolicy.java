package com.majian.utils.mask;

/**
 * Created by majian on 2017/12/13.
 * 脱敏权限策略，主要用来判断当前用户是否需要脱敏；
 * 实现中当前的用户信息可以从@RequestScope bean 中获取(eg. HttpServletRequest)
 */
public interface MaskPolicy{
    boolean needMask();
}
