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

// TODO: add startTimeSec - integer epoch seconds only - why startTime does not apply?
// TODO: buffer file writes to bigger chunks?
package br.unifor.iadapter.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.samplers.SampleEvent;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

/**
 * @see ResultCollector
 */
public class FileWriter {

	public static final String AVAILABLE_FIELDS = "isSuccsessful "
			+ "startTime endTime "
			+ "sentBytes receivedBytes "
			+ "responseTime latency "
			+ "responseCode responseMessage "
			+ "isFailed " // surrogates
			+ "threadName sampleLabel " + "startTimeMillis endTimeMillis "
			+ "responseTimeMicros latencyMicros "
			+ "requestData responseData responseHeaders "
			+ "threadsCount requestHeaders workLoadName enter";
	private static final Logger log = LoggingManager.getLoggerForClass();
	private static final String OVERWRITE = "overwrite";
	private static final String FILENAME = "filename";
	private static final String COLUMNS = "columns";
	private static final String HEADER = "header";
	private static final String FOOTER = "footer";
	private static final String VAR_PREFIX = "variable#";

	private int writeBufferSize;

	public int getWriteBufferSize() {
		return writeBufferSize;
	}

	public void setWriteBufferSize(int writeBufferSize) {
		this.writeBufferSize = writeBufferSize;
	}

	protected volatile FileChannel fileChannel;

	public FileChannel getFileChannel() {
		return fileChannel;
	}

	public void setFileChannel(FileChannel fileChannel) {
		this.fileChannel = fileChannel;
	}

	private int[] compiledVars;
	private int[] compiledFields;
	private ByteBuffer[] compiledConsts;
	private ArrayList<String> availableFieldNames = new ArrayList<String>(
			Arrays.asList(AVAILABLE_FIELDS.trim().split(" ")));
	private static final byte[] b1 = "1".getBytes();
	private static final byte[] b0 = "0".getBytes();

	public FileWriter() {
		super();
	}

	public void sampleStarted(SampleEvent e) {
	}

	public void sampleStopped(SampleEvent e) {
	}

	public void testStarted() {
		compileColumns();
		try {
			openFile();
		} catch (FileNotFoundException ex) {
			log.error("Cannot open file " + getFilename(), ex);
		} catch (IOException ex) {
			log.error("Cannot write file header " + getFilename(), ex);
		}
	}

	public void testStarted(String host) {
		testStarted();
	}

	public void testEnded() {
		closeFile();
	}

	public void testEnded(String host) {
		testEnded();
	}

	private String fileName;

	public void setFilename(String name) {
		this.fileName = name;
	}

	public String getFilename() {
		return this.fileName;
	}

	private String cols;

	public void setColumns(String cols) {
		this.cols = cols;
	}

	public String getColumns() {
		return this.cols;
	}

	private boolean overwrite;

	public boolean isOverwrite() {
		return this.overwrite;
	}

	public void setOverwrite(boolean ov) {
		this.overwrite = ov;
	}

	private String fileHeader;

	public void setFileHeader(String str) {
		this.fileHeader = str;
	}

	public String getFileHeader() {
		return this.fileHeader;
	}

	private String fileFooter;

	public void setFileFooter(String str) {
		this.fileFooter = str;
	}

	public String getFileFooter() {
		return this.fileFooter;
	}

	/**
	 * making this once to be efficient and avoid manipulating strings in switch
	 * operators
	 */
	public void compileColumns() {
		log.debug("Compiling columns string: " + getColumns());
		String[] chunks = JMeterPluginsUtils.replaceRNT(getColumns()).split(
				"\\|");
		log.debug("Chunks " + chunks.length);
		compiledFields = new int[chunks.length];
		compiledVars = new int[chunks.length];
		compiledConsts = new ByteBuffer[chunks.length];
		for (int n = 0; n < chunks.length; n++) {
			int fieldID = availableFieldNames.indexOf(chunks[n]);
			if (fieldID >= 0) {
				// log.debug(chunks[n] + " field id: " + fieldID);
				compiledFields[n] = fieldID;
			} else {
				compiledFields[n] = -1;
				compiledVars[n] = -1;
				if (chunks[n].contains(VAR_PREFIX)) {
					log.debug(chunks[n] + " is sample variable");
					String varN = chunks[n].substring(VAR_PREFIX.length());
					try {
						compiledVars[n] = Integer.parseInt(varN);
					} catch (NumberFormatException e) {
						log.error("Seems it is not variable spec: " + chunks[n]);
						compiledConsts[n] = ByteBuffer.wrap(chunks[n]
								.getBytes());
					}
				} else {
					log.debug(chunks[n] + " is const");
					if (chunks[n].length() == 0) {
						// log.debug("Empty const, treated as |");
						chunks[n] = "|";
					}

					compiledConsts[n] = ByteBuffer.wrap(chunks[n].getBytes());
				}
			}
		}
	}

	public void openFile() throws IOException {
		String filename = getFilename();
		FileOutputStream fos = new FileOutputStream(filename, !isOverwrite());
		fileChannel = fos.getChannel();

		String header = JMeterPluginsUtils.replaceRNT(getFileHeader());
		if (!header.isEmpty()) {
			syncWrite(ByteBuffer.wrap(header.getBytes()));
		}
	}

	public synchronized void closeFile() {
		if (fileChannel != null && fileChannel.isOpen()) {
			try {
				String footer = JMeterPluginsUtils.replaceRNT(getFileFooter());
				if (!footer.isEmpty()) {
					syncWrite(ByteBuffer.wrap(footer.getBytes()));
				}

				fileChannel.force(false);
				fileChannel.close();
			} catch (IOException ex) {
				log.error("Failed to close file: " + getFilename(), ex);
			}
		}
	}

