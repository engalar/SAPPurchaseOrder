package odataquerybuilder.impl;

import java.util.Comparator;

import odataquerybuilder.proxies.QueryParam;

public class ODataSortComparator implements Comparator<QueryParam>{

	@Override
	public int compare(QueryParam arg0, QueryParam arg1) {
		// TODO Auto-generated method stub
		return arg0.getOrder() - arg1.getOrder();
	}

}
