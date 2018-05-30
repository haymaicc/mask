package com.majian.utils.mask;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.majian.utils.mask.MaskAdviceTest.MockedApplicationContext;
import com.majian.utils.mask.annotations.BankAccountMask;
import com.majian.utils.mask.annotations.IdCardMask;
import com.majian.utils.mask.annotations.MobileMask;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by linzt on 21/12/2017.
 */
@Slf4j
public class MaskExampleTest {

    @Data
    class User {

        private String name = "nini";
        @MobileMask
        private String mobile = "13777668899";
        @IdCardMask
        private String idCard = "330889199912039981";
        @BankAccountMask
        private String bankAccount = "6226191234063582";
    }

    @Data
    class User1 {

        private String name = "nini";
        @MobileMask
        private String mobile = "13777668899";
        @IdCardMask
        private String idCard = "330889199912039981";
        @BankAccountMask
        private String bankAccount = "6226191234063582";

        private User user = new User();
    }

    @Data
    class User2 {

        private List<User> users = Arrays.asList(new User());
        private List<User1> user1s = Arrays.asList(new User1());
    }

    @Data
    class User3 {

        private String name = "nini";
        ;
        @MobileMask
        private String mobile = "13777668899";
        ;
        @IdCardMask
        private String idCard = "330889199912039981";
        @BankAccountMask
        private String bankAccount = "6226191234063582";

        private User user = new User();
        private User1 user1 = new User1();
        private User2 user2 = new User2();
    }

    public String simpleString() {
        return "17826811389";
    }

    public User simpleObj() {
        return new User();
    }

    public List<User1> listObject() {
        return Arrays.asList(new User1());
    }

    public Set<User1> setObject() {
        return Sets.newHashSet(new User1());
    }

    public Map<String, User1> mapObject() {
        Map<String, User1> map = Maps.newHashMap();
        map.put("test", new User1());
        return map;
    }


    public List<List<User1>> complexList() {
        return Arrays.asList(Arrays.asList(new User1()));
    }

    public User1 complexObj() {
        return new User1();
    }


    public User2 complexEg1() {
        return new User2();
    }

    public User3 complexEg2() {
        return new User3();
    }

    public List<List<User3>> complexEg3() {
        User3 user3 = complexEg2();
        return Arrays.asList(Arrays.asList(user3));
    }

    public List<Map<String, List>> complexEg4() {
        Map<String, List> map = Maps.newHashMap();
        map.put("test", complexEg3());
        return Arrays.asList(map);
    }

    public Map<String, User3> nullMap() {
        Map<String, User3> map = Maps.newHashMap();
        map.put("test", new User3());
        map.put("test1", null);
        map.put(null, null);
        return map;
    }

    public List<User3> nullList() {
        List<User3> list = Lists.newArrayList();
        list.add(new User3());
        list.add(null);
        return list;
    }

    public List<User3> emptyList() {
        return Lists.newArrayList();
    }

    public Map emptyMap() {
        return Maps.newHashMap();
    }


    private MaskAdvice maskAdvice = new MaskAdvice();

    @Before
    public void setUp() throws Exception {
        maskAdvice.setBeanFactory(new MockedApplicationContext());
        maskAdvice.afterPropertiesSet();
    }

    @Test
    public void testSimpleString() {
    }

    private void checkUser(User user) {
        assertEquals("nini", user.getName());
        assertEquals("137****8899", user.getMobile());
        assertEquals("330889********9981", user.getIdCard());
        assertEquals("622619****063582", user.getBankAccount());
    }

    private void checkUser1(User1 user1) {
        assertEquals("nini", user1.getName());
        assertEquals("137****8899", user1.getMobile());
        assertEquals("330889********9981", user1.getIdCard());
        assertEquals("622619****063582", user1.getBankAccount());
        checkUser(user1.user);
    }

    private void checkUser2(User2 user2) {
        User user = user2.getUsers().get(0);
        User1 user1 = user2.getUser1s().get(0);
        checkUser(user);
        checkUser1(user1);
    }

    private void checkUser3(User3 user3) {
        assertEquals("nini", user3.getName());
        assertEquals("137****8899", user3.getMobile());
        assertEquals("330889********9981", user3.getIdCard());
        assertEquals("622619****063582", user3.getBankAccount());
        checkUser(user3.user);
        checkUser1(user3.user1);
        checkUser2(user3.user2);
    }

    @Test
    public void testSimpleObj() throws IllegalAccessException {
        User user = simpleObj();
        maskAdvice.mask(user);
        checkUser(user);
    }

    @Test
    public void testListObject() throws IllegalAccessException {
        List<User1> list = listObject();
        maskAdvice.mask(list);
        checkUser1(list.get(0));
    }

