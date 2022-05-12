package uz.sanjar.note.core.cache;

import android.content.Context;
import android.content.SharedPreferences;

public class SetUpHelper {
    private static SetUpHelper helper;
    private SharedPreferences preferences;

    private SetUpHelper(Context context) {
        //context
        preferences = context.getSharedPreferences("note", Context.MODE_PRIVATE);
    }

    public static void init(Context context) {
        if (helper == null) {
            helper = new SetUpHelper(context);
        }
    }

    public static SetUpHelper getHelper() {
        return helper;
    }

    public boolean getBoard() {
        return preferences.getBoolean("board", false);
    }

    public void setBoard(boolean b) {
        preferences.edit().putBoolean("board", b).apply();
    }
}
