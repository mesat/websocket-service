package websocket;

import java.util.Calendar;

import org.apache.tomcat.util.buf.ByteBufferUtils;

import com.fasterxml.jackson.core.util.ByteArrayBuilder;

import ch.qos.logback.core.encoder.ByteArrayUtil;

public class ByteArrayModel {
	private long time;
	private int width;
	private int height;
	private byte[] data ;

	private ByteArrayModel() {
	}

	public ByteArrayModel(byte[] data) {
		setTime(Calendar.getInstance().getTimeInMillis());
		this.data = new byte[data.length];
		ByteArrayBuilder b = new ByteArrayBuilder();
		b.write(data);
		this.data = b.toByteArray();

	}

	public byte[] getData() {
		return data;
	}

	private void setData(byte[] data) {
		this.data = data;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}