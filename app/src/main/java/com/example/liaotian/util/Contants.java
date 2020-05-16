package com.example.liaotian.util;

import io.realm.RealmConfiguration;

public class Contants {

    public static boolean first = true;     //第一次登录
    public static final String url = "http://192.168.10.100:80/zhihuishequ/android/";    //网址
//    public static final String url = "http://192.168.43.124/zhihuishequ/android/";    //网址

    public static final int EVENTBUS_REGIST = 0;
    public static final int EVENTBUS_IS_REGIST = 1;
    public static final int EVENTBUS_SHOUJIHAO_LOGIN = 2;
    public static final int EVENTBUS_GETTIANQI = 3;
    public static final int EVENT_GETNEWS = 4;
    public static final int EVENT_GETZHANGDAN = 5;
    public static final int EVENT_GETPAY = 6;
    public static final int EVENT_GETADDRESS = 7;
    public static final int EVENT_SETCHUZUSUCESS = 8;
    public static final int EVENT_GETZUFANGSUCESS = 9;
    public static final int EVENT_GETWEIXIUSUCCESS= 10;
    public static final int EVENT_SETWEIXIUSUCCESS= 11;
    public static final int EVENT_GETPERSONALINFO= 12;
    public static final int EVENT_SETPERSONALINFO= 13;
    public static final int EVENT_GETFUWULIST = 14;
    public static final int EVENT_GETFUWU= 15;
    public static final int EVENT_GETCHUZUXIUGAI= 16;
    public static final int EVENT_SETFUWUSUCCESS= 17;
    public static final int EVENT_GETTOUSUUCCESS= 18;
    public static final int EVENT_SETTOUSUSUCCUESS= 19;
    public static final int EVENT_GETTOUSUUCCESSID= 20;
    public static final int EVENT_GETROOMSUCCESS= 21;
    public static final int EVENT_UPDATECHUZUSUCCESS =  22;
    public static final int EVENT_UPDATEJIAOFEISUCCESS =  23;
    public static final int EVENT_GETKUAIDIMESSAGE=  24;
    public static final int EVENT_ADDTOUSUSECCESS=  25;
    public static final int EVENT_DELETESUCCESS=  26;
    public static final int EVENT_DELETEFAIL=  27;
    public static final String t1 = "a";
    public static final String t2 = "b";
    public static final String t3 = "c";
    public static final String t4 = "d";

    public static final int uploadsuccess = 1;
    public static final int filecompree = 2;
    public static boolean isfirst = false;
    public static final String type1 = "电费";
    public static final String type2 = "水费";
    public static final String type3 = "物业费";
    public static final String type4 = "其他";
    public static final String type5 = "全部";
    public static RealmConfiguration config = new RealmConfiguration.Builder()
            .name("default.realm")
            .schemaVersion(1)
            .build();


    /**
     * 用于支付宝支付业务的入参 app_id。
     */
    public static final String APPID = "2016102100729088";

    /**
     * 用于支付宝账户登录授权业务的入参 pid。
     */
    public static final String PID = "";

