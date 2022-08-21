package me.cumhax.chipshack.util;

public class Timah {

	private long time;

	public Timah() {
		this.time = -1L;
	}

	public boolean passedMs(final long ms) {
		return this.getMs(System.nanoTime() - this.time) >= ms;
	}

	public void reset() {
		this.time = System.nanoTime();
	}

	public long getMs(final long time) {
		return time / 1000000L;
	}

}
