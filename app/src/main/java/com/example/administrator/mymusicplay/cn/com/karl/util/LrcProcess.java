package com.example.administrator.mymusicplay.cn.com.karl.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * �������ļ�����
 */
public class LrcProcess {

	private List<LrcContent> LrcList;

	private LrcContent mLrcContent;

	public LrcProcess() {

		mLrcContent = new LrcContent();
		LrcList = new ArrayList<LrcContent>();
	}

	/**
	 * ��ȡ����ļ�������
	 */
	public String readLRC(String song_path) {
		// public void Read(String file){

		StringBuilder stringBuilder = new StringBuilder();

		File f = new File(song_path.replace(".mp3", ".lrc"));
		

		try {
			FileInputStream fis = new FileInputStream(f);
			InputStreamReader isr = new InputStreamReader(fis, "GB2312");
			BufferedReader br = new BufferedReader(isr);
			String s = "";
			while ((s = br.readLine()) != null) {
				// if ((s.indexOf("[ar:") != -1) || (s.indexOf("[ti:") != -1)
				// || (s.indexOf("[by:") != -1)) {
				// s = s.substring(s.indexOf(":") + 1, s.indexOf("]"));
				// } else {
				// try {
				// String ss = s.substring(s.indexOf("["),
				// s.indexOf("]") + 1);
				// s = s.replace(ss, "");
				// } catch (Exception e) {
				// s = "     ";
				// }
				// }
				// stringBuilder.append(s + "\n");

				// �滻�ַ�
				s = s.replace("[", "");
				s = s.replace("]", "@");

				// ����"@"�ַ�
				String splitLrc_data[] = s.split("@");
				if (splitLrc_data.length > 1) {
					mLrcContent.setLrc(splitLrc_data[1]);

					// ������ȡ�ø���ʱ��
					int LrcTime = TimeStr(splitLrc_data[0]);

					mLrcContent.setLrc_time(LrcTime);

					// ��ӽ��б�����
					LrcList.add(mLrcContent);

					// ��������
					mLrcContent = new LrcContent();
				}

			}
			br.close();
			isr.close();
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			stringBuilder.append("ľ�и���ļ����Ͻ�ȥ���أ�...");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			stringBuilder.append("ľ�ж�ȡ����ʰ���");
		}
		return stringBuilder.toString();
	}

	/**
	 * ��������ʱ�䴦����
	 */
	public int TimeStr(String timeStr) {

		timeStr = timeStr.replace(":", ".");
		timeStr = timeStr.replace(".", "@");

		String timeData[] = timeStr.split("@");

		// ������֡��벢ת��Ϊ����
		int minute = Integer.parseInt(timeData[0]);
		int second = Integer.parseInt(timeData[1]);
		int millisecond = Integer.parseInt(timeData[2]);

		// ������һ������һ�е�ʱ��ת��Ϊ������
		int currentTime = (minute * 60 + second) * 1000 + millisecond * 10;

		return currentTime;
	}

	public List<LrcContent> getLrcContent() {

		return LrcList;
	}

	/**
	 * ��ø�ʺ�ʱ�䲢���ص���
	 */
	public class LrcContent {
		private String Lrc;
		private int Lrc_time;

		public String getLrc() {
			return Lrc;
		}

		public void setLrc(String lrc) {
			Lrc = lrc;
		}

		public int getLrc_time() {
			return Lrc_time;
		}

		public void setLrc_time(int lrc_time) {
			Lrc_time = lrc_time;
		}
	}

}
