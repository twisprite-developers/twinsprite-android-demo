package com.twinsprite.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.twinsprite.RedemptionRequest;
import com.twinsprite.TwinSpriteContext;
import com.twinsprite.Twinsprite;
import com.twinsprite.TwinspriteException;
import com.twinsprite.callback.TwinspriteCallback;
import com.twinsprite.callback.TwinspriteToyxCallback;
import com.twinsprite.entity.Toyx;
import com.twinsprite.test.nfc.NfcActivity;

public class MainActivity extends Activity {

	// TWINSPRITE DEMO
	private String API_KEY  	= "YOUR_API_KEY";
	private String SECRET_KEY   = "YOUR_SECRET_KEY";
	private String TOYX_ID 		= ""; //TOYX_ID will be read from the Edit Text Box
	
	private Toyx myToyx 			= null;
	private Context myContext 		= this;
	private ProgressDialog progress = null;
	private EditText etToyxId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		progress = new ProgressDialog(this);
		etToyxId = (EditText)findViewById(R.id.editTextToyxId);

        // INITIALIZING
        Twinsprite.initialize(this, API_KEY, SECRET_KEY);
        Twinsprite.SetLogLevel(TwinSpriteContext.TwinSpriteInfoLog);
	}

	/*
	 * Scan QR code which contain toyx id
	 * view is the button from layout
	 */
	public void scanQR(View view) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                try {

                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                    startActivityForResult(intent, 1);

                } catch (Exception e) {

                    Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
                    startActivity(marketIntent);
                }

            }
        });
	}
	
	/*
	 * Scan NFC which contain toyx id
	 * view is the button from layout
	 */
	public void scanNFC(View view) {
		Intent intent = new Intent(this, NfcActivity.class);
		startActivityForResult(intent, 1);
	}
	
	/*
	 * Create session with this device and for this toyx id
	 * view is the button from layout
	 */
	public void createSession(View view) {
		TOYX_ID = etToyxId.getText().toString();
		if(API_KEY.compareTo("") == 0 || SECRET_KEY.compareTo("") == 0){
			showDialog("Warning", "The SECRET_KEY or the API_KEY are invalid");
		}else if(TOYX_ID.compareTo("") == 0 ){
			showDialog("Warning", "The toyxId is empty, Scan first a toyx");
		}else{
			// show progress dialog
			progress.setTitle("Loading");
            progress.setMessage("Creating session...");
            progress.show();

			// CREATE TOYX
			myToyx = new Toyx(TOYX_ID);
			
			// CREATE SESSION
			myToyx.createSessionInBackground(new TwinspriteCallback() {
                @Override
                public void onFinish(TwinspriteException e) {
                    progress.dismiss();
                    if(e == null){
                        showDialog("Warning", "Session Created" + myToyx.toString());
                    }else{
                        showDialog("Warning", "CreateSession error: "+e.getDetailMessage());
                    }
                }
			});
		}
	}
	
	/*
	 * fetch the toyx with all its datas
	 * view is the button from layout
	 */
	public void fetch(View view) {
		TOYX_ID = etToyxId.getText().toString();
		if(API_KEY.compareTo("") == 0 || SECRET_KEY.compareTo("") == 0){
			showDialog("Warning", "The SECRET_KEY or the API_KEY are invalid");
		}else if(TOYX_ID.compareTo("") == 0  || myToyx == null){
			showDialog("Warning", "Scan or enter a valid toyx id and Create Session first");
		}else{
			// show progress dialog
			progress.setTitle("Loading");
			progress.setMessage("Fetching...");
			progress.show();
			
			// FETCH
			myToyx.fetchIfNeededInBackground(new TwinspriteCallback() {
                @Override
                public void onFinish(TwinspriteException e) {
                    progress.dismiss();
                    if (e == null) {
                        showDialog("Toyx Data", myToyx.toString());
                    } else {
                        showDialog("Warning", "Fetch error: " + e.getDetailMessage());
                    }
                }
            });
		}
	}
	
	/*
	 * save the toyx
	 * view is the button from layout
	 */
	public void save(View view) {
		TOYX_ID = etToyxId.getText().toString();
		if(API_KEY.compareTo("") == 0 || SECRET_KEY.compareTo("") == 0){
			showDialog("Warning", "The SECRET_KEY or the API_KEY are invalid");
		}else if(TOYX_ID.compareTo("") == 0   || myToyx == null){
			showDialog("Warning", "Scan or enter a valid toyx id and Create Session first");
		}else{
			// show progress dialog
			progress.setTitle("Loading");
			progress.setMessage("Creating session...");
			progress.show();

			// SAVE
			myToyx.saveInBackground(new TwinspriteCallback() {
                @Override
                public void onFinish(TwinspriteException e) {
                    progress.dismiss();
                    if (e == null) {
                        showDialog("Warning", "The toyx was saved");
                    } else {
                        showDialog("Warning", "Save error: " + e.getDetailMessage());
                    }
                }
            });
		}
	}

    /*
    * Redeem a toyx (redemptions have limited uses)
    */
    public void redeem(View view)
    {
        TOYX_ID = etToyxId.getText().toString();
        if(API_KEY.compareTo("") == 0 || SECRET_KEY.compareTo("") == 0){
            showDialog("Warning", "The SECRET_KEY or the API_KEY are invalid");
        }else if(TOYX_ID.compareTo("") == 0){
            showDialog("Warning", "Scan or enter a valid toyx id");
        }else{
            // show progress dialog
            progress.setTitle("Loading");
            progress.setMessage("Redeeming...");
            progress.show();

            // REDEEM
            RedemptionRequest.RedeemInBackground(TOYX_ID, new TwinspriteToyxCallback() {
                @Override
                public void onFinish(Toyx toyx, TwinspriteException e) {
                    progress.dismiss();
                    if(e==null){
                        showDialog("Warning", "Toyx has been redeemed successfully " + toyx.toString());
                    }else{
                        showDialog("Warning", "Error redeeeming toyx: "+e.getDetailMessage());
                    }
                }
            });
        }
    }
	
	/*
	 * Scan QR code wich contain toyx id
	 * requestCode the code to identify the request
	 * requestCode the result code obtained
	 * data the intent with the data inside
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		  if (requestCode == 1) {

		     if(resultCode == RESULT_OK){
		         //String result=data.getStringExtra("result");
                 String result=data.getStringExtra("SCAN_RESULT");
		         TOYX_ID = getToyxFromUrl(result);
		         etToyxId.setText(TOYX_ID);
		     }
		     if (resultCode == RESULT_CANCELED) {    
		         //Write your code if there's no result
		     }
		  }
		}//onActivityResult
	
	/*
	 * Show dialog with the result of any request
	 * title the title is going to appear in the dialog
	 * message the body of the dialog
	 */
	private void showDialog(final String title, final String message){
		
		runOnUiThread(new Runnable() {
		     @Override
		     public void run() {

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						myContext);
		 
				// set title
				alertDialogBuilder.setTitle(title);
	 
				// set dialog message
				alertDialogBuilder
					.setMessage(message)
					.setCancelable(false)
					.setPositiveButton("OK",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							//close dialog
						}
					  });
	 
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
				alertDialog.show();
		    }	
		});
	}

    /**
     * Just in case the toyx we have just scanned is an url instead of a clean toyx id, parse it and return just the id
     * @param url
     * @return
     */
    private String getToyxFromUrl(String url) {
        int index = url.lastIndexOf('/') + 1;

        if (url.length() > index) {
            return url.substring(index);
        }

        return url;
    }
}
