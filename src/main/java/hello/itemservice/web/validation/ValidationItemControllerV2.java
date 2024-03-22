package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.web.ItemValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/validation/v2/items")
@RequiredArgsConstructor
@Slf4j
public class ValidationItemControllerV2 {

    private final ItemRepository itemRepository;

    private final ItemValidator itemValidator;

//    검증기에 대한 어노테이션으로 사용한 컨트롤러에서만 사용가능
//    해당 컨트롤러의 어떤 메서드가 실행되더라도 선행으로 init의 검증기가 작동하게 됨
    @InitBinder
    public void init (WebDataBinder dataBinder){
        log.info("init binder {} " , dataBinder);
        dataBinder.addValidators(itemValidator);
    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v2/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v2/addForm";
    }

//    @PostMapping("/add")
//    public String addItemV1(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
//        Item savedItem = itemRepository.save(item);
//        if(!StringUtils.hasText(item.getItemName())){
//            bindingResult.addError(new FieldError("item", "itemName","상품이름은 필수입니다."));
//        }
//        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000){
//            bindingResult.addError(new FieldError("item", "price","가격은 1,000원이상 1,000,000이하입니다."));
//        }
//        if(item.getQuantity() == null || item.getQuantity() >= 9999){
//            bindingResult.addError(new FieldError("item", "quantity","수량은 최대 9,999까지 허용합니다. 현재수량= " +item.getQuantity()));
//        }
//        if(item.getPrice() != null && item.getQuantity() != null){
//            int resultPrice = item.getPrice() * item.getQuantity();
//            if(resultPrice < 10000){
//                bindingResult.addError(new ObjectError("item", "가격 + 수량의 합은 10,000원 이상이어야 합니다"));
//            }
//        }
////        에러가 존재하면?
//        if(bindingResult.hasErrors()){
//            log.info("error = {}", bindingResult);
//            return "validation/v2/addForm";
//        }
//        redirectAttributes.addAttribute("itemId", savedItem.getId());
//        redirectAttributes.addAttribute("status", true);
//        return "redirect:/validation/v2/items/{itemId}";
//    }


//    @PostMapping("/add")
//    public String addItemV2(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
//        Item savedItem = itemRepository.save(item);
////        기존에는 만일 값을 잘못입력시 그값이 보존되지 않았지만 다른생성자를 활용하여 만일 값이없을경우 item.get으로 해당 값을 보존시킨다.
//        if(!StringUtils.hasText(item.getItemName())){
//            bindingResult.addError(new FieldError("item", "itemName",item.getItemName(), false, null,null,"상품이름은 필수입니다."));
//        }
//        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000){
//            bindingResult.addError(new FieldError("item", "price",item.getPrice(),false,null,null,"가격은 1,000원이상 1,000,000이하입니다."));
//        }
//        if(item.getQuantity() == null || item.getQuantity() >= 9999){
//            bindingResult.addError(new FieldError("item", "quantity",item.getQuantity(),false,null,null,"수량은 최대 9,999까지 허용합니다. 현재수량= " +item.getQuantity()));
//        }
//        if(item.getPrice() != null && item.getQuantity() != null){
//            int resultPrice = item.getPrice() * item.getQuantity();
//            if(resultPrice < 10000){
//                bindingResult.addError(new ObjectError("item",null,null, "가격 + 수량의 합은 10,000원 이상이어야 합니다"));
//            }
//        }
////        에러가 존재하면?
//        if(bindingResult.hasErrors()){
//            log.info("error = {}", bindingResult);
//            return "validation/v2/addForm";
//        }
//        redirectAttributes.addAttribute("itemId", savedItem.getId());
//        redirectAttributes.addAttribute("status", true);
//        return "redirect:/validation/v2/items/{itemId}";
//    }
//

//    @PostMapping("/add")
//    public String addItemV3(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
//        Item savedItem = itemRepository.save(item);
////        기존에는 만일 값을 잘못입력시 그값이 보존되지 않았지만 다른생성자를 활용하여 만일 값이없을경우 item.get으로 해당 값을 보존시킨다.
//        if(!StringUtils.hasText(item.getItemName())){
//            bindingResult.addError(new FieldError("item", "itemName",item.getItemName(), false, new String[] {"required.item.itemName"},null,null));
//        }
//        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000){
//            bindingResult.addError(new FieldError("item", "price",item.getPrice(),false,new String[] {"range.item.price"},new Object[]{1000,1000000},null));
//        }
//        if(item.getQuantity() == null || item.getQuantity() >= 9999){
//            bindingResult.addError(new FieldError("item", "quantity",item.getQuantity(),false,new String[] {"max.item.quantity"},new Object[]{9999},null));
//        }
//        if(item.getPrice() != null && item.getQuantity() != null){
//            int resultPrice = item.getPrice() * item.getQuantity();
//            if(resultPrice < 10000){
//                bindingResult.addError(new ObjectError("item",new String[]{"totalPriceMin"},new Object[]{10000, resultPrice}, null));
//            }
//        }
////        에러가 존재하면?
//        if(bindingResult.hasErrors()){
//            log.info("error = {}", bindingResult);
//            return "validation/v2/addForm";
//        }
//        redirectAttributes.addAttribute("itemId", savedItem.getId());
//        redirectAttributes.addAttribute("status", true);
//        return "redirect:/validation/v2/items/{itemId}";
//    }


//    @PostMapping("/add")
//    public String addItemV4(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
//        Item savedItem = itemRepository.save(item);
////        supports를 통해 Item들어온 객체가 맞는지 확인후 validate를 통해 검증처리를 진행
//        if(itemValidator.supports(Item.class)){
//            itemValidator.validate(item,bindingResult);
//        }
//
//        if(bindingResult.hasErrors()){
//            log.info("error = {}", bindingResult);
//            return "validation/v2/addForm";
//        }
//        redirectAttributes.addAttribute("itemId", savedItem.getId());
//        redirectAttributes.addAttribute("status", true);
//        return "redirect:/validation/v2/items/{itemId}";
//    }

    @PostMapping("/add")
    public String addItemV5(@Validated @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        Item savedItem = itemRepository.save(item);

        if(bindingResult.hasErrors()){
            log.info("error = {}", bindingResult);
            return "validation/v2/addForm";
        }
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v2/items/{itemId}";
    }

}

