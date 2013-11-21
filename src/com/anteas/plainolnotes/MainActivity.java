/**  MainActivity class which is a first activity for PlainNotes application.
 *   It provides user interface to manipulate all notes: edit, delete, add new one, update existing one.
 * */
package com.anteas.plainolnotes;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.anteas.plainolnotes.data.NoteItem;
import com.anteas.plainolnotes.data.NotesDataSource;

public class MainActivity extends ListActivity {

	private static final int MY_REQ_CODE = 1001;
	private NotesDataSource datasource;
	List<NoteItem> notes;

	private static final int MENU_DELETE_ID=1002;
	private int currentNodeId; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		registerForContextMenu(getListView());    


		datasource = new NotesDataSource(this); 
		refreshDisplay();


	}


	//	   Method refresh a display: it gets the data using the findAll method, wraps the adapter around it 
	//	   and attaches the adapter to the current list activity. 
	//	   It's called everytime the changes has been made (updating a note, adding a new note, deleting a note).

	private void refreshDisplay() {
		notes = datasource.findAll();

		ArrayAdapter <NoteItem> adapter =
				new ArrayAdapter<NoteItem>(this, R.layout.list_item_layout, notes);

		setListAdapter(adapter);

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId()==R.id.action_create) {
			createNote();
		}
		return super.onOptionsItemSelected(item);
	}


	//	  Starts a new Intent, creates new NoteItem and send it to the second Activity.
	//	  And wait for result: RESULT_OK

	private void createNote() {
		NoteItem note = NoteItem.getNew();
		Intent intent = new Intent(this, NoteEditorActivity.class); 
		intent.putExtra("key", note.getKey()); 
		intent.putExtra("text", note.getText()); 
		startActivityForResult(intent, MY_REQ_CODE); 
	}

	//	  Starts a new Intent, gets a NoteItem that was "clicked" by a user and send it to the second Activity.
	//	  And waits for result: RESULT_OK

	@Override 
	protected void onListItemClick(ListView l, View v, int position, long id) {
		NoteItem note = notes.get(position); 
		Intent intent = new Intent(this, NoteEditorActivity.class); 
		intent.putExtra("key", note.getKey());
		intent.putExtra("text", note.getText()); 
		startActivityForResult(intent, MY_REQ_CODE); 
	}

	// Method waits for the result: RESULT_OK
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode== MY_REQ_CODE && resultCode==RESULT_OK) {    
			NoteItem note = new NoteItem();
			note.setKey(data.getStringExtra("key"));
			note.setText(data.getStringExtra("text"));
			datasource.update(note);
			refreshDisplay(); // 
		}
	}

	// Adding an DELETE option as a ContextMenu, or a "long click" and getting an ID of a note that is supposed to be deleted.
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo; 
		currentNodeId =(int) info.id;

		menu.addSubMenu(0, MENU_DELETE_ID, 0, "Delete");   
	}

	// Deleting a note.
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId()==MENU_DELETE_ID) {
			NoteItem note = notes.get(currentNodeId);
			datasource.remove(note);
			refreshDisplay();

		}
		return super.onContextItemSelected(item);
	}
}
