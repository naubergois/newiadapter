package br.unifor.iadapter.threadGroup.simulated;

import static org.junit.Assert.*;

import org.junit.Test;

public class SimulateConcurrentAcess {

	@Test
	public void test() {

		int x;

		synchronized (StaticClass.class) {
			for (int i = 0; i <= 1000; i++) {
				StaticClass.x += i;
			}
			StaticClass.x = 0;
		}

	}

	@Test
	public void test20() {

		int x;

		synchronized (StaticClass.class) {
			for (int i = 0; i <= 20000; i++) {
				StaticClass.x += i;
			}
			StaticClass.x = 0;
		}

	}

	@Test
	public void test30() {

		int x;

		synchronized (StaticClass.class) {
			for (int i = 0; i <= 300000; i++) {
				StaticClass.x += i;
			}
			StaticClass.x = 0;
		}

	}

	@Test
	public void test40() {

		int x;

		synchronized (StaticClass.class) {
			for (int i = 0; i <= 4000000; i++) {
				StaticClass.x += i;
			}
			StaticClass.x = 0;
		}

	}

	@Test
	public void test50() {

		int x;

		synchronized (StaticClass.class) {
			for (int i = 0; i <= 50000000; i++) {
				StaticClass.x += i;
			}
			StaticClass.x = 0;
		}

	}

	@Test
	public void test60() {

		int x;

		synchronized (StaticClass.class) {
			for (int i = 0; i <= 600000000; i++) {
				StaticClass.x += i;
			}
			StaticClass.x = 0;
		}

	}

	@Test
	public void test70() {

		int x;

		synchronized (StaticClass.class) {
			for (int i = 0; i <= 700000000; i++) {
				StaticClass.x += i;
			}
			StaticClass.x = 0;
		}

	}

	@Test
	public void test80() {

		int x;

		synchronized (StaticClass.class) {
			for (int i = 0; i <= 800000000; i++) {
				StaticClass.x += i;
			}
			StaticClass.x = 0;
		}

	}

	@Test
	public void test90() {

		int x;

		synchronized (StaticClass.class) {
			for (int i = 0; i <= 900000000; i++) {
				StaticClass.x += i;
			}
			StaticClass.x = 0;
		}

	}

	@Test
	public void test100() {

		int x;

		synchronized (StaticClass.class) {
			for (int i = 0; i <=99000000; i++) {
				StaticClass.x += i;
			}
			StaticClass.x = 0;
		}

	}

}
