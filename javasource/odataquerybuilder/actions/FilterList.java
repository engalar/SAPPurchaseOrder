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
import odataquerybuilder.proxies.AndOrFilter;
import odataquerybuilder.proxies.OptionType;
import odataquerybuilder.proxies.QueryOption;
import com.mendix.systemwideinterfaces.core.IMendixObject;

/**
 * The results are filtered using by using the value of an attribute of each of a list of objects.
 * For example, you have a list of two people objects, each with a name attribute. The two objects have the name 'Adam' and 'Eve', Using FilterList you can create a query:
 * 
 * $filter=Name eq 'Adam' or Name eq 'Eve'
 */
public class FilterList extends CustomJavaAction<java.lang.String>
{
	private IMendixObject __ODataObject;
	private odataquerybuilder.proxies.OData ODataObject;
	private java.lang.String ODataVariableName;
	private java.util.List<IMendixObject> MendixListObject;
	private java.lang.String MendixAttributeForFilter;
	private odataquerybuilder.proxies.Evaluation FilterExpression;
	private java.lang.String MendixAttributeForFunction;
	private odataquerybuilder.proxies.Functions Function;
	private odataquerybuilder.proxies.AndOrFilter JoinType;
	private java.lang.Boolean Not;

	public FilterList(IContext context, IMendixObject ODataObject, java.lang.String ODataVariableName, java.util.List<IMendixObject> MendixListObject, java.lang.String MendixAttributeForFilter, java.lang.String FilterExpression, java.lang.String MendixAttributeForFunction, java.lang.String Function, java.lang.String JoinType, java.lang.Boolean Not)
	{
		super(context);
		this.__ODataObject = ODataObject;
		this.ODataVariableName = ODataVariableName;
		this.MendixListObject = MendixListObject;
		this.MendixAttributeForFilter = MendixAttributeForFilter;
		this.FilterExpression = FilterExpression == null ? null : odataquerybuilder.proxies.Evaluation.valueOf(FilterExpression);
		this.MendixAttributeForFunction = MendixAttributeForFunction;
		this.Function = Function == null ? null : odataquerybuilder.proxies.Functions.valueOf(Function);
		this.JoinType = JoinType == null ? null : odataquerybuilder.proxies.AndOrFilter.valueOf(JoinType);
		this.Not = Not;
	}

	@java.lang.Override
	public java.lang.String executeAction() throws Exception
	{
		this.ODataObject = this.__ODataObject == null ? null : odataquerybuilder.proxies.OData.initialize(getContext(), __ODataObject);

		// BEGIN USER CODE
		String returnListString = null;
		String filter;
		switch (FilterExpression) {
			case equals : filter = "eq";
				break;
			case greater_than : filter = "gt";
				break;
			case less_than :filter = "lt";
				break;
			case greater_than_or_equal : filter = "ge";
				break;
			case less_than_or_equal : filter = "le";
				break;
			case not_equal : filter = "ne";
				break;
			default : filter = "eq";
				break;	
		} 
		
		LOGGER.info("FilterObject - Derived Filter[" + filter + "]");
		
		boolean isFirst = true;
		
		if (MendixListObject!=null) {
				for(IMendixObject obj : MendixListObject){
				
				if(isFirst ==false){
					returnListString+="&";
					isFirst = false;
				}
				
				String objValueForFilter = null, objValueForFunction = null;
				ODataFunctionHelper helper = new ODataFunctionHelper(ODataObject.getV3FunctionStyle());
				if(MendixAttributeForFilter!=null && obj.getValue(this.getContext(), MendixAttributeForFilter) != null){
					if(obj.getValue(this.getContext(), MendixAttributeForFilter) instanceof Date){
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
						Date date = obj.getValue(this.getContext(), MendixAttributeForFilter);
						objValueForFilter = dateFormat.format(date).substring(0,10)+"T"+dateFormat.format(date).substring(11);
					}
					else{
						objValueForFilter = obj.getValue(this.getContext(), MendixAttributeForFilter).toString();
					}
				}
				
				if(MendixAttributeForFunction!=null && obj.getValue(this.getContext(), MendixAttributeForFunction) != null){
					 objValueForFunction = obj.getValue(this.getContext(), MendixAttributeForFunction).toString();
				}
				
				String filterString = "";
				if (objValueForFilter != null || objValueForFunction != null) {
					if(Function == null){
						if(StringUtils.isNumeric(objValueForFilter)){
							filterString = ODataVariableName+" "+filter + " "+objValueForFilter;
						}
						else if(obj.getValue(this.getContext(), MendixAttributeForFilter) instanceof Date){
							filterString = ODataVariableName+" "+filter + " datetime'"+objValueForFilter+"'";
						}
						else{
							filterString = ODataVariableName+" "+filter + " '"+objValueForFilter+"'";
						}
					}
					else {
						filterString = helper.processFunction(Function, objValueForFilter, objValueForFunction, ODataVariableName, filter);
					}
				}
				
				if(Not !=null && Not && !filterString.isEmpty()){
					filterString = "not " + filterString;
				}
				if (returnListString == null) {
					returnListString = filterString;
				} else {
					if (JoinType == AndOrFilter.AND) {
						returnListString += " and " + filterString;
					} else {
						returnListString += " or " + filterString;
					}
				}
			}
		}
		if(returnListString!=null){
			QueryOption FilterList = new QueryOption(this.getContext());
			FilterList.setOptionType(OptionType.filter);
			FilterList.setQueryParam_OData(ODataObject);
			FilterList.setOrder(ODataObject.getNextOrderNumber());
			FilterList.setValue(returnListString);
			FilterList.commit();
			ODataObject.setNextOrderNumber(ODataObject.getNextOrderNumber()+1);
			ODataObject.commit();
		}


		return returnListString;
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 * @return a string representation of this action
	 */
	@java.lang.Override
	public java.lang.String toString()
	{
		return "FilterList";
	}

	// BEGIN EXTRA CODE
	private static final ILogNode LOGGER = Core.getLogger("ODataQueryBuilder");
	// END EXTRA CODE
}