/**
 * 08/giu/2009
 */
package it.nch.eb.xsqlcmd.threads;

import java.util.Stack;

/**
 * @author gdefacci
 */
public class Coordinator {
	
	static final Runnable NOOP = new Runnable() {

		public void run() {
		}
		
	};
	
	private final StackUser consumer;
	private final NotifierStack sharedState = new NotifierStack();
	
	public Coordinator(StackUser consumer) {
		this.consumer = consumer;
	}

	class NotifierStack extends Stack {
		private static final long serialVersionUID = 1L;
		private final Stack stack = new Stack();

		public Object push(Object obj) {
			stack.push(obj);
			pushListener().run();
			return obj;
		}
		
		public Object pop() {
			Object res = stack.pop();
			popListener().run();
			return res;
		}

		public boolean empty() {
			return stack.empty();
		}

		public synchronized Object peek() {
			return stack.peek();
		}
		
	}
	
	Runnable pushListener() {
		return new Runnable() {

			public void run() {
				consumer.apply(sharedState);
			}
			
		};
	}
	
	Runnable popListener() {
		return NOOP;
	}

	public void runWithSharedState(StackUser user) {
		user.apply(sharedState);
	}
	
}