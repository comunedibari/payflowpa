/*
 * File: NullUserData.java
 * Package: it.nch.fwk.fo.base.handler.profile
 *
 * Revision: $Revision: 1.1.1.1 $
 * Last revision by: $Author: CattaniA $
 * Last revised on: $Date: 2006/05/03 11:06:45 $
 * Created on: 11-nov-2005 - 13.40.49
 * Created by: EE07869 (Etnoteam)
 */
package it.nch.fwk.fo.base.handler.profile;

/**
 * @author EE07869
 *
 * la classe NullUserData e' l'implementazione del pattern NullObject
 * per l'interfaccia UserData. Oggetti che debbano restituire un o UserData possono , in
 * sua assenza, decidere di restituire un NullUserData che corrisponde ad uno UserData "vuoto"
 */
public class NullUserData implements UserData {

	private static final long	serialVersionUID	= - 8826490867167221431L;

	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(NullUserData.class);

    /* (non-Javadoc)
     * @see it.nch.fwk.fo.base.handler.profile.UserData#hasProperty(java.lang.String)
     */
    public boolean hasProperty(String property) {
        return false;
    }

    /* (non-Javadoc)
     * @see it.nch.fwk.fo.base.handler.profile.UserData#getBooleanProperty(java.lang.String)
     */
    public boolean getBooleanProperty(String property)
            throws IllegalArgumentException {
        throw new IllegalArgumentException("non esiste la proprieta' ("+property+") : NullUserData");
    }

    /* (non-Javadoc)
     * @see it.nch.fwk.fo.base.handler.profile.UserData#setBooleanProperty(java.lang.String, boolean)
     */
    public void setBooleanProperty(String property, boolean value)
            throws IllegalArgumentException {
        logger.warn("Si sta cercando di settare in un NullUserData la proprieta': "+property);
    }

    /* (non-Javadoc)
     * @see it.nch.fwk.fo.base.handler.profile.UserData#getProperty(java.lang.String)
     */
    public String getProperty(String property) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new IllegalArgumentException("non esiste la proprieta' ("+property+") : NullUserData");
    }

    /* (non-Javadoc)
     * @see it.nch.fwk.fo.base.handler.profile.UserData#setProperty(java.lang.String, java.lang.String)
     */
    public void setProperty(String property, String value)
            throws IllegalArgumentException {
        logger.warn("Si sta cercando di settare in un NullUserData la proprieta': "+property);
    }

    /* (non-Javadoc)
     * @see it.nch.fwk.fo.base.handler.profile.UserData#getServiceProperty(java.lang.String, java.lang.String)
     */
    public String getServiceProperty(String profileServiceName,
            String propertyName) {
        throw new IllegalArgumentException("non esiste la proprieta' "+propertyName+" per il servizio "+profileServiceName+": NullUserData");
    }

    /* (non-Javadoc)
     * @see it.nch.fwk.fo.base.handler.profile.UserData#setServiceProperty(java.lang.String, java.lang.String, java.lang.String)
     */
    public void setServiceProperty(String profileServiceName,
            String propertyName, String value) {
        logger.warn("Si sta cercando di settare in un NullUserData la proprieta': "+propertyName+" per il servizio "+profileServiceName);
    }

    /* (non-Javadoc)
     * @see it.nch.fwk.fo.base.handler.profile.UserData#setServiceProperty(java.lang.String, java.lang.String)
     */
    public void setServiceProperty(String propertyKey, String value) {
        logger.warn("Si sta cercando di settare in un NullUserData la proprieta': "+propertyKey);
    }

    /* (non-Javadoc)
     * @seeit.nch.fwk.fo.base.handler.profile.UserData#getPropertyObject(java.lang.String)
     */
    public Object getPropertyObject(String propertyName) {
        throw new IllegalArgumentException("non esiste la proprieta' ("+propertyName+") : NullUserData");
    }

    /* (non-Javadoc)
     * @see it.nch.fwk.fo.base.handler.profile.UserData#setPropertyObject(java.lang.String, java.lang.Object)
     */
    public void setPropertyObject(String propertyName, Object object) {
        logger.warn("Si sta cercando di settare in un NullUserData la proprieta': "+propertyName);
    }

    /* (non-Javadoc)
     * @see it.nch.fwk.fo.base.handler.profile.UserData#getPropertyInt(java.lang.String)
     */
    public int getPropertyInt(String propertyName) {
        throw new IllegalArgumentException("non esiste la proprieta' ("+propertyName+") : NullUserData");
    }

    /* (non-Javadoc)
     * @see it.nch.fwk.fo.base.handler.profile.UserData#setPropertyInt(java.lang.String, int)
     */
    public void setPropertyInt(String propertyName, int integer) {
        logger.warn("Si sta cercando di settare in un NullUserData la proprieta': "+propertyName);
    }

}
