package com.majian.utils.mask;

import static junit.framework.Assert.assertEquals;

import com.majian.utils.mask.MaskAdviceTest.MockedApplicationContext;
import com.majian.utils.mask.annotations.BankAccountMask;
import com.majian.utils.mask.annotations.IdCardMask;
import com.majian.utils.mask.annotations.MobileMask;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

/**
 * @author linzt
 * @date 2018/5/3
 */
@Data
class TestObj {

    @MobileMask
    private static String staticWorkPhone = "17768832291";
    @MobileMask
    private String workPhone = "17768832291";

    public static String getStaticWorkPhone() {
        return staticWorkPhone;
    }


}

@Data
class TestObj1 extends TestObj {

    @MobileMask
    private String workPhone1 = "17768832292";

}

@Data
class TestObj2 extends TestObj1 {

    @MobileMask
    private String workPhone2 = "17768832293";

}

@Slf4j
public class MaskExampleTest1 {

    @Data
    class Person {

        private String name = "nini";
        @MobileMask
        protected String mobile = "13777668899";
        @IdCardMask
        private String idCard = "330889199912039981";
        @BankAccountMask
        private String bankAccount = "6226191234063582";
    }

    @Data
    class Employee extends Person {

        @MobileMask
        private String workPhone = "17768832299";

    }

    @Data
    class EmployeeBBB extends Employee {

        @MobileMask
        private String workPhoneBBB = "17768832299";

    }

    private MaskAdvice maskAdvice = new MaskAdvice();

    @Before
    public void setUp() throws Exception {
        maskAdvice.setBeanFactory(new MockedApplicationContext());
        maskAdvice.afterPropertiesSet();
    }

    @Test
    public void testExtend() throws IllegalAccessException {
        Employee employee = new Employee();
        maskAdvice.mask(employee);
        assertEquals(employee.getWorkPhone(), "177****2299");
        assertEquals(employee.getName(), "nini");
        assertEquals(employee.getMobile(), "137****8899");
        assertEquals(employee.getIdCard(), "330889********9981");
        assertEquals(employee.getBankAccount(), "622619****063582");
    }

    @Test
    public void testExtend1() throws IllegalAccessException {
        EmployeeBBB employee = new EmployeeBBB();
        maskAdvice.mask(employee);
        assertEquals(employee.getWorkPhone(), "177****2299");
        assertEquals(employee.getName(), "nini");
        assertEquals(employee.getMobile(), "137****8899");
        assertEquals(employee.getIdCard(), "330889********9981");
        assertEquals(employee.getBankAccount(), "622619****063582");
        assertEquals(employee.getWorkPhoneBBB(), "177****2299");
    }

    @Test
    public void testExtend2() throws IllegalAccessException {
        TestObj2 testObj2 = new TestObj2();
        maskAdvice.mask(testObj2);
        assertEquals(testObj2.getWorkPhone(), "177****2291");
        assertEquals(testObj2.getWorkPhone1(), "177****2292");
        assertEquals(testObj2.getWorkPhone2(), "177****2293");
        assertEquals(TestObj2.getStaticWorkPhone(), "177****2291");
    }

}
