/**
*
* Interfaccia generata
*
*/

package it.nch.is.fo.profilo;

import it.nch.fwk.fo.dto.business.Pojo;
import it.nch.utility.enumeration.Categoria;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

public interface ApplicazioniPojo extends Pojo{

    public String getApplicationCode();

    public void setApplicationCode(String applicationCode);

    public Date getUpdateDate();

    public void setUpdateDate(Date updateDate);

    public String getUpdateUser();

    public void setUpdateUser(String updateUser);

    public String getDescription();

    public Categoria getCategory();

    public void setCategory(String category);

    public void setDescription(String description);

    public String getEnabled();

    public void setEnabled(String enabled);

    public Collection getAree();

    public void setAree(Set aree);


}
