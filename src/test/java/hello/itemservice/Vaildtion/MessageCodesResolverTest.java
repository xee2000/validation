package hello.itemservice.Vaildtion;

import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;

import static org.assertj.core.api.Assertions.assertThat;

class MessageCodesResolverTest {
    DefaultMessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

    @Test
    void messageCodesResolverObject() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item");
        for (String messageCode : messageCodes) {
            System.out.println("messageCode = " + messageCode);

        }
    }
    @Test
    void messageCodeResolverField(){
       String[] messageCodes =  codesResolver.resolveMessageCodes("required","item","itemName", String.class);
//        조합기준은 에러코드 + ObjectName + field 즉 타입제외하고 모두다 붙임
//        두번째는 에러코드 + itemName이 붙여짐 즉 required만 붙여도 결과가 나왓던것은 우선순위상 ObjectName과 field를 다찾기때문
       assertThat(messageCodes).containsExactly("required.item.itemName",
               "required.itemName",
               "required.java.lang.String",
               "required");
    }
}