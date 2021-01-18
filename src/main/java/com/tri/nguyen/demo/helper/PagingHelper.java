package com.tri.nguyen.demo.helper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import com.tri.nguyen.demo.models.entity.common.EnumCommon.SortType;
import com.tri.nguyen.demo.models.req.CriteriaReq;
import com.tri.nguyen.demo.models.res.SearchResults;

public class PagingHelper {
	
	private PagingHelper() {
	}
	
	public static <T> SearchResults sort(CriteriaReq req, List<T> searchResult, T obj) {
		Integer pageTotal = req.getPage().intValue() == 0 ? 1
				: (int) Math.ceil((double) searchResult.size() / req.getPerPage().doubleValue());

		Stream<T> stream = searchResult.stream();

		Comparator<T> compare = null;
		for(String columnName : req.getColumnName()){
			int index = req.getColumnName().indexOf(columnName);
			SortType type = req.getSortType().size() <= index ? SortType.ASC : req.getSortType().get(index);
			String functionName = String.format("get%s", StringUtils.capitalize(columnName));
			try {
				obj.getClass().getMethod(functionName);

				Comparator<T> subCompare = by(functionName);  
				compare = compare == null ?
						subCompare
						:
						compare.thenComparing(subCompare);

				if (SortType.DESC.equals(type))
					compare = compare.reversed();
			} catch (NoSuchMethodException | SecurityException e) {
				// Ignore unknown column name
			}
		}

		if (compare != null)
			stream = stream.sorted(compare);

		SearchResults results = SearchResults.builder()
				.withPageTotal(pageTotal)
				.withRecords(stream
						.skip((req.getPage() - 1) * req.getPerPage())
						.limit(req.getPerPage())
						.collect(Collectors.toList()))
				.build();

		return results;
	}

	private static <T> Comparator<T> by(String methodName) {
        return by(methodName, Comparator.naturalOrder());
    }

    private static <T> Comparator<T> by(String methodName, Comparator<?> downstream) {
        @SuppressWarnings("unchecked")
        Comparator<Object> uncheckedDownstream =
            (Comparator<Object>) downstream;
        return (t0, t1) -> 
        {
            Object r0 = getOptional(t0, methodName);
            Object r1 = getOptional(t1, methodName);
            return uncheckedDownstream.compare(r0, r1);
        };
    }

    private static <T> T getOptional(Object instance, String methodName) {
        try
        {
            Class<?> type = instance.getClass();
            Method method = type.getDeclaredMethod(methodName);
            Object object = method.invoke(instance);
            @SuppressWarnings("unchecked")
            T result = (T)object;
            return result;
        }
        catch (NoSuchMethodException 
            | SecurityException 
            | IllegalAccessException 
            | IllegalArgumentException 
            | InvocationTargetException
            | ClassCastException e)
        {
            e.printStackTrace();
            return null;
        }
    }

}
