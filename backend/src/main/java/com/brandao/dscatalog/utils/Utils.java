package com.brandao.dscatalog.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.brandao.dscatalog.projections.IdProjection;


public class Utils {    

    public static <ID, T extends IdProjection<ID>> List<T>replace(List<? extends IdProjection<ID>> ordered, List<T> unordered){

        Map<ID, T> map = new HashMap<>();
        for(T obj : unordered){
            map.put((obj.getId()), obj);
        }
        List<T> result = new ArrayList<>();
        for(IdProjection<ID> obj : ordered){
            result.add(map.get(obj.getId()));
        }

        return result;
    }

}
