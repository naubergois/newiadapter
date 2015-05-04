package br.unifor.iadapter.threadGroup;

/*
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class PercentileCounter implements Iterable<Percentile> {
	private static final long serialVersionUID = 1L;

	private final int[] _limits;
	private final int[] _counts;
	private int _total;

	public PercentileCounter(final int... limits) {
		this(TimeUnit.MILLISECONDS, limits);
	}

	public PercentileCounter(final TimeUnit unit, final int... limits) {
		if (limits.length == 0) {
			throw new IllegalArgumentException("at least 1 limit required");
		}

		_limits = Arrays.copyOf(limits, limits.length);
		Arrays.sort(_limits);

		if (unit != TimeUnit.MILLISECONDS) {
			for (int i = 0; i < _limits.length; i++) {
				_limits[i] = (int) unit.toMillis(_limits[i]);
			}
		}

		_counts = new int[_limits.length];

	}

	public void increment(final int millis) {
		final int i = index(millis);
		if (i < _limits.length) {
			_counts[i]++;
		}
		_total++;
	}

	int index(final int millis) {
		// binary search

		int low = 0;
		int high = _limits.length - 1;
		int mid = -1;

		while (low <= high) {
			mid = (low + high) / 2;

			final int res = _limits[mid] - millis;
			if (res < 0) {
				low = mid + 1;
			} else if (res > 0) {
				high = mid - 1;
			} else {
				return mid;
			}
		}

		return _limits[mid] < millis ? mid + 1 : mid;
	}

	public int getTotal() {
		return _total;
	}

	public int estimatePercentile(final double percentile) {
		if (percentile < 0.0 || percentile > 100.0) {
			throw new IllegalArgumentException(
					"percentile must be between 0.0 and 100.0, was "
							+ percentile);
		}

		for (final Percentile p : this) {
			if (percentile - p.getPercentage() <= 0.0001) {
				return p.getLimit();
			}
		}
		return Integer.MAX_VALUE;
	}

	public void clear() {
		for (int i = 0; i < _counts.length; i++) {
			_counts[i] = 0;
		}
		_total = 0;
	}

	public String toString() {
		final StringBuilder buf = new StringBuilder();
		buf.append(PercentileCounter.class.getSimpleName()).append(": ");

		Percentile last = null;
		for (final Percentile p : this) {
			if (last == null || p.getSum() != last.getSum()) {
				buf.append(p).append(", ");
			}
			last = p;
		}

		final double remaining = last == null ? 0.0 : 100.0 - last
				.getPercentage();

		if (Math.abs(remaining) > 0.001) {
			final int count = _total - last.getSum();
			final int limit = _limits[_limits.length - 1];
			buf.append(String.format("%d (%.2f%%) > %d ms", count, remaining,
					limit));
			buf.append(", ");
		}

		buf.append(_total).append(" total");

		return buf.toString();
	}

	public Iterator<Percentile> iterator() {
		return new Iterator<Percentile>() {

			// get a copy of current state
			private final int _total = PercentileCounter.this._total;
			private final int[] _counts = Arrays.copyOf(
					PercentileCounter.this._counts,
					PercentileCounter.this._counts.length);

			private int _i = 0;
			private int _sum = 0;

			public boolean hasNext() {
				return _i < _counts.length;
			}

			public Percentile next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}

				_sum += _counts[_i];
				final Percentile p = new Percentile(_sum, _total, _limits[_i]);
				_i++;
				return p;
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	public List<Percentile> toList() {
		final ArrayList<Percentile> list = new ArrayList<Percentile>(
				_limits.length);
		for (final Percentile p : this) {
			list.add(p);
		}
		return list;
	}

	public int[] getLimits() {
		return Arrays.copyOf(_limits, _limits.length);
	}

}