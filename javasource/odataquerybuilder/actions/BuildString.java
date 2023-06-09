// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package odataquerybuilder.actions;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.mendix.core.Core;
import com.mendix.logging.ILogNode;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.systemwideinterfaces.core.meta.IMetaAssociation;
import com.mendix.webui.CustomJavaAction;
import odataquerybuilder.impl.ODataSortComparator;
import odataquerybuilder.proxies.AndOrFilter;
import odataquerybuilder.proxies.Operator;
import odataquerybuilder.proxies.OptionType;
import odataquerybuilder.proxies.QueryOption;
import odataquerybuilder.proxies.QueryParam;

/**
 * Used to build the query after each parameter has been set.
 * Returns a string containing the complete OData query.
 */
public class BuildString extends CustomJavaAction<java.lang.String>
{
	private IMendixObject __OData;
	private odataquerybuilder.proxies.OData OData;

	public BuildString(IContext context, IMendixObject OData)
	{
		super(context);
		this.__OData = OData;
	}

	@java.lang.Override
	public java.lang.String executeAction() throws Exception
	{
		this.OData = this.__OData == null ? null : odataquerybuilder.proxies.OData.initialize(getContext(), __OData);

		// BEGIN USER CODE
		String oDataStringBase = OData.getLocation() == null?"":OData.getLocation();		
		oDataStringBase = oDataStringBase + "/"+ OData.getCollectionName();
		String oDataString = "";
		boolean isFirst = true;
		if(OData.getFormat() != null) 
		{
			oDataString = oDataString +"?"+"$format="+OData.getFormat().toString().toLowerCase();
			isFirst = false;
		}
		Collection<? extends IMetaAssociation> childAssociations =OData.getMendixObject().getMetaObject().getMetaAssociationsChild();
		for(IMetaAssociation childAssociation: childAssociations ){
			List<IMendixObject> queryParams = Core.retrieveByPath(this.getContext(), OData.getMendixObject(), childAssociation.getName());


			List<QueryParam>optionList = createParamQueryList(queryParams);
			optionList.sort(new ODataSortComparator());

			for(QueryParam queryParam :optionList){

				if(queryParam instanceof QueryOption){
					QueryOption option = (QueryOption) queryParam;
					if(option.getOptionType() == OptionType.count){
						oDataStringBase = oDataStringBase + "/$count";
					}
					else if(option.getOptionType()!= OptionType.filter || !oDataString.contains("$filter")){
						if(isFirst){
							oDataString = oDataString +"?"+processQueryOption(option,oDataString);
							isFirst = false;
						}
						else oDataString+= "&"+processQueryOption(option,oDataString);
					}else{

						oDataString+= processQueryOption(option,oDataString);
					}


				}else{
					Operator operator = (Operator) queryParam;
					if(checkIfOperatorNeeded(oDataString,operator,optionList))
					oDataString+=processOperator(operator);
				}
			}

		}
		return oDataStringBase+oDataString;
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 * @return a string representation of this action
	 */
	@java.lang.Override
	public java.lang.String toString()
	{
		return "BuildString";
	}

	// BEGIN EXTRA CODE
	private static final ILogNode LOGGER = Core.getLogger("ODataQueryBuilder");
	
	private java.lang.String processQueryOption(QueryOption queryOption,String oDataString){
		OptionType optionType = queryOption.getOptionType();
		switch(optionType){
		case inlinecount:
			return "$inlinecount=allpages";
		case count:
			return "$count";
		case filter:
				if (oDataString.contains("$filter"))
					return encodeString(queryOption.getValue());

				return "$filter="+ encodeString(queryOption.getValue());

		case skip:
			if(oDataString.contains("$skip"))
				return "";

				return "$skip="+encodeString(queryOption.getValue());
		case expand:
			if(oDataString.contains("$expand"))
				return "";

			return "$expand="+encodeString(queryOption.getValue());
		case orderby:
			if(oDataString.contains("$orderby"))
				return queryOption.getValue();

			return "$orderby="+encodeString(queryOption.getValue());
		case search:
			if(oDataString.contains("$search"))
				return encodeString(queryOption.getValue());

			return "$search="+encodeString(queryOption.getValue());
		case select:
			if(oDataString.contains("$select"))
				return ","+encodeString(queryOption.getValue());

			return "$select="+queryOption.getValue();
		case top:
			if(oDataString.contains("$top"))
				return "";

			return "$top="+encodeString(queryOption.getValue());
		default:
			return "";

		}
	}
	private java.lang.String processOperator(Operator queryOperator){
		AndOrFilter optionType = queryOperator.getOperatorType();
		switch(optionType){
		case AND:
			return "%20and%20";
		case OR:
			return "%20or%20";
		default:
			return "";

		}
	}

	private java.lang.String encodeString(String value){
		try {
			LOGGER.info("Query before encoding [" + value + "]");
			return URLEncoder.encode(value,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * This method is used to see whether an operator needs to be added.
	 */
	private boolean checkIfOperatorNeeded(String oDataString,Operator operator,List<QueryParam>optionList){
		if(operator.getOrder()+1 >= optionList.size())
			return false;

		if(optionList.get(operator.getOrder()+1) instanceof QueryOption){
			QueryOption nextOption = (QueryOption) optionList.get(operator.getOrder()+1);
			if(nextOption.getOptionType() == OptionType.filter)
				return true;
		}
		return false;
	}

	private List<QueryParam> createParamQueryList(List<IMendixObject>options){
		ArrayList<QueryParam> queryParams = new ArrayList<QueryParam>();
		for(IMendixObject queryParam :options){
			QueryParam param = QueryParam.initialize(this.getContext(), queryParam);
			queryParams.add(param);
		}
		return queryParams;

	}

	// END EXTRA CODE
}
