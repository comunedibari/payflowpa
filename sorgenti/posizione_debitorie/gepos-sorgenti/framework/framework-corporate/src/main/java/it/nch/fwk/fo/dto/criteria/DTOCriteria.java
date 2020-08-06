package it.nch.fwk.fo.dto.criteria;


import it.nch.fwk.fo.exceptions.NotImplementedException;

import java.io.Serializable;

public class DTOCriteria implements Serializable {

	private static final long	serialVersionUID	= - 2548204533015938236L;
	private ICondition        preCondition   = new DTOPreCondition();
	private ICondition        postCondition  = null; //new DTOPostCondition();

	void elaboratePreCondition() throws Exception{
		this.preCondition.elaborateCondition();
	}

	void elaboratePostCondition() throws Exception
	{
		this.postCondition.elaborateCondition();
	}

	/**
	 * @return Returns the postCondition.
	 */
	public ICondition getPostCondition()
	{
		return this.postCondition;
	}
	/**
	 * @param postCondition The postCondition to set.
	 */
	public void setPostCondition(ICondition postCondition)
	{
		this.postCondition = postCondition;
	}
	/**
	 * @return Returns the preCondition.
	 */
	public ICondition getPreCondition()
	{
		return this.preCondition;
	}
	/**
	 * @param preCondition The preCondition to set.
	 */
	public void setPreCondition(ICondition preCondition){
		this.preCondition = preCondition;
	}

	public void addPreCondition(String tokenLogico,
								String key,
								String operator,
								String[] value)
	throws Exception
	{
		this.preCondition.addCondition(tokenLogico, key, operator, value);
	}

	public void addPostCondition(String tokenLogico, String key, String operator, String value)
	throws NotImplementedException
	{
		// TODO
		//this.postCondition.addCondition(tokenLogico, key, operator, value);
		throw new NotImplementedException(this.toString()
										 + ".addPostCondition() "
										 + "has not been implemented yet.");
	}

	public void addPostInnerCondition(String tokenLogico, ICondition innerCondition)
	throws NotImplementedException
	{
		// TODO
//		this.postCondition.addInnerCondition(tokenLogico, innerCondition);
		throw new NotImplementedException(this.toString()
				 						 + ".addPostInnerCondition() "
				 						 + "has not been implemented yet.");
	}

	public void addPreInnerCondition(String tokenLogico, ICondition innerCondition)
	throws Exception
	{
		this.preCondition.addInnerCondition(tokenLogico, innerCondition);
	}
}
