package it.nch.fwk.fo.dto.business;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class PojoImpl implements Serializable {

	private Long id;
	private Integer Version;
	private boolean flagDoNotCopy = false; // SADPA - to avoid infinite
											// recursion on bidirectional links
	private List<Class> copyObjectList;

	// @Version
	@Transient
	public Integer getVersion() {
		return Version;
	}

	public void setVersion(Integer version) {
		Version = version;
	}

	@Transient
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Transient
	public boolean getFlagDoNotCopy() {
		return flagDoNotCopy;
	}

	public void setFlagDoNotCopy(boolean flagDoNotCopy) {
		this.flagDoNotCopy = flagDoNotCopy;
	}

	public void setCopyList(List<Class> copyList) {
		if (this.copyObjectList == null)
			this.copyObjectList = new LinkedList<Class>();

		this.copyObjectList.addAll(copyList);
	}

	@Transient
	public void addToCopyList(Class cls) {
		if (this.copyObjectList == null)
			this.copyObjectList = new LinkedList<Class>();

		this.copyObjectList.add(cls);
	}

	@Transient
	public List<Class> getCopyList() {
		return this.copyObjectList;
	}

	@Transient
	public boolean isInCopyList(Class classToCopy) {
		if (this.copyObjectList == null)
			return false;

		for (Class cl : this.copyObjectList) {
			if (cl.equals(classToCopy))
				return true;
		}

		return false;
	}
}