	private static ByteBuffer buf;

	public static ByteBuffer singleton(int writeBufferSize) {

		if (buf == null) {
			buf = ByteBuffer.allocateDirect(writeBufferSize);
		}

		return buf;

	}

	public void sampleOccurred(SampleEvent evt, String workLoadName) {
		if (fileChannel == null || !fileChannel.isOpen()) {
			if (log.isWarnEnabled()) {
				log.warn("File writer is closed! Maybe test has already been stopped");
			}
			return;
		}

		ByteBuffer buf = ByteBuffer.allocateDirect(writeBufferSize);
		for (int n = 0; n < compiledConsts.length; n++) {
			if (compiledConsts[n] != null) {
				synchronized (compiledConsts) {
					buf.put(compiledConsts[n].duplicate());
				}
			} else {
				if (!appendSampleResultField(workLoadName, buf,
						evt.getResult(), compiledFields[n])) {
					appendSampleVariable(buf, evt, compiledVars[n]);
				}
			}
		}

		buf.flip();

		try {
			syncWrite(buf);
		} catch (IOException ex) {
			log.error("Problems writing to file", ex);
		}
	}

	private synchronized void syncWrite(ByteBuffer buf) throws IOException {
		try {
			FileLock lock = fileChannel.lock();
			try {
				fileChannel.write(buf);
			} finally {
				lock.release();
			}
		} catch (Exception e) {

			log.info(e.getMessage());

		}
	}

	/*
	 * we work with timestamps, so we assume number > 1000 to avoid tests to be
	 * faster
	 */
	private String getShiftDecimal(long number, int shift) {
		StringBuilder builder = new StringBuilder();
		builder.append(number);

		int index = builder.length() - shift;
		builder.insert(index, ".");

		return builder.toString();
	}

	private void appendSampleVariable(ByteBuffer buf, SampleEvent evt, int varID) {
		if (SampleEvent.getVarCount() < varID + 1) {
			buf.put(("UNDEFINED_variable#" + varID).getBytes());
			log.warn("variable#" + varID + " does not exist!");
		} else {
			if (evt.getVarValue(varID) != null) {
				buf.put(evt.getVarValue(varID).getBytes());
				buf.put(",".getBytes());
			}
		}
	}

	/**
	 * @return boolean true if existing field found, false instead
	 */
	private boolean appendSampleResultField(String workLoadName,
			ByteBuffer buf, SampleResult result, int fieldID) {
		// IMPORTANT: keep this as fast as possible
		switch (fieldID) {
		case 0:
			buf.put(result.isSuccessful() ? b1 : b0);
			buf.put(",".getBytes());
			break;

		case 1:
			buf.put(String.valueOf(result.getStartTime()).getBytes());
			buf.put(",".getBytes());
			break;

		case 2:
			buf.put(String.valueOf(result.getEndTime()).getBytes());
			buf.put(",".getBytes());
			break;

		case 3:
			if (result.getSamplerData() != null) {
				buf.put(String.valueOf(result.getSamplerData().length())
						.getBytes());
				buf.put(",".getBytes());
			} else {
				buf.put(b0);
				buf.put(",".getBytes());
			}
			break;

		case 4:
			if (result.getResponseData() != null) {
				buf.put(String.valueOf(result.getResponseData().length)
						.getBytes());
				buf.put(",".getBytes());
			} else {
				buf.put(b0);
				buf.put(",".getBytes());
			}
			break;

		case 5:
			buf.put(String.valueOf(result.getTime()).getBytes());
			buf.put(",".getBytes());
			break;

		case 6:
			buf.put(String.valueOf(result.getLatency()).getBytes());
			buf.put(",".getBytes());
			break;

		case 7:
			buf.put(result.getResponseCode().getBytes());
			buf.put(",".getBytes());
			break;

		case 8:
			buf.put(result.getResponseMessage().getBytes());
			buf.put(",".getBytes());
			break;

		case 9:
			buf.put(!result.isSuccessful() ? b1 : b0);
			buf.put(",".getBytes());
			break;

		case 10:
			buf.put(result.getThreadName().getBytes());
			buf.put(",".getBytes());
			break;

		case 11:
			buf.put(result.getSampleLabel().getBytes());
			buf.put(",".getBytes());
			break;

		case 12:
			buf.put(getShiftDecimal(result.getStartTime(), 3).getBytes());
			buf.put(",".getBytes());
			break;

		case 13:
			buf.put(getShiftDecimal(result.getEndTime(), 3).getBytes());
			buf.put(",".getBytes());
			break;

		case 14:
			buf.put(String.valueOf(result.getTime() * 1000).getBytes());
			buf.put(",".getBytes());
			break;

		case 15:
			buf.put(String.valueOf(result.getLatency() * 1000).getBytes());
			buf.put(",".getBytes());
			break;

		case 16:
			if (result.getSamplerData() != null) {
				buf.put(result.getSamplerData().getBytes());
				buf.put(",".getBytes());
			} else {
				buf.put(b0);
				buf.put(",".getBytes());
			}
			break;

		case 17:
			buf.put(result.getResponseData());
			buf.put(",".getBytes());
			break;

		case 18:
			buf.put(result.getResponseHeaders().getBytes());
			buf.put(",".getBytes());
			break;

		case 19:
			buf.put(String.valueOf(result.getAllThreads()).getBytes());
			buf.put(",".getBytes());
			break;

		case 20:
			buf.put(String.valueOf(result.getRequestHeaders()).getBytes());
			buf.put(",".getBytes());
			break;

		case 21:
			buf.put(workLoadName.getBytes());
			buf.put(",".getBytes());
			break;

		case 22:
			buf.put(",\n".getBytes());

			break;

		default:
			return false;
		}
		return true;
	}
}
