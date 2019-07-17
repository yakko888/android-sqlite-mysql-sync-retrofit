package net.simplifiedcoding.androidmysqlsync;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Belal on 1/27/2017.
 */

public class NetworkStateChecker extends BroadcastReceiver {

    //context and database helper object
    private Context context;
    private DatabaseHelper db;

    APIInterface apiInterface;
    Retrofit retrofit;
    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;

        apiInterface = APIClient.getClient().create(APIInterface.class);

        db = new DatabaseHelper(context);

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        //if there is a network
        if (activeNetwork != null) {
            //if connected to wifi or mobile data plan
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {

                //getting all the unsynced names
                Cursor cursor = db.getUnsyncedNames();
                if (cursor.moveToFirst()) {
                    do {
                        //calling the method to save the unsynced name to MySQL
                        saveName(
                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME))
                        );
                    } while (cursor.moveToNext());
                }
            }
        }
    }

    /*
    * method taking two arguments
    * name that is to be saved and id of the name from SQLite
    * if the name is successfully sent
    * we will update the status as synced in SQLite
    * */
    private void saveName(final int id, final String name) {
        //Call call = retrofit.create(APIInterface.class).saveName(name);
       Call call = apiInterface.saveName(name);
        call.enqueue(new Callback<ResponseName>() {
            @Override
            public void onResponse(Call<ResponseName> call, Response<ResponseName> response) {
                ResponseName mResponseName = response.body();
                if(!mResponseName.isError()){
                    //updating the status in sqlite
                    db.updateNameStatus(id, MainActivity.NAME_SYNCED_WITH_SERVER);
                    //sending the broadcast to refresh the list
                    context.sendBroadcast(new Intent(MainActivity.DATA_SAVED_BROADCAST));
                }

            }

            @Override
            public void onFailure(Call<ResponseName> call, Throwable t) {

            }
        });
    }

}
