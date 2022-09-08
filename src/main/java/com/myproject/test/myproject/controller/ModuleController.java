package com.myproject.test.myproject.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class ModuleController {
    public void simpleModule(Model model, String module, String message){
        model.addAttribute("module", module);
        model.addAttribute("message",message);
    }

    public void displayModel(Model model, String module, Map<String, Object> data, String message){
        simpleModule(model, module, message);
        model.addAttribute("data", data);
    }

    public void uploadModel(Model model, String module, String formAction, Number id, String message){
        simpleModule(model,module,message);
        model.addAttribute("id", id);
        model.addAttribute("action", formAction);
    }

    public void selectionModel(Model model, String module, List entities, String message){
        simpleModule(model,module,message);
        model.addAttribute("entities", entities);
    }

    public void formModel(Model model, String module, Object formObject, String formAction, String message){
        simpleModule(model,module,message);
        model.addAttribute("entity", formObject);
        model.addAttribute("action", formAction);
    }

    public void multiChoiceFormModule(Model model, String module, Object formObject, String formAction, List multiChhoiceList, String message){
       formModel(model,module,formObject,formAction,message);
       model.addAttribute("mclist", multiChhoiceList);
    }

    public void ngMCFormModel(Model model, String module, Object formObject, String formAction, String multiChoiceList, String message){
        formModel(model, module, formObject, formAction, message);
        model.addAttribute("mclist", multiChoiceList);

    }

    public void ngMCFormModel(Model model, String module, Object formObject, String formAction, String multiChoiceList, String selectedItem, String message){
        formModel(model, module, formObject, formAction, message);
        model.addAttribute("mclist", multiChoiceList);
        model.addAttribute("mcitem", selectedItem);

    }

    public void formAndTableModel(Model model, String module, Object formObject, String formAction,
           List multiChoiceList, Map<String, Object> data, String message){
        multiChoiceFormModule(model,module,formObject,formAction,multiChoiceList,message);
        model.addAttribute("data", data);
    }

    public void pageModel(Model model, HttpServletRequest httpServletRequest,
                          String module, String pageLink, String message){
        String searchParam = httpServletRequest.getParameter("searchParam");
        String sortCriteria= httpServletRequest.getParameter("sortCriteria");
        String sortDirection= httpServletRequest.getParameter("sortDirection");
        int pagesize =9;
        String size = httpServletRequest.getParameter("size");
        if(size != null && !size.isEmpty()){
            pagesize = Integer.parseInt(size);
        }
        simpleModule(model,module,message);
        model.addAttribute("size", pagesize);
        model.addAttribute("pageLink", pageLink);
        model.addAttribute("sortCriteria", sortCriteria);
        model.addAttribute("sortDirection",sortDirection);
    }

    public void pageDataModel(Model model, HttpServletRequest httpServletRequest,
                              String module, String pageLing, Map<String,Object> data, String message){
        pageModel(model,httpServletRequest,module,pageLing,message);
        model.addAttribute("data", data);
    }

    public void galaryModule(Model model, HttpServletRequest httpServletRequest,
                             String module, String pageLink,
                             Page page, String message){
        pageModel(model,httpServletRequest,module,pageLink,message);
        model.addAttribute("photo", page.getContent());
        model.addAttribute("pageIndex", page.getNumber());
        model.addAttribute("totalPage", page.getTotalPages());
        model.addAttribute("totalElement", page.getTotalElements());

    }

    public void dateFilterModel(Model model, HttpServletRequest httpServletRequest,
                                  String module, String pageLink,
                                  Map<String, Object> data, String message){
        pageDataModel(model,httpServletRequest,module,pageLink,data,message);
        String startDate = httpServletRequest.getParameter("startDate");
        String endDate = httpServletRequest.getParameter("endDate");
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
    }

    public void filterPageModel(Model model, HttpServletRequest httpServletRequest,
                                String module, String pageLink,
                                List multiChoiceList,
                                boolean mcIdExists, Map<String, Object> data, String message){
        dateFilterModel(model,httpServletRequest,module,pageLink,data,message);
        String filterText = httpServletRequest.getParameter("filterText");
        model.addAttribute("filterText", filterText);
        model.addAttribute("mclist", multiChoiceList);
        model.addAttribute("mcIdExists", mcIdExists);
    }


}

