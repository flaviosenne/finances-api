package com.project.finances.app.usecases.user.utils;

import java.util.Random;

public class UserUtils {

    public static String generateCode(){
        return  String.format("%06d",new Random().nextInt(999999));
    }
}
