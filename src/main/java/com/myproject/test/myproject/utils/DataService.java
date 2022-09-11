package com.myproject.test.myproject.utils;


import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.data.domain.Page;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class DataService {

    public Map<String, Object> getTableData(Object entity, List list, String... hidden){
        List<String> headers = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();

        if(entity != null){
            headers = getFields(entity, hidden);
            data.put("class", entity.getClass().getSimpleName().toLowerCase());

        }
        List<Map<String, Object>> rows = new ArrayList<>();
        if (list != null){
            int length = headers.size();
            for (Object element : list){
                try {
                    Map<String, Object> map = new HashMap<>();
                    for (int i = 0; i < length; i++){
                        String field = headers.get(i);
                        Object value = PropertyUtils.getSimpleProperty(element, field);
                        map.put(field,value);
                    }
                    rows.add(map);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        data.put("headers", headers);
        data.put("rows", rows);
        return data;
    }

    public Map<String, Object> getViewData(Object entity, String... hidden){
        List<String> keys = new ArrayList<>();
        Map<String,Object> values = new HashMap<>();
        Map<String, Object> data = new HashMap<>();

        if(entity != null){
            keys = getFields(entity, hidden);
            data.put("class", entity.getClass().getSimpleName().toLowerCase());

        }
        int length= keys.size();
        try {
            for (int i = 0; i<length; i++){
                String key = keys.get(i);
                Object value = PropertyUtils.getSimpleProperty(entity, key);
                values.put(key, value);
            }
        }catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e){
            throw new RuntimeException(e);
        }
        String image = null;
        if(keys.contains("photo")){
            image= "photo";
        }else if (keys.contains("logo")){
            image="logo";
        }
        data.put(image, values.remove(image));
        keys.remove(image);
        data.put("keys", keys);
        data.put("values", values);
        return data;
    }

    public Map<String, Object> getViewAndListData(Object entity, List list, String... hidden){
        Map<String, Object> data = getViewData(entity, hidden);
        Map<String, Object> subdata = null;
        if (list != null && !list.isEmpty()){
            subdata = getTableData(list.get(0), list, hidden);
        }
        data.put("list", subdata);
        return data;

    }

    public Map<String, Object> getPageData(Object entity, Page page, String... hidden){
        Map<String, Object> data = new HashMap<>();
        if(page != null){
            List list = page.getContent();
            data= getTableData(entity, list, hidden);
            data.put("pageIndex", page.getNumber());
            data.put("totalPage", page.getTotalPages());
            data.put("totalElement", page.getTotalElements());
        }else {
            List<String> headers = getFields(entity, hidden);
            data.put("headders", headers);
        }
        return data;
    }

    public List<String> getFields(Object entity, String... hidden){
        List<String> headers = new ArrayList<>();
        Field[] declaredFields = entity.getClass().getDeclaredFields();
        if(hidden == null){
            hidden= new String[]{};
        }
        ArrayList<String> ignorables = new ArrayList();
        Collections.addAll(ignorables, hidden);
        for(Field field : declaredFields){
            String fieldName = field.getName();
            int index = ignorables.indexOf(fieldName);
            if(index<0){
                headers.add(fieldName);
            }else {
                ignorables.remove(index);
            }
        }
        return headers;
    }


}
