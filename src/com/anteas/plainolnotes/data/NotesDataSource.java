package com.anteas.plainolnotes.data;

/**NotesDataSource is a class which manages NoteItem-s and connect this application with SharedPreferences. 
 * */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import android.content.Context;
import android.content.SharedPreferences;

public class NotesDataSource {

	private static final String PREFKEY = "notes";
	private SharedPreferences notePrefs;


	public NotesDataSource(Context context){
		notePrefs = context.getSharedPreferences(PREFKEY, Context.MODE_PRIVATE);
	}

	/**
	 * 
	 * Method return the List of NoteItem-s retrieved from SharedPreferences set in order from oldest to newest.
	 *
	 * @return     the List of NoteItem set in order
	 */
	public List<NoteItem> findAll(){



		Map<String, ?> notesMap = notePrefs.getAll(); 
		SortedSet<String> keys = new TreeSet<String>(notesMap.keySet());

		List<NoteItem> noteList = new ArrayList<NoteItem>();
		for (String key : keys) {
			NoteItem note = new NoteItem();
			note.setKey(key);
			note.setText((String) notesMap.get(key));
			noteList.add(note);
		}

		return noteList;
	}

	/**
	 * Adds NoteItem object to SharedPreferences
	 * @param NoteItem object that is supposed to be added to SharedPreferences
	 * @return boolean value if adding object to SharedPreferences was a success 
	 */
	public boolean update (NoteItem note){

		SharedPreferences.Editor editor = notePrefs.edit();
		editor.putString(note.getKey(), note.getText());
		editor.commit();
		return true;
	}

	/**
	 * Removes NoteItem object to SharedPreferences
	 * @param NoteItem object that is supposed to be deleted from SharedPreferences
	 * @return boolean value if deleting object from SharedPreferences was a success 
	 */
	public boolean remove(NoteItem note){

		if (notePrefs.contains(note.getKey())) {
			SharedPreferences.Editor editor = notePrefs.edit();
			editor.remove(note.getKey());
			editor.commit();
		}

		return true;
	}
}
