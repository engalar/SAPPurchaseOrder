// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package odataquerybuilder.actions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import com.mendix.core.Core;
import com.mendix.logging.ILogNode;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.webui.CustomJavaAction;
import odataquerybuilder.impl.ODataFunctionHelper;
import odataquerybuilder.proxies.OptionType;
import odataquerybuilder.proxies.QueryOption;
import com.mendix.systemwideinterfaces.core.IMendixObject;

/**
 * The results are filtered using by using the value of an attribute in an object.
 * For example, you have an object, with a name attribute. The object has the name 'Sam', Using FilterList you can create a query:
 * 
 * $filter=Name eq 'Sam'
 */
public class FilterObject extends CustomJavaAction<java.lang.String>
{
	private IMendixObject __ODataObject;
	private odataquerybuilder.proxies.OData ODataObject;
	private java.lang.String ODataAttribute;
	private IMendixObject MendixObj;
	private java.lang.String MendixAttributeForFilter;
	private odataquerybuilder.proxies.Evaluation FilterExpression;
	private java.lang.String MendixAttributeForFunction;
	private odataquerybuilder.proxies.Functions Function;
	private java.lang.Boolean Not;

	public FilterObject(IContext context, IMendixObject ODataObject, java.lang.String ODataAttribute, IMendixObject MendixObj, java.lang.String MendixAttributeForFilter, java.lang.String FilterExpression, java.lang.String MendixAttributeForFunction, java.lang.String Function, java.lang.Boolean Not)
	{
		super(context);
		this.__ODataObject = ODataObject;
		this.ODataAttribute = ODataAttribute;
		this.MendixObj = MendixObj;
		this.MendixAttributeForFilter = MendixAttributeForFilter;
		this.FilterExpression = FilterExpression == null ? null : odataquerybuilder.proxies.Evaluation.valueOf(FilterExpression);
		this.MendixAttributeForFunction = MendixAttributeForFunction;
		this.Function = Function == null ? null : odataquerybuilder.proxies.Functions.valueOf(Function);
		this.Not = Not;
	}

	@java.lang.Override
	public java.lang.String executeAction() throws Exception
	{
		this.ODataObject = this.__ODataObject == null ? null : odataquerybuilder.proxies.OData.initialize(getContext(), __ODataObject);

		// BEGIN USER CODE
		
		String returnString = null;
		String filter;
		switch (FilterExpression) {
		case equals:
			filter = "eq";
			break;
		case greater_than:
			filter = "gt";
			break;
		case less_than:
			filter = "lt";
			break;
		case greater_than_or_equal:
			filter = "ge";
			break;
		case less_than_or_equal:
			filter = "le";
			break;
		case not_equal:
			filter = "ne";
			break;
		default:
			filter = "eq";
			break;
		}
		
		LOGGER.info("FilterObject - Derived Filter[" + filter + "]");
		
		String objValueForFilter = null, objValueForFunction = null;
		if (MendixObj != null) {
			if (MendixAttributeForFilter!=null && MendixObj.getValue(this.getContext(), MendixAttributeForFilter) != null) {
				if(MendixObj.getValue(this.getContext(), MendixAttributeForFilter) instanceof Date){
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
					Date date = MendixObj.getValue(this.getContext(), MendixAttributeForFilter);
					objValueForFilter = dateFormat.format(date).substring(0,10)+"T"+dateFormat.format(date).substring(11);
				}
				else {
					objValueForFilter = MendixObj.getValue(this.getContext(), MendixAttributeForFilter).toString();
				}
			}
			
			if (MendixAttributeForFunction!=null && MendixObj.getValue(this.getContext(), MendixAttributeForFunction) != null) {
				objValueForFunction = MendixObj.getValue(this.getContext(), MendixAttributeForFunction).toString();
			}
			
			ODataFunctionHelper helper = new ODataFunctionHelper(ODataObject.getV3FunctionStyle());
			if (objValueForFilter != null || objValueForFunction != null) {
				if(Function == null){
					if(StringUtils.isNumeric(objValueForFilter)){
						returnString = ODataAttribute+" "+filter + " "+objValueForFilter;
					}
					else if(MendixObj.getValue(this.getContext(), MendixAttributeForFilter) instanceof Date){
						returnString = ODataAttribute+" "+filter + " datetime'"+objValueForFilter+"'";
					}
					else{
						returnString = ODataAttribute+" "+filter + " '"+objValueForFilter+"'";
					}
				}
				else {
					returnString = helper.processFunction(Function, objValueForFilter, objValueForFunction, ODataAttribute, filter);
				}
			}
			
			if (returnString != null) {
				if (Not != null && Not) {
					returnString = "not " + returnString;
				}
				QueryOption FilterObj = new QueryOption(this.getContext());
				FilterObj.setOptionType(OptionType.filter);
				FilterObj.setQueryParam_OData(ODataObject);
				FilterObj.setOrder(ODataObject.getNextOrderNumber());
				FilterObj.setValue(returnString);
				FilterObj.commit();
				ODataObject.setNextOrderNumber(ODataObject.getNextOrderNumber() + 1);
				ODataObject.commit();
			}
		}
		return returnString;

		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 * @return a string representation of this action
	 */
	@java.lang.Override
	public java.lang.String toString()
	{
		return "FilterObject";
	}

	// BEGIN EXTRA CODE

	private static final ILogNode LOGGER = Core.getLogger("ODataQueryBuilder");
	
	// END EXTRA CODE
}
