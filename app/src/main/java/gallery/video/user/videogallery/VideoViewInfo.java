package gallery.video.user.videogallery;

public class VideoViewInfo {

    String filePath;
    String mimeType;
    String thumbPath;
    String title;

    //Since we are using a ListView in our activity to display each of the videos returned from the MediaStore query, we'll be using the ListView to display both the title of the video and a thumbnail. In order to hand the data to the ListView, we need to construct an Adapter. Next, we create an Adapter, VideoGalleryAdapter, which extends BaseAdapter. When this class is constructed, it gets passed the ArrayList that holds all of the videos returned from the MediaStore query.

    //BaseAdapter is an abstract class, so in order to extend it, we need to implement several methods. Most of them are straightforward and just operate on the ArrayList we passed in, such as getCount and getItem. The method that requires the most attention is the getView method.
}