    @Test
    public void testSetObj() throws IllegalAccessException {
        Set<User1> set = setObject();
        maskAdvice.mask(set);
        User1 user1 = set.iterator().next();
        checkUser1(user1);
    }

    @Test
    public void testMapObj() throws IllegalAccessException {
        Map<String, User1> map = mapObject();
        maskAdvice.mask(map);
        User1 user1 = map.get("test");
        checkUser1(user1);
    }

    @Test
    public void testComplexList() throws IllegalAccessException {
        List<List<User1>> llist = complexList();
        maskAdvice.mask(llist);
        User1 user1 = llist.get(0).get(0);
        checkUser1(user1);
    }

    @Test
    public void testComplexObj() throws IllegalAccessException {
        User1 user1 = complexObj();
        maskAdvice.mask(user1);
        checkUser1(user1);
    }

    @Test
    public void testComplexEg1() throws IllegalAccessException {
        User2 user2 = complexEg1();
        maskAdvice.mask(user2);
        checkUser2(user2);
    }

    @Test
    public void testComplexEg2() throws IllegalAccessException {
        User3 user3 = complexEg2();
        maskAdvice.mask(user3);
        checkUser3(user3);
    }

    @Test
    public void testComplexEg3() throws IllegalAccessException {
        List<List<User3>> complex = complexEg3();
        maskAdvice.mask(complex);
    }

//    @Test
    public void testComplexEg4() throws IllegalAccessException {
        Object complex = complexEg4();
        maskAdvice.mask(complex);
    }


    @Test
    public void testNullMap() throws IllegalAccessException {
        Object complex = nullMap();
        maskAdvice.mask(complex);
    }

    @Test
    public void testNullList() throws IllegalAccessException {
        List<User3> complex = nullList();
        maskAdvice.mask(complex);
        checkUser3(complex.get(0));
        assertNull(complex.get(1));
    }

    @Test
    public void testEmptyList() throws IllegalAccessException {
        Object list = emptyList();
        maskAdvice.mask(list);

    }

    @Test
    public void testEmptyMap() throws IllegalAccessException {
        Object map = emptyMap();
        try {
            maskAdvice.mask(map);
        } catch (Exception e) {
            assert false;
            return;
        }
        assert true;
    }


    @Data
    class User4 {

        private String name;
        @MobileMask
        private String mobile = "178";
        @IdCardMask
        private String idCard = "3308891999";
        @BankAccountMask
        private String bankAccount = "622619";
    }

    public User4 errorMask() {
        return new User4();
    }


    @Test
    public void testErrorMask() throws IllegalAccessException {
        Object obj = errorMask();
        maskAdvice.mask(obj);
    }

    @Data
    public class ApiResponse<T> {

        public static final String SUCCESS = "0"; //成功
        public static final String FAILED = "-1"; //失败

        private int code;
        private String msg;
        private T result;

        public ApiResponse(int code, String msg, T result) {
            this.code = code;
            this.msg = msg;
            this.result = result;
        }
    }

    enum GenderEnum {
        MALE(0, "male"),
        FEMALE(1, "female");
        int code;
        String msg;

        GenderEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    @Data
    class AccountInfoDTO {

        private Long accountId = 123123123l;
        private String userId = "adasdasdasd";
        private String realName = "test";
        @IdCardMask
        private String idCard = "44432119920812121x";
        @BankAccountMask
        private String defaultBankCardNo = "622619123123123123";
        private String bankName = "工行";
        @MobileMask
        private String mobile = "17826813188";
        private GenderEnum gender = GenderEnum.FEMALE;
        private Date birthDay = new Date();
        private int age = 10;
        private List<BankCardDTO> bankCardDTOList;
    }

    @Data
    public class BankCardDTO {

        private Integer cardType = 0;
        private String cardTypeStr = "123123";
        private String bankName = "23123123";
        @BankAccountMask
        private String bankCardNo = "4123123121231231";
        @MobileMask
        private String mobile = "1298319283912";
        private boolean isDefault = true;
        private Integer status = 0;
        private Integer lockStatus = 0;
        private Date createTime = new Date();
        private Date updateTime = new Date();

    }


    public ApiResponse<List<AccountInfoDTO>> errorLevelMask() {
        AccountInfoDTO dto = new AccountInfoDTO();
        BankCardDTO bdto = new BankCardDTO();
        bdto.setBankCardNo("6345234124123123123");
        dto.setBankCardDTOList(Arrays.asList(bdto));
        return new ApiResponse(0, "", Arrays.asList(dto));
    }

    @Test
    public void testErrorLevelMask() throws IllegalAccessException {
        Object obj = errorLevelMask();
        maskAdvice.mask(obj);
    }


}
