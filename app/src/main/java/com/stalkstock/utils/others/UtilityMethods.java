package com.stalkstock.utils.others;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Environment;
import android.text.format.DateUtils;
import android.util.DisplayMetrics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sonal grover.
 * Contact on sonal.grover12@gmail.com
 */
public class UtilityMethods {
    private static UtilityMethods mInstance;
    private static Context mContext;
    public static String txtNumberofEvents="txtNumberofEvents";
    public static String txtNumberofUsers="txtNumberofUsers";
    public static File myDir_omorni = new File(Environment.getExternalStoragePublicDirectory("Apollo"), "Data");

    public static String getPhoneCurrentDate() {
        Date mCurrent_Time = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        return df.format(mCurrent_Time);
    }


    public static boolean isValidEmail(String emailInput) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(emailInput);
        return matcher.matches();
    }
    public static File createTemporalFileFrom(InputStream inputStream, Context context) throws IOException {
        File targetFile = null;

        if (inputStream != null) {
            int read;
            byte[] buffer = new byte[8 * 1024];

            targetFile = createTemporalFile(context);
            OutputStream outputStream = new FileOutputStream(targetFile);

            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();

            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return targetFile;
    }

    public static File createTemporalFile(Context context) {
        return new File(context.getExternalCacheDir(), System.currentTimeMillis() + ".jpg"); // context needed
    }


    public static String getImagePathFromInputStreamUri(Context context, Uri uri) {
        InputStream inputStream = null;
        String filePath = null;

        if (uri.getAuthority() != null) {
            try {
                inputStream = context.getContentResolver().openInputStream(uri); // context needed
                File photoFile = createTemporalFileFrom(inputStream, context);

                filePath = photoFile.getPath();

            } catch (FileNotFoundException e) {
                // log
            } catch (IOException e) {
                // log
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return filePath;
    }


    public static UtilityMethods getInstance() {
        if (mInstance == null) {
            mInstance = new UtilityMethods();
        } else {
            return mInstance;
        }
        return mInstance;
    }
    public  static String getFormattedDate(String dateStr) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dayf = new SimpleDateFormat("dd");
        SimpleDateFormat df1 = new SimpleDateFormat("EEE,"+"dd'" + "' MMM yyyy");
        df1.setTimeZone(TimeZone.getDefault());
        String formattedDate = df1.format(date);
        return formattedDate;
    }

    public static void init(Context mContext) {
        UtilityMethods.mContext = mContext;
    }

    private static double ConvertMetersToKm(double m) {
        return (m / 1000);
    }

    private static double ConvertMetersToMiles(double meters) {
        return (meters / 1609.344);
    }

    public static long getDateTimeMilliString(String dateStr, String inputFormat) {
        SimpleDateFormat df = new SimpleDateFormat(inputFormat, Locale.ENGLISH);
        Date date = null;
        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date == null) {
            return 0;
        }
        return date.getTime();
    }

    public static String getFileExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
    }

    public static Context getContext() {
        return mContext;
    }

    public static int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int pxToDp(int px) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static boolean isValidYoutubeLink(String videoUrl) {
        String vId = null;
        Pattern pattern = Pattern.compile(
                "^(http(s)?:\\/\\/)?((w){3}.)?youtu(be|.be)?(\\.com)?\\/.+",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(videoUrl);
        if (matcher.matches()) {
            vId = matcher.group(1);
        }

        return (vId != null && vId.length() > 0);

    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @return A float value to represent dp equivalent to px value
     */
    public float convertPixelsToDp(float px) {
        Resources resources = mContext.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public float convertDpToPixel(float dp) {
        Resources resources = mContext.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    public int convertDpToPixelInt(float dp) {
        Resources resources = mContext.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        int px = (int) (dp * (metrics.densityDpi / 160f));
        return px;
    }

    /**
     * Check the service is currently running or not on android system.
     *
     * @param serviceClass class name
     * @return true/false
     */
    public boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager
                .getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public long getMillisFromUTCString(String dateStr, String format) {

        return getMillisFromUTCString(dateStr, format, false);
    }

    public long getMillisFromUTCString(String dateStr, boolean convertToLocal) {

        return getMillisFromUTCString(dateStr, "yyyy-MM-dd HH:mm:ss", convertToLocal);
    }

    public long getMillisFromUTCString(String dateStr, String format, boolean convertToLocal) {
        //yyyy-MM-dd HH:mm:ss
        SimpleDateFormat df = new SimpleDateFormat(format);
        if (convertToLocal) df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date == null) {
            return 00000;
        }
        return date.getTime();
    }

    public String convertLocalTimeToUTC(String dateStr, String inputFormat) {
        long localTime = getMillisFromUTCString(dateStr, inputFormat, false);
        SimpleDateFormat sdf = new SimpleDateFormat(inputFormat);
        // Convert Local Time to UTC (Works Fine)
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(localTime);
    }

    public String convertLocalTimeToUTC(long millis, String outputFormat) {

        SimpleDateFormat sdf = new SimpleDateFormat(outputFormat);
        // Convert Local Time to UTC (Works Fine)
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(millis);
    }

    public String getFormattedString(long time, String outputFormat) {
        return getFormattedString(time, outputFormat, null);
    }

    public String getFormattedString(long time, String outputFormat, String timeZone) {
        SimpleDateFormat df = new SimpleDateFormat(outputFormat);
        if (timeZone != null) {
            df.setTimeZone(TimeZone.getTimeZone(timeZone));
        }
        return df.format(time);
    }


    private String getDayNumberSuffix(int day) {
        if (day >= 11 && day <= 13) {
            return "th";
        }
        switch (day % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }


    public String getRemainingTime(String time, boolean b) {
        // it comes out like this 2013-08-31 15:55:22 so adjust the date format
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (b) {
            df.setTimeZone(TimeZone.getTimeZone("UTC"));
        }
        Date date = null;
        long epoch = 0;
        try {
            date = df.parse(time);

            epoch = date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }

        CharSequence timePassedString = DateUtils.getRelativeTimeSpanString(epoch, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        return timePassedString.toString();
    }

    public String getRemainingTime_format(long time, boolean b) {
        // it comes out like this 2013-08-31 15:55:22 so adjust the date format
        SimpleDateFormat df1 = new SimpleDateFormat("MMM dd,yyyy");
        df1.setTimeZone(TimeZone.getDefault());
        String formattedDate = df1.format(time);
        return formattedDate;
    }

    public String getRemainingTime(long time, boolean b) {
        // it comes out like this 2013-08-31 15:55:22 so adjust the date format
        long epoch = 0;
        epoch = time;
        if (epoch > System.currentTimeMillis()) {
            epoch = System.currentTimeMillis() - 2000;
        }
        CharSequence timePassedString = DateUtils.getRelativeTimeSpanString(epoch, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        return timePassedString.toString();
    }

    public String formatChatTime(long time) {
        // it comes out like this 2013-08-31 15:55:22 so adjust the date format

        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        return dateFormat.format(time);
    }

    public String getPastTimeString(long time) {
        long mMillies = time / 1000;
        long seconds = mMillies % 60;
        long minutes = (mMillies / 60) % 60;
        long hours = (mMillies / (60 * 60)) % 24;
        long days = (mMillies / (60 * 60 * 24)) % 7;

        String commenttimeago;
        if (minutes > 0) {
            if (hours > 0) {
                if (days > 0) {
                    if (days > 1)
                        commenttimeago = days + " days ago";
                    else commenttimeago = days + " day ago";
                } else {
                    if (hours > 1)
                        commenttimeago = hours + " hours ago";
                    else commenttimeago = hours + " hour ago";
                }
            } else {
                if (minutes > 1) {
                    commenttimeago = minutes + " minutes ago";
                } else {
                    commenttimeago = minutes + " minute ago";
                }
            }
        } else {
//            commenttimeago = seconds + " secs ago";
            commenttimeago = "0 minute ago";
        }

        return commenttimeago;
    }

    /**
     * Calculates distance between two location points.
     *
     * @param lat1       latitude of first point
     * @param lng1       longitude of first point
     * @param lat2       latitude of second point
     * @param lng2       longitude of second point
     * @param unitOutput required output in miles(unitOutput=mile),meters(unitOutput=m) and km(unitOutput=km) use @see hangar.nightway.mUtils.Utils.Unit com.{@CO Double}
     * @return give distance between given points in {@link Double}
     */
    public double getdistFrom(double lat1, double lng1, double lat2, double lng2, String unitOutput) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        float dist = (float) (earthRadius * c);
        double miles = dist * 0.00062137119;
        if (unitOutput.equalsIgnoreCase("mile")) {
            return Double.valueOf(String.format("%.2f", miles));
        }
        if (unitOutput.equalsIgnoreCase("m")) {
            return Double.valueOf(String.format("%.2f", dist));
        }
        if (unitOutput.equalsIgnoreCase("km")) {
            return Double.valueOf(String.format("%.2f", ConvertMetersToKm(dist)));
        }
        return miles;
    }

    public String capFirstChar(String s) {
        if (s != null) {
            return s.substring(0, 1).toUpperCase() + s.substring(1);
        }
        return "";

    }

    public String getJsonFromFile(String fileName) {
        String json = null;
        try {
            InputStream is = mContext.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }

}
