package com.majian.utils.mask;


import com.majian.utils.mask.annotations.Mask;
import com.majian.utils.mask.util.CommonConstants;
import com.majian.utils.mask.util.MaskException;
import com.majian.utils.mask.util.RecursiveTooDeepException;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.regex.Pattern;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.AnnotatedElementUtils;

/**
 * Created by majian on 2017/12/13. usage:
 *
 * @Configuration
 * @Import(MasterConfiguration.class) public class MaskConfig { }
 */
@Aspect
public class MaskAdvice implements BeanFactoryAware, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(MaskAdvice.class);

    private BeanFactory beanFactory;
    private MaskPolicy maskPolicy;

    @AfterReturning(pointcut = "@annotation(com.majian.utils.mask.annotations.UseMask)", returning = "obj")
    public void mask(Object obj) throws IllegalAccessException {
        if (!maskPolicy.needMask()) {
            return;
        }
        try {
            processMask(obj, 0);
        } catch (IllegalAccessException | MaskException e) {
            LOGGER.error("脱敏失败", e);
        }
    }

    private boolean isNeedExit(int level) {
        return level >= CommonConstants.maxLevel;
    }

    private boolean isRecursiveSearch(Class aClass) {
        return Pattern.matches(CommonConstants.nameRegular, aClass.getName());
    }

    private Object processBasicType(Masker masker, String obj) {
        return masker.mask(obj);
    }

    private void processCollection(Object obj, int level) throws IllegalAccessException {
        Iterable elements = (Iterable) obj;
        for (Object element : elements) {
            processMask(element, level + 1);
        }
    }

    private void processMap(Object obj, int level) throws IllegalAccessException {
        processCollection(((Map) obj).values(), level + 1);
    }

    private void getRecurAllFields(Class clazz, int level, Set<Field> set) {
        if (isNeedExit(level)) {
            throw new RecursiveTooDeepException("getSupperClass level=" + level + ", class:" + clazz.getName());
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            // 剔除this field
            if (field.getName().contains("this$0")) {
                continue;
            }
            set.add(field);
        }
        if (clazz.getSuperclass() != Object.class && isRecursiveSearch(clazz)) {
            getRecurAllFields(clazz.getSuperclass(), level + 1, set);
        }
    }

    private Set<Field> getAllFields(Class clazz) {
        Set<Field> allFields = new HashSet<>();
        getRecurAllFields(clazz, 0, allFields);
        return allFields;
    }


    private void processObj(Object obj, int level) throws IllegalAccessException {
        for (Field field : getAllFields(obj.getClass())) {
            field.setAccessible(true);
            Object val = field.get(obj);
            if (val == null) {
                continue;
            }
            if (val instanceof String) {
                Mask mergedAnnotation = AnnotatedElementUtils.findMergedAnnotation(field, Mask.class);
                if (mergedAnnotation == null) {
                    continue;
                }
                Masker masker = beanFactory.getBean(mergedAnnotation.value());
                field.set(obj, processBasicType(masker, String.valueOf(val)));
            } else {
                processMask(val, level + 1);
            }
            field.setAccessible(false);
        }
    }

    private void processMask(Object obj, int level) throws IllegalAccessException {
        if (isNeedExit(level)) {
            throw new RecursiveTooDeepException("process Mask level=" + level + ", class:" + obj.getClass().getName());
        }
        if (obj == null || obj instanceof Enum) {
            return;
        }
        if (obj instanceof Iterable) {
            processCollection(obj, level);
        } else if (obj instanceof Map) {
            processMap(obj, level);
        } else if (isRecursiveSearch(obj.getClass())) {
            processObj(obj, level);
        }
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void setMaskPolicy(MaskPolicy maskPolicy) {
        this.maskPolicy = maskPolicy;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        assert beanFactory != null;
        if (maskPolicy == null) {
            maskPolicy = () -> true;
        }
    }
}
