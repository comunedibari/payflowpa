/**
 * Created on 06/dic/07
 */
package it.nch.eb.flatx.bean.types;

import it.nch.eb.common.utils.Alignments;

import java.io.Serializable;



/**
 * @author gdefacci
 */
public interface FillerConverter extends SizedConverter {

	public String getFiller();
	public int getAlignment();
	
	class SizedConverterAdapter implements FillerConverter, Serializable, DelegatorConverter {
		
		private static final long	serialVersionUID	= 6964095526367669018L;
		private final SizedConverter	delegate;
		private String	filler;
		private int	alignment;

		public SizedConverterAdapter(SizedConverter delegate) {
			this.delegate = delegate;
			if (delegate instanceof FillerConverter) {
				FillerConverter fc = (FillerConverter) delegate;
				this.filler = fc.getFiller();
				this.alignment = fc.getAlignment();
			} else {
				this.filler = " ";
				this.alignment = Alignments.LEFT;
			}
		}

		public int getAlignment() {
			return alignment;
		}

		public String getFiller() {
			return filler;
		}

		public Integer getLength() {
			return delegate.getLength();
		}

		public String encode(String stringModel) {
			return delegate.encode(stringModel);
		}

		public String toString() {
			return "FillerConverter adapter of " + delegate;
		}

		public Converter getDelegate() {
			return delegate;
		}

	}
	
}
