package com.example.liaotian.entity.MyMigration;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

public class MyMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();
        if(oldVersion ==0){
            schema.get("User")
                    .addField("address",String.class);
            oldVersion++;
        }
      /*  if (oldVersion == 1){
            schema.get("User")
                    .addField()
        }*/
    }
}
