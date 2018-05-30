package com.majian.utils.mask.configuration;

import com.majian.utils.mask.masker.IdCardMasker;
import com.majian.utils.mask.masker.MobileMasker;
import com.majian.utils.mask.MaskAdvice;
import com.majian.utils.mask.masker.BankAccountMasker;
import com.majian.utils.mask.masker.NameMasker;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by majian on 2017/12/13.
 */
@Configuration
public class MasterConfiguration {
    @Bean
    @ConditionalOnMissingBean(IdCardMasker.class)
    public IdCardMasker idCardMasker() {
        return new IdCardMasker();
    }
    @Bean
    @ConditionalOnMissingBean(MobileMasker.class)
    public MobileMasker mobileMasker() {
        return new MobileMasker();
    }
    @Bean
    @ConditionalOnMissingBean(BankAccountMasker.class)
    public BankAccountMasker bankAccountMasker() {
        return new BankAccountMasker();
    }
    @Bean
    @ConditionalOnMissingBean(NameMasker.class)
    public NameMasker nameMasker() {
        return new NameMasker();
    }
    @Bean
    @ConditionalOnMissingBean(MaskAdvice.class)
    public MaskAdvice maskAdvice() {
        return new MaskAdvice();
    }
}
