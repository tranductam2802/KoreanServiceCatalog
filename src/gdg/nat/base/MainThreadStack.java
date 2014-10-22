package gdg.nat.base;

import gdg.nat.util.ThreadUtil;

import java.util.Stack;

public class MainThreadStack extends Stack<Object> {
	private static final long serialVersionUID = 3903509541401439294L;

	@Override
	public synchronized boolean isEmpty() {
		ThreadUtil.ensureOnMainThread();
		return super.isEmpty();
	}

	@Override
	public synchronized Object peek() {
		ThreadUtil.ensureOnMainThread();
		return super.peek();
	}

	public synchronized Object pop() {
		ThreadUtil.ensureOnMainThread();
		return super.pop();
	}

	public synchronized Object push(Object obj) {
		ThreadUtil.ensureOnMainThread();
		return super.push(obj);
	}
}