/**  NoteItem class which is a Java Bean class: it describes each Note.
 * */

package com.anteas.plainolnotes.data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;

public class NoteItem {

	private String key;
	private String text;

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * This method return initiated NoteItem object with two parameters: 
	 * key set to unique timestamp, and value set to empty string.
	 *
	 * @return      the NoteItem item with two parameters: key set to unique timestamp, and value which is an empty string.
	 */
	@SuppressLint("SimpleDateFormat") public static NoteItem getNew(){

		Locale locale = new Locale("en_US"); 
		Locale.setDefault(locale);

		String pattern = "yyyy-MM-dd HH:mm:ss Z"; 
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);

		String key = formatter.format(new Date());

		NoteItem note = new NoteItem(); 
		note.setKey(key); 
		note.setText("");  

		return note;

	}

	@Override
	public String toString() {
		return this.getText();
	}

}
