package vikrant.com.myapplicationfirebase;


import android.app.DatePickerDialog;
import android.app.DownloadManager;
import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.security.PrivilegedAction;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class Main2Activity extends AppCompatActivity {

    private final String TAG = "ListActivity";
    DatabaseReference mListItemRef;
    private RecyclerView mListItemsRecyclerView;
    private ListItemsAdapter mAdapter;
    private ArrayList<EventPOJO> myListItems;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    DatabaseReference dbRef;
    Button btn,btn2,btn3,btn4 ;
    AppCompatImageButton imgbtn;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

      //  dbRef = database.getReference("User"+auth.getCurrentUser().getUid());// "/User/lavY0VIigzQaZ7xZBuyrSyWKgeC3"    https://myapplicationfirebase-1bd85.firebaseio.com/User/V31ISKWWJZbKB1dKS16R1lhpNHF2
     //   mListItemRef=dbRef.child("posts");

        dbRef = FirebaseDatabase.getInstance().getReference("User/"+auth.getCurrentUser().getUid());//     auth.getCurrentUser().getUid() https://myapplicationfirebase-1bd85.firebaseio.com/User/V31ISKWWJZbKB1dKS16R1lhpNHF2
        mListItemRef=dbRef.child("posts");



       // mListItemRef = mDB.child("posts");
        myListItems = new ArrayList<>();
        mListItemsRecyclerView = (RecyclerView)findViewById(R.id.list);
       // mListItemsRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getResources()));
        mListItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        updateUI();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

       btn=(Button)findViewById(R.id.btn);
        btn2=(Button)findViewById(R.id.btn2);
        btn3=(Button)findViewById(R.id.btn3);
        btn4=(Button)findViewById(R.id.btn4);
        imgbtn =(AppCompatImageButton) findViewById(R.id.signout);


        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar date = Calendar.getInstance();
                date.add(Calendar.MONTH, -1);
                long l = Long.valueOf(String.valueOf(date.getTimeInMillis()));

                //This method returns the time in millis
                Log.e("current time",String.valueOf(date.getTime()));
                Log.e("current time sec",String.valueOf(date.getTime()));
                Log.e("1 hr before",String.valueOf(l));


                DatabaseReference mDatabaseRef =FirebaseDatabase.getInstance().getReference("User/"+auth.getCurrentUser().getUid()+"/posts");

                Query query=mDatabaseRef.orderByChild("Date").startAt(l);

                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        myListItems.clear();

                        for (DataSnapshot data:dataSnapshot.getChildren()){


                            EventPOJO models=data.getValue(EventPOJO.class);
                            myListItems.add(models);

                        }

                        updateUI();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                    }
        });


        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();

                startActivity(new Intent(Main2Activity.this,LoginActivity.class));
                finish();
            }
        });

       btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

              // Collections.sort(myListItems);
              // updateUI();


               Calendar date = Calendar.getInstance();
               date.add(Calendar.DAY_OF_MONTH, -1);
               long l = Long.valueOf(String.valueOf(date.getTimeInMillis()));

               //This method returns the time in millis


               DatabaseReference mDatabaseRef =FirebaseDatabase.getInstance().getReference("User/"+auth.getCurrentUser().getUid()+"/posts");

               Query query=mDatabaseRef.orderByChild("Date").startAt(l);

               query.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {
                       myListItems.clear();

                       for (DataSnapshot data:dataSnapshot.getChildren()){


                           EventPOJO models=data.getValue(EventPOJO.class);
                           myListItems.add(models);

                       }

                       updateUI();

                   }

                   @Override
                   public void onCancelled(DatabaseError databaseError) {

                   }
               });




           }
       });


        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar date = Calendar.getInstance();
                date.add(Calendar.HOUR, -1);
                long l = Long.valueOf(String.valueOf(date.getTimeInMillis()));

                //This method returns the time in millis
                Log.e("current time",String.valueOf(date.getTime()));
                Log.e("current time sec",String.valueOf(date.getTime()));
                Log.e("1 hr before",String.valueOf(l));


                DatabaseReference mDatabaseRef =FirebaseDatabase.getInstance().getReference("User/"+auth.getCurrentUser().getUid()+"/posts");

                Query query=mDatabaseRef.orderByChild("Date").startAt(l);

                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        myListItems.clear();

                        for (DataSnapshot data:dataSnapshot.getChildren()){


                            EventPOJO models=data.getValue(EventPOJO.class);
                            myListItems.add(models);

                        }

                       updateUI();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }
        });


        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this,AddNewEvent.class));
            }
        });


            mListItemRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.e(TAG+"Added",dataSnapshot.getValue(EventPOJO.class).toString());
                fetchData(dataSnapshot);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.e(TAG+"Changed",dataSnapshot.getValue(EventPOJO.class).toString());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.e(TAG+"Removed",dataSnapshot.getValue(EventPOJO.class).toString());
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.e(TAG+"Moved",dataSnapshot.getValue(EventPOJO.class).toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG+"Cancelled",databaseError.toString());
            }

        });

    }




    private void fetchData(DataSnapshot dataSnapshot) {
        EventPOJO listItem=dataSnapshot.getValue(EventPOJO.class);
        myListItems.add(listItem);
        updateUI();
    }

    private void updateUI(){
        mAdapter = new ListItemsAdapter(myListItems);
        mListItemsRecyclerView.setAdapter(mAdapter);
    }

    private class ListItemsHolder extends RecyclerView.ViewHolder{
        public TextView mNameTextView,mEmailTextView,mDateTextView,mTimeTextView;
        public ListItemsHolder(View itemView){
            super(itemView);
            mNameTextView = (TextView) itemView.findViewById(R.id.list_title);
            mEmailTextView = (TextView) itemView.findViewById(R.id.list_desc);
            mDateTextView = (TextView) itemView.findViewById(R.id.date);
            mTimeTextView = (TextView) itemView.findViewById(R.id.time);
        }

        public void bindData(EventPOJO s){
            mNameTextView.setText(s.getEventAgenda());
            mEmailTextView.setText(s.getEmails());
            DateFormat simple = new SimpleDateFormat("dd-MMM-yyyy");
            Date result = new Date(s.getDate());
            mDateTextView.setText(simple.format(result));
            mTimeTextView.setText(new SimpleDateFormat("hh:mm:ss").format(s.getDate()));
        }
    }
    private class ListItemsAdapter extends RecyclerView.Adapter<ListItemsHolder>{
        private ArrayList<EventPOJO> mListItems;
        public ListItemsAdapter(ArrayList<EventPOJO> ListItems){
            mListItems = ListItems;
        }
        @Override
        public ListItemsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(Main2Activity.this);
            View view = layoutInflater.inflate(R.layout.list_item,parent,false);
            return new ListItemsHolder(view);

        }
        @Override
        public void onBindViewHolder(ListItemsHolder holder, int position) {
            EventPOJO s = mListItems.get(position);
            holder.bindData(s);
        }
        @Override
        public int getItemCount() {
            return mListItems.size();
        }
    }


}
