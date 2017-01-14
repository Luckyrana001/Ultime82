package utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.http.HttpResponseCache;
import android.os.Build;
import android.os.StatFs;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.widget.Button;

import com.extect.appbase.BaseApplication;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.UrlConnectionDownloader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import shared.LibConstants;
import shared.BaseFlyContext;

/**
 * Created by Sanka on 9/23/16.
 */
public class Utils {
//content://media/external/images/media/45350

    public static String getRealPathFromURI_API19(Context context, Uri uri) {

        String filePath = "";
        if (uri.getHost().contains("com.android.providers.media")) {
            // Image pick from recent
            String wholeID = DocumentsContract.getDocumentId(uri);

            // Split at colon, use second item in the array
            String id = wholeID.split(":")[1];

            String[] column = {MediaStore.Images.Media.DATA};

            // where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";

            Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    column, sel, new String[]{id}, null);

            int columnIndex = cursor.getColumnIndex(column[0]);

            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
            return filePath;
        }
        else if (uri.getHost().contains("media")){
            Cursor cursor = null;
            try {
                String[] proj = { MediaStore.Images.Media.DATA };
                cursor = context.getContentResolver().query(uri, proj, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                return cursor.getString(column_index);
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
//        else if (uri.getHost().contains("com.google.android.apps.docs.storage")){
//
//            // Image pick from recent
//            String wholeID = DocumentsContract.getDocumentId(uri);
//
//            // Split at colon, use second item in the array
//            String id = wholeID.split(":")[1];
//
//            String[] column = {MediaStore.Images.Media.DATA};
//
//            // where id is equal to
//            String sel = MediaStore.Images.Media._ID + "=?";
//
//            Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                    column, sel, new String[]{id}, null);
//
//            int columnIndex = cursor.getColumnIndex(column[0]);
//
//            if (cursor.moveToFirst()) {
//                filePath = cursor.getString(columnIndex);
//            }
//            cursor.close();
//            return filePath;
//        }
        else {
            return "";
        }
    }

    public static Bitmap getBitmapAPI_24(String fileUri,Uri sArtworkUri){

        Uri uris = ContentUris.withAppendedId(sArtworkUri,
                Long.parseLong(fileUri));

        ContentResolver res = BaseFlyContext.getInstant().getActivity().getContentResolver();
        InputStream in = null;
        try { in = res.openInputStream(uris);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Bitmap artwork = BitmapFactory.decodeStream( in );
        return artwork;
    }

    public static boolean isNullOrEmptyString(String s) {
        if (s == null || s.trim().length() == 0 || s.equalsIgnoreCase("null"))
            return true;
        return false;
    }

    public static boolean isNetworkConnected(Context context, boolean isShowMessage,int teamId) {
        return isNetworkConnected(context, isShowMessage, false,teamId);
    }

    public static boolean isNetworkConnected(Context context,boolean isShowMessage, boolean isClose,int teamId) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            String message = "";
            boolean airPlaneMode = Settings.System.getInt(context.getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON, 0) != 0;
            if (airPlaneMode) {
                message = "Airplane Mode Turn On, Use Wi-Fi or Cellular data to Access Data";
            } else {
                message = "No network!!! Please connect to internet.";
            }

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null
                    && activeNetwork.isConnected();
            if (!isConnected && isShowMessage) {
                showNoNetworkMessage(context, isClose, message,teamId);
            }
            return isConnected;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private static AlertDialog showNoNetworkMessage(final Context context, final boolean isClose, String message,int teamId) {
        AlertDialog alert = new AlertDialog.Builder(BaseFlyContext.getInstant().getActivity(),teamId)
                .setMessage(message)
                .setPositiveButton("Settings", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // bug fixed MORE-572
						/*Intent intent = new Intent(
								Settings.ACTION_WIFI_SETTINGS);
						*/
                        Intent intent = new Intent(
                                Settings.ACTION_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        dialog.dismiss();
                    }
                }).setNegativeButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (isClose && context instanceof Activity) {
                            ((Activity) context).finish();
                        }
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert).show();
        alert.setCancelable(false);
        alert.setCanceledOnTouchOutside(true);
        customAlertDialog(context, alert);
        return alert;
    }

    public static void customAlertDialog(Context context,
                                         AlertDialog alertDialog) {
        if (alertDialog != null) {
            Button b = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            if (b != null) {
                // b.setTextColor(getColorGroup4(context));
                // b.setTypeface(getBoldFont(context));
            }
            b = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            if (b != null) {
                //  b.setTextColor(getColorGroup4(context));
                //  b.setTypeface(getBoldFont(context));
            }
        }
    }


    public static Picasso getPicasso(Context context) {
        return BaseApplication.getPicasso(context);
    }


    /**
     * The Class UrlCustomDownloader.
     */
    public static class UrlCustomDownloader extends UrlConnectionDownloader {

        /**
         * The Class ResponseCacheIcs.
         */
        private static class ResponseCacheIcs {

            /**
             * Install.
             *
             * @param context
             *            the context
             * @return the object
             * @throws IOException
             *             Signals that an I/O exception has occurred.
             */
            static Object install(Context context) throws IOException {
                File cacheDir = createDefaultCacheDir(context);
                HttpResponseCache cache = HttpResponseCache.getInstalled();
                if (cache == null) {
                    long maxSize = calculateDiskCacheSize(cacheDir);
                    cache = HttpResponseCache.install(cacheDir, maxSize);
                }
                return cache;
            }
        }

        /** The response source. */
        final String RESPONSE_SOURCE = "X-Android-Response-Source";

        /** The cache. */
        volatile Object cache;

        /** The lock. */
        private Object lock = new Object();

        /** The force cache. */
        private String FORCE_CACHE = "only-if-cached,max-age=2147483647";

        /** The picasso cache. */
        private static String PICASSO_CACHE = "picasso-cache";

        /** The min disk cache size. */
        private static int MIN_DISK_CACHE_SIZE = 5 * 1024 * 1024; // 5MB

        /** The max disk cache size. */
        private static int MAX_DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB

        /**
         * Calculate disk cache size.
         *
         * @param dir
         *            the dir
         * @return the long
         */
        static long calculateDiskCacheSize(File dir) {
            long size = MIN_DISK_CACHE_SIZE;

            try {
                StatFs statFs = new StatFs(dir.getAbsolutePath());
                long available = ((long) statFs.getBlockCount())
                        * statFs.getBlockSize();
                // Target 2% of the total space.
                size = available / 50;
            } catch (IllegalArgumentException ignored) {
            }

            // Bound inside min/max size for disk cache.
            return Math.max(Math.min(size, MAX_DISK_CACHE_SIZE),
                    MIN_DISK_CACHE_SIZE);
        }

        /**
         * Creates the default cache dir.
         *
         * @param context
         *            the context
         * @return the file
         */
        static File createDefaultCacheDir(Context context) {
            File cache = new File(
                    // TODO cache dir here
					/* context.getExternalCacheDir().getAbsolutePath() */
                    context.getApplicationContext().getCacheDir(),
                    PICASSO_CACHE);
            if (!cache.exists()) {
                // noinspection ResultOfMethodCallIgnored
                cache.mkdirs();
            }
            return cache;
        }

        /**
         * Returns {@code true} if header indicates the response body was loaded
         * from the disk cache.
         *
         * @param header
         *            the header
         * @return true, if successful
         */
        static boolean parseResponseSourceHeader(String header) {
            if (header == null) {
                return false;
            }
            String[] parts = header.split(" ", 2);
            if ("CACHE".equals(parts[0])) {
                return true;
            }
            if (parts.length == 1) {
                return false;
            }
            try {
                return "CONDITIONAL_CACHE".equals(parts[0])
                        && (Integer.parseInt(parts[1]) == 304);
            } catch (NumberFormatException e) {
                return false;
            }
        }

        /** The cache header builder. */
        private ThreadLocal<StringBuilder> CACHE_HEADER_BUILDER = new ThreadLocal<StringBuilder>() {
            @Override
            protected StringBuilder initialValue() {
                return new StringBuilder();
            }
        };

        /** The context. */
        private final Context context;

        /** The result bitmap. */
        Bitmap resultBitmap = null;

        /**
         * Instantiates a new url custom downloader.
         *
         * @param context
         *            the context
         */
        public UrlCustomDownloader(Context context) {
            super(context);
            this.context = context;
        }

        /**
         * Install cache if needed.
         *
         * @param context
         *            the context
         */
        private void installCacheIfNeeded(Context context) {
            // DCL + volatile should be safe after Java 5.
            if (this.cache == null) {
                try {
                    synchronized (this.lock) {
                        if (this.cache == null) {
                            this.cache = ResponseCacheIcs.install(context);
                        }
                    }
                } catch (IOException ignored) {
                }
            }
        }

        @Override
        public Response load(Uri uri, int networkPolicy) throws IOException {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                installCacheIfNeeded(this.context);
            }

            HttpURLConnection connection = openConnection(uri);
            connection.setUseCaches(true);
            String token = SettingServices.getInstance().getUserToken(context);
            if (!Utils.isNullOrEmptyString(token)) {
                connection.setRequestProperty(
                        LibConstants.TOKEN_HEADER_AUTHORIZATION, token);
            }
            HttpURLConnection.setFollowRedirects(true);
            //HttpsTrustManager.allowAllSSL();

            if (networkPolicy != 0) {

                String headerValue;

                if (NetworkPolicy.isOfflineOnly(networkPolicy)) {
                    headerValue = this.FORCE_CACHE;
                } else {
                    StringBuilder builder = this.CACHE_HEADER_BUILDER.get();
                    builder.setLength(0);

                    if (!NetworkPolicy.shouldReadFromDiskCache(networkPolicy)) {
                        builder.append("no-cache");
                    }
                    if (!NetworkPolicy.shouldWriteToDiskCache(networkPolicy)) {
                        if (builder.length() > 0) {
                            builder.append(',');
                        }
                        builder.append("no-store");
                    }

                    headerValue = builder.toString();
                }

                connection.setRequestProperty("Cache-Control", headerValue);
            }

            int responseCode = connection.getResponseCode();
			/*
			 * if (responseCode == 302) { String newUrl =
			 * connection.getHeaderField("Location"); newUrl =
			 * newUrl.replace("https", "http"); uri = Uri.parse(newUrl);
			 * load(uri, networkPolicy); } else
			 */if (responseCode >= 300) {
                connection.disconnect();
                throw new ResponseException(responseCode + " "
                        + connection.getResponseMessage(), networkPolicy,
                        responseCode);
            }

            long contentLength = connection.getHeaderFieldInt("Content-Length",
                    -1);
            boolean fromCache = parseResponseSourceHeader(connection
                    .getHeaderField(this.RESPONSE_SOURCE));
            Response result = null;
            try {
                //if (this.resultBitmap == null) {
                InputStream imageStream = connection.getInputStream();
                imageStream.mark(imageStream.available());
                BufferedInputStream bufferedInputStream = new BufferedInputStream(
                        imageStream);

                this.resultBitmap = BitmapFactory
                        .decodeStream(bufferedInputStream);
                //}

                result = new Response(this.resultBitmap, fromCache,
                        contentLength);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

    }

    public static Bitmap getBitmapRotateMatrix(String src,Bitmap bitmap) {

        Bitmap resizedBitmap = null;
        try {
            int orientation = getExifOrientation(src);

            Matrix matrix = new Matrix();
            if (orientation == 1) {
                return bitmap;
            }

            switch (orientation) {
                case 2:
                    matrix.setScale(-1, 1);
                    break;
                case 3:
                    matrix.setRotate(180);
                    break;
                case 4:
                    matrix.setRotate(180);
                    matrix.postScale(-1, 1);
                    break;
                case 5:
                    matrix.setRotate(90);
                    matrix.postScale(-1, 1);
                    break;
                case 6:
                    matrix.setRotate(90);
                    break;
                case 7:
                    matrix.setRotate(-90);
                    matrix.postScale(-1, 1);
                    break;
                case 8:
                    matrix.setRotate(-90);
                    break;
                default:
                    return bitmap;
            }
            resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        }catch (Exception e){
            e.printStackTrace();
        }
        return resizedBitmap;
    }

    public static String getDateFormatted(String dateString,String format,String requestFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        try {
            Date date = sdf.parse(dateString);
            sdf = new SimpleDateFormat(requestFormat, Locale.getDefault());
            String s = sdf.format(date);
            return s;
        } catch (ParseException e) {
            return dateString;
        }
    }

    private static int getExifOrientation(String src) throws IOException {
        int orientation = 1;

        ExifInterface exif = new ExifInterface(src);
        String orientationString=exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        try {
            orientation = Integer.parseInt(orientationString);
        }
        catch(NumberFormatException e){}

        return orientation;
    }
}
