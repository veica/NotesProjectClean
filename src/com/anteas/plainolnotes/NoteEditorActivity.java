/**  NoteEditorClass class which is a second activity for PlainNotes application.
 *   It provides user interface to manipulate particular note.
 * */

package com.anteas.plainolnotes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import com.anteas.plainolnotes.data.NoteItem;

public class NoteEditorActivity extends Activity {

	private NoteItem note;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_editor);
		getActionBar().setDisplayHomeAsUpEnabled(true); 


		Intent intent = this.getIntent(); 
		note = new NoteItem();
		note.setKey(intent.getStringExtra("key"));
		note.setText(intent.getStringExtra("text"));

		EditText et = (EditText) findViewById(R.id.noteText);
		et.setText(note.getText()); 
		et.setSelection(note.getText().length()); 
	}

	/**
	 * Method gets the data which user entered in EditText, add it to Intent and finishes.
	 * This method is called in two cases: either the user has pressed application button "Back" or device button "Back".
	 */
	private void saveAndFinish(){
		EditText et = (EditText) findViewById(R.id.noteText);
		String noteText = et.getText().toString(); 

		Intent intent = new Intent();
		intent.putExtra("key", note.getKey()); 
		intent.putExtra("text", noteText);
		setResult(RESULT_OK, intent);
		finish(); 

	}


	//	  Method is called the user has pressed application button "Back", meaning, back to Home screen.
	//	  @param MenuItem item which was clicked 


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId()==android.R.id.home) { 
			saveAndFinish();
		}
		return false; 
	}


	// Method is called the user has pressed device button "Back", meaning, the "real button".

	@Override
	public void onBackPressed() {
		saveAndFinish();
	}
}
