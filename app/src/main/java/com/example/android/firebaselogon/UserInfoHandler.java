package com.example.android.firebaselogon;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserInfoHandler {
    public UserInfoHandler(String userUniqueId,
                           String userEmail,
                           String userName,
                           String userStatus,
                           int userCurrentScore) {

        UserInfoTableEntry oneUserEntry = new UserInfoTableEntry();
        //oneUserEntry.setUserUniqueId( userUniqueId );
        oneUserEntry.setUserEmail( userEmail );
        oneUserEntry.setUserName( userName );
        oneUserEntry.setUserStatus( userStatus );
        oneUserEntry.setUserCurrentScore( userCurrentScore );

        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference tableReference = database.getReference();
            DatabaseReference tableChild = tableReference.child( "userInfo" );
            tableChild.child( userUniqueId ).setValue( oneUserEntry );
        } catch (Exception e){
            /**/
        }
    }
}
