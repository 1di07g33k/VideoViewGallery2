package gallery.video.user.videogallery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.File;
import java.util.ArrayList; import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class VideoGallery extends Activity implements OnItemClickListener {
    Cursor cursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        // We'll use a ListView to display the list of videos.
        ListView listView = (ListView) this.findViewById(R.id.ListView);
        // Next is the list of columns we want from the MediaStore.Video.Thumbnails queries.
        String[] thumbColumns = {

                MediaStore.Video.Thumbnails.DATA,
                MediaStore.Video.Thumbnails.VIDEO_ID
        };
        //Then comes the list of columns we want from the MediaStore.Video.Media query.
        String[] mediaColumns = {
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.MIME_TYPE
        };
        //  In the main query, we'll select all of the videos that are represented in the MediaStore.
        cursor = managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, mediaColumns, null, null, null);
        //Each row returned by the query will create an item in the following ArrayList.
        // Each item will be a VideoViewInfo object,
        // which is a class defined here specifically to hold information about a video for use in this activity.
        ArrayList<VideoViewInfo> videoRows = new ArrayList<VideoViewInfo>();
        //Here we loop through the data contained in the Cursor object,
        // creating a VideoViewInfo object for each row and adding it to our ArrayList.
        if (cursor.moveToFirst()) {
            // We are using a do while loop as we want it to run through
            // the first row of data before moving to the next row.
            // The do portion happens before the while clause is tested/executed.
            // In our loop, we'll create a new VideoViewInfo object for each row of data returned.
            do {
                VideoViewInfo newVVI = new VideoViewInfo();
                //We can then pull out all of the relevant data from the Cursor.
                // As just described, we'll also make another query to pull out a
                // thumbnail image for each video.
                // Each of these pieces of data will be stored in the VideoViewInfo object.

                int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media._ID));
                Cursor thumbCursor = managedQuery(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, thumbColumns, MediaStore.Video.Thumbnails.VIDEO_ID + "=" + id, null, null);
                if (thumbCursor.moveToFirst()) {
                    newVVI.thumbPath = thumbCursor.getString(thumbCursor.getColumnIndex(MediaStore.Video.Thumbnails.DATA));
                    Log.v("VideoGallery", "Thumb " + newVVI.thumbPath);
                    newVVI.filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                    newVVI.title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
                    Log.v("VideoGallery", "Title " + newVVI.title);
                    newVVI.mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
                    Log.v("VideoGallery", "Mime " + newVVI.mimeType);
                    //Finally, we add the VideoViewInfo to the videoRows ArrayList.
                    videoRows.add(newVVI);
                }
            } while (cursor.moveToNext());
        }
        //  Once we are done getting all of the videos, we can continue on.
        // We'll set the adapter of the ListView object to be a new instance of VideoGalleryAdapter,
        // which is an inner class defined here. We'll also set this activity to be the OnItemClickListener for the ListView.
        listView.setAdapter(new VideoGalleryAdapter(this, videoRows));
        listView.setOnItemClickListener(this);
    }

    //  When an item in the ListView is clicked, the onItemClick method will be called.
    // In this method, we extract the data we need from the Cursor and create an intent
    // to launch the default media player application on the device to play back the video.
    // We could have created our own MediaPlayer or used the VideoView class here instead.
    public void onItemClick(AdapterView<?> l, View v, int position, long id) {

        if (cursor.moveToPosition(position)) {

            int fileColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            int mimeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE);

            String videoFilePath = cursor.getString(fileColumn);
            String mimeType = cursor.getString(mimeColumn);

            Intent intent = new Intent(android.content.Intent.ACTION_VIEW);

            File newFile = new File(videoFilePath);
            intent.setDataAndType(Uri.fromFile(newFile), mimeType);

            startActivity(intent);
        }
    }
}

