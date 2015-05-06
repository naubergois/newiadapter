package br.unifor.iadapter.percentiles;

/**
 * Copyright (c) 2009 Molindo GmbH
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 *
 * Note:
 *
 * A human-readable format of the "MIT License" in available at creativecommons.org: 
 * http://creativecommons.org/licenses/MIT/
 */

import java.io.Serializable;

public class Percentile implements Serializable {

	private static final long serialVersionUID = 1L;

	private final int _sum;
	private final int _total;
	private final int _limit;

	public Percentile(final int sum, final int total, final int limit) {
		_sum = sum;
		_total = total;
		_limit = limit;
	}

	public int getSum() {
		return _sum;
	}

	public int getTotal() {
		return _total;
	}

	public int getLimit() {
		return _limit;
	}

	public double getPercentage() {
		return _total == 0 ? 0 : 100.0 / _total * _sum;
	}

	public String toString() {
		return String.format("%d (%.2f%%) <= %d ms", _sum, getPercentage(),
				_limit);
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + _limit;
		result = prime * result + _sum;
		result = prime * result + _total;
		return result;
	}

	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Percentile)) {
			return false;
		}
		final Percentile other = (Percentile) obj;
		if (_limit != other._limit) {
			return false;
		}
		if (_sum != other._sum) {
			return false;
		}
		if (_total != other._total) {
			return false;
		}
		return true;
	}

}