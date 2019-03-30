package vikrant.com.myapplicationfirebase;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;


public class AddNewEvent extends AppCompatActivity {

    private Button addEvent;
    private final String TAG = "AddNewEvent";
    private TextView  timeTxt, dateTxt;
    private EditText  agenda, newEmail;
    private DatabaseReference mDatabase;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    DatabaseReference dbRef;
    DatabaseReference mListItemRef;
    long msec;
    int hr,ms,yy,mm,dd;
    String mDate,startDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_event);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        //get firebase auth instance
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        dbRef = database.getReference("User/"+auth.getCurrentUser().getUid());//     auth.getCurrentUser().getUid() https://myapplicationfirebase-1bd85.firebaseio.com/User/V31ISKWWJZbKB1dKS16R1lhpNHF2
        mListItemRef=dbRef.child("posts");

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
      final   String userId = auth.getCurrentUser().getUid();

        mDatabase= database.getReference("User");

        timeTxt=(TextView)findViewById(R.id.time);
        dateTxt=(TextView)findViewById(R.id.date);

        dateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int y = calendar.get(Calendar.YEAR);
                int m = calendar.get(Calendar.MONTH);
                int d = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(AddNewEvent.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                      //  dd-M-yyyy hh:mm:ss
                        String date = String.valueOf(dayOfMonth) + "-" + String.valueOf(month + 1)
                                + "-" + String.valueOf(year);

                        dateTxt.setText(date);

                        startDate =String.valueOf(year)+"-" + String.valueOf(month + 1)+"-"+String.valueOf(dayOfMonth);

                        mDate=date;

                     yy =year;
                     mm =month+1;
                     dd = dayOfMonth;

                      /*  Date date1= null;
                        try {

                            date1 = new SimpleDateFormat("dd/mm/yyyy").parse(date);
                            dateTxt.setText(date);
                            Log.e("date",date1.toString());
                             msec = date1.getTime();

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
*/
                    }


                },y, m, d);datePicker.show();
            }
        });

        timeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                 int mHour = c.get(Calendar.HOUR_OF_DAY);
                 int mMinute = c.get(Calendar.MINUTE);
                // Launch Time Picker Dialog
                final TimePickerDialog timePickerDialog = new TimePickerDialog(AddNewEvent.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {


                                timeTxt.setText(hourOfDay + ":" + minute);

                                hr=hourOfDay;
                                ms=minute;

                                Log.e("hr",String.valueOf(hr));
                               // time_formated = hourOfDay + ":" + minute + ":" + "00";

                            }
                        }, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(AddNewEvent.this, LoginActivity.class));
                    finish();
                }
            }
        };

        addEvent = (Button) findViewById(R.id.addEvent);


        agenda = (EditText) findViewById(R.id.agenda);
        newEmail = (EditText) findViewById(R.id.new_email);



        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String EmailString = newEmail.getText().toString();
                ArrayList<String> list = new ArrayList<String>(Arrays.asList(EmailString.split(",")));
              //  EventPOJO eventPOJO=new EventPOJO( agenda.getText().toString(),dateTxt.getText().toString(),timeTxt.getText().toString(),newEmail.getText().toString());

              //  String userId1 = auth.getCurrentUser().getUid();
                // DatabaseReference currentUser = mDatabase.child(userId1).child();


               // String userId1 = auth.getCurrentUser().getUid();
                //  DatabaseReference currentUser = mDatabase.child(userId1);


                LocalDateTime date121 = LocalDateTime.of(yy, mm, dd, hr, ms);


               msec= date121.toInstant(ZoneOffset.ofTotalSeconds(19800)).toEpochMilli();
                Log.e("Date Is ",date121.toString() + "  "+date121.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli());


                String userId1 = auth.getCurrentUser().getUid();
                 DatabaseReference currentUser = mDatabase.child(userId1).child("posts").push();

 /*               FirebaseDatabase database = FirebaseDatabase.getInstance();



                String pattern = " HH:mm";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(hr+":"+ms);
                String date = simpleDateFormat.format(new Date());
                System.out.println(date);




                String s;
                DateFormat formatter;

                formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date11 = null;
                try {
                    date11 = formatter.parse(startDate);//"2013-07-17"
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                formatter = new SimpleDateFormat("yyMMdd");
                s = formatter.format(date11);
               Log.e("new ",s);





                LocalDate datePart = date11.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
               Calendar cal = Calendar.getInstance();
              // cal.setTime(date);
                Instant instant = Instant.ofEpochMilli(cal.getTimeInMillis());
                LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();

                LocalTime timePart = LocalTime.parse(date);
                LocalDateTime dt = LocalDateTime.of(datePart, timePart);

                Log.e("Final date is ",dt.toString());
              //  msec= dt.toEpochSecond(OffsetDateTime.now().getOffset());



               *//* Date date1= null;
                try {

                    SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy HH:mm");//yyyy-MM-dd HH:mm:ss.SSS
                    Date dateStr = formatter1.parse(mDate+" "+date);
                    String formattedDate = formatter1.format(dateStr);
                    System.out.println("yyyy-MM-dd date is ==>"+formattedDate);


                    msec = dateStr.getTime();

                } catch (ParseException e) {
                    e.printStackTrace();
                }*/

              Map<String,Object> data =new HashMap<>();
              data.put("EventAgenda",agenda.getText().toString());
              data.put("Date",msec);
              data.put("Time",timeTxt.getText().toString());
              data.put("Emails",newEmail.getText().toString());

               currentUser.setValue(data);


               currentUser.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       dateTxt.setText("");
                       agenda.setText("");
                       timeTxt.setText("");
                       newEmail.setText("");

                       Toast.makeText(AddNewEvent.this, "Event added ", Toast.LENGTH_SHORT).show();

                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {
                       Toast.makeText(AddNewEvent.this, "Unable to add Event  ", Toast.LENGTH_SHORT).show();
                   }
               });

            //    currentUser.setValue(users);



              //  String userId1 = auth.getCurrentUser().getUid();
              //  DatabaseReference currentUser = mDatabase.child(userId1);
              //  currentUser.child("Emails").setValue(list);


            }
        });


      /*  mListItemRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.e(TAG+"Added",dataSnapshot.getValue(EventPOJO.class).EventAgenda.toString());
               // fetchData(dataSnapshot);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG+"Changed",dataSnapshot.getValue(EventPOJO.class).EventAgenda.toString());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG+"Removed",dataSnapshot.getValue(EventPOJO.class).EventAgenda.toString());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG+"Moved",dataSnapshot.getValue(EventPOJO.class).EventAgenda.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG+"Cancelled",databaseError.toString());
            }

        });*/
    }




    //sign out method
    public void signOut() {
        auth.signOut();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

}