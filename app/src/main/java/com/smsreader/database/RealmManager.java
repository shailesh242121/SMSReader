package com.smsreader.database;

import com.smsreader.model.SMSModel;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmManager {
    private static RealmManager instance;
    public static RealmManager getInstance()
    {
        if(instance == null)
             instance   = new RealmManager();
        return instance;
    }


    private Realm getRealm()
    {
        // Get a Realm instance for this thread
       // Realm realm = Realm.getDefaultInstance();

// Query Realm for all dogs younger than 2 years old
//        final RealmResults<SMSModel> puppies = realm.where(SMSModel.class).lessThan("age", 2).findAll();
//        puppies.size(); // => 0 because no dogs have been added to the Realm yet
//
//// Persist your data in a transaction
//        realm.beginTransaction();
//        final SMSModel managedDog = realm.copyToRealm(dog); // Persist unmanaged objects
//        Person person = realm.createObject(Person.class); // Create managed objects directly
//        person.getDogs().add(managedDog);
//        realm.commitTransaction();

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("smsRealm.realm")
               // .encryptionKey(getKey())
                .schemaVersion(42).deleteRealmIfMigrationNeeded()
                //.modules(new MySchemaModule())
                //.migration(new MyMigration())
                .build();
// Use the config
        Realm realm = Realm.getInstance(config);
        return realm;
    }

    public List<SMSModel> getAllSMS()
    {
        Realm realm = getRealm();
        List<SMSModel> mList = realm.where(SMSModel.class).findAll();

        return mList;
    }

    public void saveSMS(final SMSModel model)
    {
        Realm realm = getRealm();
        try { // I could use try-with-resources here
          //  realm = Realm.getDefaultInstance();


            realm.beginTransaction();
            realm.insert(model);
            realm.commitTransaction();

//            realm.executeTransaction(new Realm.Transaction() {
//                @Override
//                public void execute(Realm realm) {
//                   // realm.beginTransaction();
//                    realm.insert(model);
//                    realm.commitTransaction();
//                }
//            });
        } finally {
            if(realm != null) {
                realm.close();
            }
        }
    }
}
