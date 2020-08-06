/*
 * Created on 29-nov-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.nch.fwk.fo.base.handler.profile;

import java.io.Serializable;

/**
 * @author finsaccanebbia
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface UserContextInterface extends ContextProfile, Cloneable, Serializable {
    /**
     * Returns the originalHost.
     * @return String
     */
    public abstract String getOriginalHost();

    /**
     * Method setOriginalHost.
     * @param originalHost
     */
    public abstract void setOriginalHost(String originalHost);

    /**
     * @return Returns the channelHost.
     */
    public abstract String getChannelHost();

    /**
     * @param channelHost The channelHost to set.
     */
    public abstract void setChannelHost(String channelHost);

    /**
     * @return
     */
    public abstract String getSessionId();

    /**
     * @return
     */
    public abstract String getActionId();
    
    
    /**
     * @return
     */
    public abstract void setSessionId(String sessionId);

    /**
     * @return
     */
    public abstract void setActionId(String actionId);

    /**
     * @return
     */
    public abstract String getLoginName();
    
    /**
     * @return
     */
    public abstract void setLoginName(String loginName);
    
    public abstract Object clone();
}