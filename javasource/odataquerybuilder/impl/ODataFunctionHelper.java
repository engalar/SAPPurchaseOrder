package odataquerybuilder.impl;

import odataquerybuilder.proxies.Functions;

public class ODataFunctionHelper {

	private final Boolean v3FunctionStyle;

	public ODataFunctionHelper(Boolean v3FunctionStyle) {
		this.v3FunctionStyle = v3FunctionStyle;
	}

	public java.lang.String processFunction(Functions function, String valueForFilter, String valueForFunction, String ODataField, String filter){

		switch (function) {
		case substringof:
			if (v3FunctionStyle) {
				return "substringof('" + valueForFunction + "'," + ODataField + ")";
			} else {
				// the tolower function here is a left over of the original implementation.
				return "substringof('" + valueForFunction + "'," + "tolower(" + ODataField + ")) eq true";
			}
		case endswith:
			String endsWithStr = "endswith(" + ODataField + ",'" + valueForFunction + "')";
			if (!v3FunctionStyle) {
				endsWithStr += " eq true";
			}
			return endsWithStr;
		case startswith:
			String startsWithStr = "startswith(" + ODataField + ",'" + valueForFunction + "')";
			if (!v3FunctionStyle) {
				startsWithStr += " eq true";
			}
			return startsWithStr;
		case round:
			return "round(" + ODataField + ") " + filter + " " + valueForFilter;
		case ceiling:
			return "celing(" + ODataField + ") " + filter + " " + valueForFilter;
		case floor:
			return "floor(" + ODataField + ") " + filter + " " + valueForFilter;
		case day:
			return "day(" + ODataField + ") " + filter + " " + valueForFilter;
		case hour:
			return "hour(" + ODataField + ") " + filter + " " + valueForFilter;
		case length:
			return "length(" + ODataField + ") " + filter + " " + valueForFilter;
		case month:
			return "month(" + ODataField + ") " + filter + " " + valueForFilter;
		case second:
			return "second(" + ODataField + ") " + filter + " " + valueForFilter;
		case year:
			return "year(" + ODataField + ") " + filter + " " + valueForFilter;
		case minute:
			return "minute(" + ODataField + ") " + filter + " " + valueForFilter;
		case tolower:
			return "tolower(" + ODataField + ") " + filter + " '" + valueForFilter + "'";
		case toupper:
			return "toupper(" + ODataField + ") " + filter + " '" + valueForFilter + "'";
		case trim:
			return "trim(" + ODataField + ") " + filter + " '" + valueForFilter + "'";
		case indexof:
			return "indexof(" + ODataField + ",'" + valueForFunction + "') " + filter + " " + valueForFilter;
		case isof:
			if(ODataField == null)
				return "isof('" + valueForFunction + "')";
			else if(valueForFunction == null || valueForFunction.isEmpty())
				return "isof('" + ODataField + "')";
			else
				return "isof(" + ODataField + ",'" + valueForFunction + "')";
		case concat:
			return "concat(" + ODataField + ",'" + valueForFunction + "') " + filter + " '" + valueForFilter + "'";
		case substring:
			return "substring(" + ODataField + "," + valueForFunction + ") " + filter + " '" + valueForFilter + "'";

		default:
			return ODataField + " " + filter + " '" + valueForFilter + "'";

		}
	}


}