    public static final String RSA2_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCDPXPaj3+2ecHr9/By540ViQMVhX7FEVxfqbt+LgsL6Bn9eYboRBrwAgO/yHx884+XIB2dJyMruHvCJCH+dO9L/FFI0IYq8JmRUPXnMvDowLOubcZFaGyrRNAG8XIBkina1kV/pfcQjgxC4q4MYExH3IGEHTGym1OwRBi4PE4I0ZtZVfgGbCDPUVNFFQFKGzCqPIQTGadSFZGc037/gjCdZSabL92/Q9Kw+UZNGgzOmqtjNaW05avD3sT3E7YUZZGauQ1PUvx/V/iR3+fpznx6oiM0myhKr+q9JDbl8V0tjYiF34MoqaK7XKKXYBEBvfmVleVXmmKiTGUU6pnCXuhJAgMBAAECggEATGw1kZksYoMGZLVtpEuoAVlWnWquRm10oBRDpReEwTs4Hz1sTMaT9R0HN9+0dkKSZAuEkyXeOjBkiMnnrbhEPLhCUSd0aX7Pviop96bXWIIWX8V2xqYnWZhplxF0NOv7SX6q28k2VMKS60P63CQbZ2FFXjxt2zbOBf+xcV9UhZdHMxeqf/g63CTJY8v1tAldZnxcMUQ/6Vha55GWIjTsM1yVJvpbTRd7ZbGcUEDN8vP/J3udSfR6xfHtyaX6EYlBm4em0zFLzoDD7NP5mxcMAOx87ZrrEiiBmAiDDEu0V2CGtGvXpx//n6fwg1yDUUdcEc8SFLT3j730pKLJMGvvfQKBgQDiDh+H2Ql67hzwC4b29pA/N+US7n7TWzkmVBZfsRUObDirXW81oumGOv//rWrsapN2Br6znXL995Ap2cUu/vNvEK3c/VIldM9RvP7utpNhuUN7XOcIvwvBCvnyXL2c/WZJBFH32Hm3zlb9O1J2UmUuDqi/CuA6ykEMGGi2JIMkDwKBgQCUoADnYdFCKWmjrcGg9L5ubPhS3zocIC5Q8uK3X3ibu9FLkebgnBv9qPR6xDjeXx5DaweGtHxChRHCQkbIIOM7Yyl5Kg5RB1mvhW+SFVCyeM/eYqD0KDy7mzIrVcQvQMbb+pOB1b9FUcRJwI9Y3eV0RHLPAJ363keEMh2JoGL2JwKBgQDW2TV+l/k6rTUzsMEF2DIg468E30gK+ZjHpQPezx+EJbd2p8ca40uwGENXbitBp8mfU6nMbMjuOGz8Wc3fbNFRrjiQ4PNCRlMFDOv/Veed6ccJaLLszScOwq/sedh9bRnaLBD7zvNgjux+ofMuIgGoVKZtbJjRR4mrRUfAVdOOYQKBgQCD6LakpeaJ6COASBkQd+gELeb/9mumxtEUu12JDs0Gu114yte1Bhi5u5iw1TL7+kPr5jRZ1BptF5m5ZgVYXggPj2UhyrfGJtXsuGtbpltm617iq3ra+FNe1tFiMM7ceYuFdhIxK7qFFZs3MhaH8vlRhea33n5YR6wFok0g9x6eBQKBgBhPaLoiIqz2XtQzTjYGXnbC9k42FAa2fhzy/hbnYlIqreh8y7Xxf4Vl7npdE4Qha18qXJ6kA899o9L41ecDC0moKa0lGWM36QoQUKLl80leEXSVkCMrF7ZTADvV5b6uTFlOJBAqTuz/TQj1MwACaINGYycxMxTXrn+ScbROIuNq";
    public static final String gongyao = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgz1z2o9/tnnB6/fwcueNFYkDFYV+xRFcX6m7fi4LC+gZ/XmG6EQa8AIDv8h8fPOPlyAdnScjK7h7wiQh/nTvS/xRSNCGKvCZkVD15zLw6MCzrm3GRWhsq0TQBvFyAZIp2tZFf6X3EI4MQuKuDGBMR9yBhB0xsptTsEQYuDxOCNGbWVX4Bmwgz1FTRRUBShswqjyEExmnUhWRnNN+/4IwnWUmmy/dv0PSsPlGTRoMzpqrYzWltOWrw97E9xO2FGWRmrkNT1L8f1f4kd/n6c58eqIjNJsoSq/qvSQ25fFdLY2Ihd+DKKmiu1yil2ARAb35lZXlV5piokxlFOqZwl7oSQIDAQAB";
    /**
     * 用于支付宝账户登录授权业务的入参 target_id。
     */
    public static final String TARGET_ID = "";

    public static final String login_success = "login_success";
}
