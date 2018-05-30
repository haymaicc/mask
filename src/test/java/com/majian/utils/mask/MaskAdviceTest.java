package com.majian.utils.mask;

import static org.junit.Assert.assertEquals;

import com.google.common.collect.Lists;
import com.majian.utils.mask.annotations.BankAccountMask;
import com.majian.utils.mask.annotations.IdCardMask;
import com.majian.utils.mask.annotations.MobileMask;
import com.majian.utils.mask.util.RecursiveTooDeepException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * Created by majian on 2017/12/13.
 */
public class MaskAdviceTest {

    private MaskAdvice maskAdvice = new MaskAdvice();

    @Before
    public void setUp() throws Exception {
        maskAdvice.setBeanFactory(new MockedApplicationContext());
        maskAdvice.afterPropertiesSet();
    }

    @Test
    public void maskSingle() throws Exception {
        User retValue = new User();
        maskAdvice.mask(retValue);
        assertEquals("nini", retValue.getName());
        assertEquals("137****8899", retValue.getMobile());
        assertEquals("330889********9981", retValue.getIdCard());
        assertEquals("622619****063582", retValue.getBankAccount());

        assertEquals("nini", retValue.getUser1().getName());
        assertEquals("137****8899", retValue.getUser1().getMobile());
        assertEquals("330889********9981", retValue.getUser1().getIdCard());
        assertEquals("622619****063582", retValue.getUser1().getBankAccount());

        assertEquals("nini", retValue.getUser1s().get(0).getName());
        assertEquals("137****8899", retValue.getUser1s().get(0).getMobile());
        assertEquals("330889********9981", retValue.getUser1s().get(0).getIdCard());
        assertEquals("622619****063582", retValue.getUser1s().get(0).getBankAccount());

    }

    @Test
    public void maskSingleWithNullField() throws Exception {
        User retValue = new User();
        retValue.setMobile(null);
        maskAdvice.mask(retValue);
        assertEquals("nini", retValue.getName());
        assertEquals(null, retValue.getMobile());
        assertEquals("330889********9981", retValue.getIdCard());
        assertEquals("622619****063582", retValue.getBankAccount());

    }
    @Test
    public void maskNull() throws Exception {
        try {
            maskAdvice.mask(null);
        } catch (IllegalAccessException e) {
            Assert.fail();
        }

    }

    @Test
    public void maskList() throws Exception {
        User user = new User();
        List<User> retValue = Lists.newArrayList(user);
        maskAdvice.mask(retValue);
        User actual = retValue.get(0);
        assertEquals("nini", actual.getName());
        assertEquals("137****8899", actual.getMobile());
        assertEquals("330889********9981", actual.getIdCard());
        assertEquals("622619****063582", actual.getBankAccount());


    }

    @Test(expected = RecursiveTooDeepException.class)
    public void maskRecursiveTooDeep() throws Exception {
        RecursiveDemo demo = new RecursiveDemo();
        demo.add(demo);
        maskAdvice.mask(demo);
    }

    @Test
    public void setMaskPolicy() throws Exception {
        maskAdvice.setMaskPolicy(new MaskPolicy() {
            @Override
            public boolean needMask() {
                return false;
            }
        });

        User retValue = new User();
        maskAdvice.mask(retValue);
        assertEquals("nini", retValue.getName());
        assertEquals("13777668899", retValue.getMobile());
        assertEquals("330889199912039981", retValue.getIdCard());
        assertEquals("6226191234063582", retValue.getBankAccount());

    }

    public static class MockedApplicationContext extends DefaultListableBeanFactory {

        @Override
        public <T> T getBean(Class<T> aClass) throws BeansException {
            try {
                return aClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    public class RecursiveDemo {
        private List<RecursiveDemo> demos = new ArrayList<>();
        public void add(RecursiveDemo demo) {
            demos.add(demo);
        }

    }
    public class User {

        private String name = "nini";
        @MobileMask
        private String mobile = "13777668899";
        @IdCardMask
        private String idCard = "330889199912039981";
        @BankAccountMask
        private String bankAccount = "6226191234063582";

        private User1 user1 = new User1();

        private List<User1> user1s = Arrays.asList(new User1());

        public List<User1> getUser1s() {
            return user1s;
        }

        public User1 getUser1() {
            return user1;
        }

        public String getName() {
            return name;
        }

        public String getMobile() {
            return mobile;
        }

        public String getIdCard() {
            return idCard;
        }

        public String getBankAccount() {
            return bankAccount;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }


    public class User1 {

        private String name = "nini";
        @MobileMask
        private String mobile = "13777668899";
        @IdCardMask
        private String idCard = "330889199912039981";
        @BankAccountMask
        private String bankAccount = "6226191234063582";

        public String getName() {
            return name;
        }

        public String getMobile() {
            return mobile;
        }

        public String getIdCard() {
            return idCard;
        }

        public String getBankAccount() {
            return bankAccount;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }

}