package hello.itemservice.Vaildtion;

import hello.itemservice.domain.item.Item;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class BeanValitaion{

    @Test
    void beanvaildation(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator  = factory.getValidator();

        Item item = new Item();
        item.setItemName(" ");
        item.setPrice(0);
        item.setQuantity(100);
        Set<ConstraintViolation<Item>> violations = validator.validate(item);
        for(ConstraintViolation<Item> violation : violations){
            System.out.println("데이터 확인 : " + violation);
            System.out.println("데이터 확인1 : " + violation.getMessage());

        }

    }
}
