/*Copyright [2016] [Francisco Nauber Bernardo Gois]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package br.unifor.iadapter.threadGroup.simulated;

import org.junit.Test;

public class SimulateConcurrentAcess {
	@Test
	public void test() {		

		synchronized (StaticClass.class) {
			for (int i = 0; i <= 1000; i++) {
				StaticClass.x += i;
			}
			StaticClass.x = 0;
		}
	}
	
	@Test
	public void test20() {

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

		
		synchronized (StaticClass.class) {
			for (int i = 0; i <=99000000; i++) {
				StaticClass.x += i;
			}
			StaticClass.x = 0;
		}

	}

}
