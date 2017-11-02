package gallery.video.user.videogallery;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class VideoGalleryAdapter extends BaseAdapter {

    private Context context;

    private List<VideoViewInfo> videoItems;

    LayoutInflater inflater;

    public VideoGalleryAdapter(Context _context, ArrayList<VideoViewInfo> _items) {
        context = _context;
        videoItems = _items;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {

        return videoItems.size();
    }

    public Object getItem(int position) {
        return videoItems.get(position);
    }

    public long getItemId(int position) {
        return position;
    }
    //    The getView method is used to return the view for each row represented in the ListView. It is passed in the position that is meant to be returned (along with a View object representing the current View and an object that represents the parent ViewGroup).

    public View getView(int position, View convertView, ViewGroup parent) {

        //To construct the View to be returned, we need to inflate the layout that we are using for each row. In this case, we are using a layout defined in list_item.xml (shown here).

        View videoRow = inflater.inflate(R.layout.list_item, null);

        //After the layout is inflated, we can get at the individual Views that are defined and use the data from the ArrayList of VideoViewInfo objects to define what to display. Here is how that is done for the ImageView that is used to display each video's thumbnail.

        ImageView videoThumb = (ImageView) videoRow.findViewById(R.id.ImageView);
        if (videoItems.get(position).thumbPath != null) {

            videoThumb.setImageURI(Uri.parse(videoItems.get(position).thumbPath));

            //Here we obtain a reference to the TextView for the video title and set the text according to the data in the ArrayList of VideoViewInfo object.
            TextView videoTitle = (TextView) videoRow.findViewById(R.id.TextView);
            videoTitle.setText(videoItems.get(position).title);

            //Finally, we return the newly constructed View.


        }
        return videoRow;
    }
}
